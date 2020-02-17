package com.otus.spring.hw02.aspect;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {
    private final Gson gson;

    @Around("@annotation(Loggable)")
    public Object employeeAroundAdvice(final ProceedingJoinPoint proceedingJoinPoint) {
        final Signature signature = proceedingJoinPoint.getSignature();
        final Object[] args = proceedingJoinPoint.getArgs();
        log.debug("method: \"{}\" is being invoked with args: \"{}\"", signature, gson.toJson(args));

        Object value = null;
        try {
            value = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("exception: \"{}\" has occurred while method: \"{}\" is being executed", throwable, signature);
        }
        log.debug("method: \"{}\" has returned: \"{}\"", signature, gson.toJson(value));
        return value;
    }
}
