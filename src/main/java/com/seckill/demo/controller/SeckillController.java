package com.seckill.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ss
 * @Date 2018/7/6 16:45
 */
@Api(tags = "秒杀系统首页")
@RestController
@Slf4j
@RequestMapping("seckill")
public class SeckillController {

    @ApiOperation(value = "test测试", notes = "接口发布说明")
    @PostMapping("/start")
    public String test() {
        log.info("test============");
        return "test";
    }
}
