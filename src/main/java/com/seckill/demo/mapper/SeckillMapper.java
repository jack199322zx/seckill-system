package com.seckill.demo.mapper;

import com.seckill.demo.domain.Seckill;

/**
 * @Author ss
 * @Date 2018/7/6 16:45
 */
public interface SeckillMapper {
    Seckill findSeckillById(String seckillId);
}
