package com.seckill.demo.service.impl;

import com.google.common.collect.Lists;
import com.seckill.demo.annotation.Limit;
import com.seckill.demo.domain.Result;
import com.seckill.demo.domain.Seckill;
import com.seckill.demo.domain.SeckillSuccess;
import com.seckill.demo.mapper.CommonMapper;
import com.seckill.demo.mapper.SeckillMapper;
import com.seckill.demo.queue.SeckillQueue;
import com.seckill.demo.service.SeckillService;
import com.seckill.demo.utils.RedisClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return type == 1 ? doRedisOptimisticAchievement() : doRedisPessimisticAchievement();
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

    private Result doRedisOptimisticAchievement() {
        return null;
    }

    private Result doRedisPessimisticAchievement() {
        return null;
    }

}
