package com.seckill.demo.mapper;

import com.seckill.demo.domain.Seckill;
import com.seckill.demo.domain.SeckillSuccess;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ss
 * @Date 2018/7/6 16:45
 */
public interface SeckillMapper {
    Seckill findSeckillById(String seckillId);
    int releaseStock(String seckillId);
    int saveSeckillSuccess(List<SeckillSuccess> seckillSuccesses);
    int saveSeckillSuccessBySingle(SeckillSuccess seckillSuccess);
}
