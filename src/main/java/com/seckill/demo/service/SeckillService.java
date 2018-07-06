package com.seckill.demo.service;


import com.seckill.demo.domain.Result;

/**
 * @Author ss
 * @Date 2018/7/6 16:46
 */

public interface SeckillService {
    Result mysqlAchievement(String seckillId);
    Result redisAchievement(String seckillId);
    Result queueAchievement(String seckillId);
}
