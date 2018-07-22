package com.seckill.demo.controller;

import com.seckill.demo.domain.Result;
import com.seckill.demo.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author ss
 * @Date 2018/7/6 16:45
 */
@Api(tags = "秒杀系统首页")
@RestController
@Slf4j
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @ApiOperation(value = "test测试", notes = "接口发布说明")
    @GetMapping("/start")
    public String test() {
        log.info("test============");
        return "test";
    }

    @ApiOperation(value = "mysql行锁", notes = "行锁实现秒杀")
    @PostMapping("/mock-mysql")
    public Result seckillByMysql(@RequestParam String seckillId) {
        return seckillService.mysqlAchievement(seckillId);
    }

    @ApiOperation(value = "blockqueue阻塞队列", notes = "阻塞队列实现秒杀")
    @PostMapping("/mock-blockqueue")
    public Result seckillByQueue(@RequestParam String seckillId) {
        return seckillService.queueAchievement(seckillId);
    }
}
