package com.mnazarenka.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class ServicesLogger {
    @Autowired
    private Logger logger;

    @Pointcut("@within(com.mnazarenka.annotation.Loggable)")
    public void services() {
    }

    @Around("services()")
    public Object loggingServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        String packageName = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();

        logger.info(String.format("method %s from %s was called ", methodName, packageName));
        return pjp.proceed();
    }
}
