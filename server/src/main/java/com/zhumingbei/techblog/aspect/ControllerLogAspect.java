package com.zhumingbei.techblog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect{
    @Pointcut("execution(public * com.zhumingbei.techblog.controller.*.*(..))")
    public void PointCut(){

    }


    @Around("PointCut()")
    public Object Around(ProceedingJoinPoint point) throws Throwable{
        HashMap<String, Object> map = new HashMap<>();
        String className = point.getTarget().getClass().getName();
        map.put("className", className);
        String methodName = point.getSignature().getName();
        Long start = System.currentTimeMillis();
        Object result = point.proceed();
        Long end = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object[] args = point.getArgs();
        ArrayList<String> params = new ArrayList<>();
        for (Object arg : args){
            params.add(arg.toString());
        }
        map.put("methodName", methodName);
        map.put("params", params);
        map.put("result", result);
        map.put("executionTime", end-start);
        map.put("date", format.format(new Date()));
        log.info("ControllerLogAspect:" + map);
        return result;
    }



}
