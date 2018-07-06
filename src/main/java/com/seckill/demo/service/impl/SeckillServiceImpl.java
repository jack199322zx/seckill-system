package com.seckill.demo.service.impl;

import com.seckill.demo.domain.Result;
import com.seckill.demo.service.SeckillService;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author ss
 * @Date 2018/7/6 16:46
 */
@Service
public class SeckillServiceImpl implements SeckillService{

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));

    @Override
    public Result mysqlAchievement(String seckillId) {

        // 模拟1000人同时访问
        for (int i = 0; i < 1000; i++) {

        }
        return null;
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
