package com.mnazarenka.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Aspect
public class ServicesLogger {
    @Autowired
    private Logger logger;

    @Pointcut("@within(com.mnazarenka.annotation.Loggable)")
    public void services() {
    }

    @Before("services()")
    public void loggingServiceMethod(JoinPoint joinPoint) {
        String packageName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0) {

            StringBuilder sb = new StringBuilder();
            Arrays.stream(args).forEach(a -> sb.append(a).append(", "));
            String formattedArguments = sb.toString();

            logger.info(String.format("method %s from %s was called with parameters: %s.", methodName, packageName, formattedArguments.substring(0, formattedArguments.length() - 2)));
        } else {

            logger.info(String.format("method %s from %s was called without parameters", methodName, packageName));
        }
    }
}
