package com.otus.spring.hw03.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class MeasureAspect {
    @Around("@annotation(com.otus.spring.hw03.aspect.Measurable)")
    public Object employeeAroundAdvice(final ProceedingJoinPoint proceedingJoinPoint) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final String methodName = proceedingJoinPoint.getSignature().getName();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("exception: \"{}\" has occurred while method: \"{}\" is being executed", throwable, methodName);
        }
        watch.stop();
        log.debug("method \"{}\" has been executed for {} micro seconds", methodName, watch.getTime(MICROSECONDS));
        return value;
    }
}
