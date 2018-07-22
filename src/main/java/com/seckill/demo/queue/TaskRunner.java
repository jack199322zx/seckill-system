package com.seckill.demo.queue;

import com.seckill.demo.domain.Result;
import com.seckill.demo.domain.SeckillSuccess;
import com.seckill.demo.service.SeckillService;
import com.seckill.demo.service.impl.SeckillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 消费秒杀队列
 * 创建者 科帮网
 * 创建时间	2018年4月3日
 */
@Component
@Slf4j
public class TaskRunner implements ApplicationRunner {

	@Autowired
	private SeckillService seckillService;
	
	@Override
    public void run(ApplicationArguments var) throws Exception{
		log.info("run ---------------");
		while(true){
			//进程内队列，不推荐这种方式，启动之后就会一直跑下去，耗费性能的一件事情
			SeckillSuccess kill = SeckillQueue.getMailQueue().consume();
			if(kill!=null){
				Result result = seckillService.seckillBySingleThread(kill.getSeckill_id());
				log.info(Thread.currentThread().getName() + ":result is --------{}", result.getMessage());
			}
		}
    }
}