package com.seckill.demo.service.impl;

import com.google.common.collect.Lists;
import com.seckill.demo.annotation.Limit;
import com.seckill.demo.domain.Result;
import com.seckill.demo.domain.Seckill;
import com.seckill.demo.domain.SeckillSuccess;
import com.seckill.demo.mapper.CommonMapper;
import com.seckill.demo.mapper.SeckillMapper;
import com.seckill.demo.queue.SeckillQueue;
import com.seckill.demo.redis.RedisDistributedLock;
import com.seckill.demo.service.SeckillService;
import com.seckill.demo.utils.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @Author ss
 * @Date 2018/7/6 16:46
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService{

//    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(1000, 10001, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(1000);
    private static RedisDistributedLock lock = RedisDistributedLock.getInstance("lock", 3000);

    @Autowired
    SeckillMapper mapper;
    @Autowired
    CommonMapper commonMapper;

    @Override
    @Transactional
    @Limit
    public Result mysqlAchievement(String seckillId) {
        // mysql悲观锁实现，for update写锁，测试时可使用压测工具jmeter
        List<SeckillSuccess> seckillSuccesses = Lists.newArrayList();
        Seckill seckill = mapper.findSeckillById(seckillId);
        int row = 0;
        if (seckill != null && seckill.getNumber() > 0) {
            mapper.releaseStock(seckillId);
            SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                    .seckill_id(seckillId)
                    .state(0)
                    .user_id(UUID.randomUUID().toString())
                    .build();
            seckillSuccesses.add(seckillSuccess);
            row = mapper.saveSeckillSuccess(seckillSuccesses);
        }
        return row > 0 ? Result.ok("success") : Result.fail("error");
    }

    @Override
    public Result redisAchievement(String seckillId, int type) {
        return type == 1 ? doRedisOptimisticAchievement(seckillId) : doRedisPessimisticAchievement(seckillId);
    }

    @Override
    public Result queueAchievement(String seckillId) {
        for(int i=0;i< 1000;i++) {
            // 模拟1000人同时访问
            Runnable runnable = new Runnable() {
                @Override
                @SneakyThrows
                public void run() {
                    cyclicBarrier.await();
                    String currentThread = Thread.currentThread().getName();
                    log.info(currentThread + "start");
                    SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                            .seckill_id(seckillId)
                            .state(0)
                            .user_id(UUID.randomUUID().toString())
                            .build();
                    boolean flag = SeckillQueue.getMailQueue().produce(seckillSuccess);
                    if (flag)
                        log.info(currentThread + "produce success");
                    else
                        log.info(currentThread + "produce fail");
                }
            };
            executor.execute(runnable);
        }
        return null;
    }

    @Override
    public Result seckillBySingleThread(String seckillId) {
        int num = commonMapper.findSeckillStockNum(seckillId);
        if (num > 0) {
            mapper.releaseStock(seckillId);
            SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                    .seckill_id(seckillId)
                    .state(0)
                    .user_id(UUID.randomUUID().toString())
                    .build();
            mapper.saveSeckillSuccessBySingle(seckillSuccess);
            return Result.ok("success");
        }
        return Result.fail("error");
    }

    /**
     * redis乐观锁实现
     * @param seckillId
     * @return
     */
    private Result doRedisOptimisticAchievement(String seckillId) {
        List<SeckillSuccess> seckillSuccesses = new ArrayList<>();
        Jedis jedis = RedisUtil.getJedis();
        jedis.set("lock", String.valueOf(100));
        for (int i= 0;i< 1000; i++) {
            Runnable runnable = new Runnable() {
                @Override
                @SneakyThrows
                public void run() {
                    try {
                        final Jedis jedis = RedisUtil.getJedis();
                        jedis.watch("lock");
                        String number2String = jedis.get("lock");
                        if (StringUtils.isNotEmpty(number2String)) {
                            int number = Integer.parseInt(number2String);
                            String currentThread = Thread.currentThread().getName();
                            if (number > 0) {
                                Transaction transaction = jedis.multi();
                                transaction.set("lock", String.valueOf(number - 1));
                                List<Object> result = transaction.exec();
                                if (result == null || result.isEmpty()) {
                                    System.out.println("悲剧了，顾客:" + currentThread + "没有抢到商品");// 可能是watch-key被外部修改，或者是数据操作被驳回
                                } else {
                                    mapper.releaseStock(seckillId);
                                    SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                                            .seckill_id(seckillId)
                                            .state(0)
                                            .user_id(UUID.randomUUID().toString())
                                            .build();
                                    seckillSuccesses.add(seckillSuccess);// 抢到商品记录一下
                                    System.out.println("好高兴，顾客:" + currentThread + "抢到商品");
                                }
                            } else {
                                System.out.println("悲剧了，库存为0，顾客:" + currentThread + "没有抢到商品");
                            }
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    } finally {
                        jedis.close();
                    }

                }
            };
            executor.execute(runnable);
        }
        return Result.ok("success");
    }

    /**
     * redis悲观锁实现
     * @param seckillId
     * @return
     */
    private Result doRedisPessimisticAchievement(String seckillId) {
        List<SeckillSuccess> seckillSuccesses = new ArrayList<>();
        for (int i= 0;i< 1000; i++) {
            Runnable runnable = new Runnable() {
                @Override
                @SneakyThrows
                public void run() {
                    try {
                        lock.lock(); // 尝试获取锁，获取到了就返回，没获取到就重试
                        Seckill seckill = mapper.findSeckillById(seckillId);
                        if (seckill != null && seckill.getNumber() > 0) {
                            mapper.releaseStock(seckillId);
                            SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                                    .seckill_id(seckillId)
                                    .state(0)
                                    .user_id(UUID.randomUUID().toString())
                                    .build();
                            seckillSuccesses.add(seckillSuccess);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            };
            executor.execute(runnable);
        }
        lock.clearJedis();
        if (seckillSuccesses.size() > 0) {
            mapper.saveSeckillSuccess(seckillSuccesses);
            return Result.ok("success");
        }
        return Result.fail("error");
    }

}
