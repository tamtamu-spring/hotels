package com.mnazarenka.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class ServicesLogger {
    @Autowired
    private Logger logger;

    @Pointcut("@within(com.mnazarenka.annotation.Loggable)")
    public void services() {
    }

    @Before("services()")
    public void loggingServiceMethod(JoinPoint JoinPoint) {
        String packageName = JoinPoint.getTarget().getClass().getName();
        String methodName = JoinPoint.getSignature().getName();

        logger.info(String.format("method %s from %s was called ", methodName, packageName));
    }
}
