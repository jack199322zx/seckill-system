package com.seckill.demo.aop;

import com.seckill.demo.mapper.CommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * 调用blockqueue时，需要注掉此类
 */

@Aspect
@Component
@Slf4j
public class RequestProcessor {

    @Autowired
    private CommonMapper commonMapper;

    @Pointcut("execution(* com.seckill.demo.service.*Service.*(..))")
    private void pointCutMethod() {
    }

    @Before("pointCutMethod()")
    public void beforeMethod(JoinPoint jp){
        Object[] objects = jp.getArgs();
        String methodName = jp.getSignature().getName();
        log.info("执行方法前置通知:" + methodName + ", 参数：" + Arrays.toString(objects));
        Object object = objects[0];
        if (object instanceof String) {
            commonMapper.updateSeckillById((String) object);
            commonMapper.clearSeckillSuccessById((String) object);
        }
    }

}
