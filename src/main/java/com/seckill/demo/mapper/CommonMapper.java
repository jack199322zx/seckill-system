package com.seckill.demo.mapper;

public interface CommonMapper {
    void clearSeckillSuccessById(String seckillId);
    void updateSeckillById(String seckillId);
    int findSeckillStockNum(String seckillId);
}
