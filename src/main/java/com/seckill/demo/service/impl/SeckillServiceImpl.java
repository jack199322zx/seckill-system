package com.seckill.demo.service.impl;

import com.google.common.collect.Lists;
import com.seckill.demo.domain.Result;
import com.seckill.demo.domain.Seckill;
import com.seckill.demo.domain.SeckillSuccess;
import com.seckill.demo.mapper.SeckillMapper;
import com.seckill.demo.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ss
 * @Date 2018/7/6 16:46
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService{

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));
    private static Random random = new Random();

    @Autowired
    SeckillMapper mapper;

    @Override
    public Result mysqlAchievement(String seckillId) {
        // 模拟1000人同时访问
        List<SeckillSuccess> seckillSuccesses = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            Seckill seckill = mapper.findSeckillById(seckillId);
            log.info("seckill========={}", seckill);
            if (seckill != null && seckill.getNumber() > 0) {
                mapper.releaseStock(seckillId);
                SeckillSuccess seckillSuccess = SeckillSuccess.builder()
                        .seckill_id(i)
                        .state(0)
                        .user_id(random.nextInt(1000))
                        .build();
                seckillSuccesses.add(seckillSuccess);
            }
        }
        int row = mapper.saveSeckillSuccess(seckillSuccesses);
        return row > 0 ? Result.ok("success") : Result.fail("error");
    }

    @Override
    public Result redisAchievement(String seckillId) {
        return null;
    }

    @Override
    public Result queueAchievement(String seckillId) {
        return null;
    }
}
