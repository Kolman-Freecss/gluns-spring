package org.gluns.glunsspring.shared.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * PerformanceAspect
 * Used to measure the execution time of methods annotated with @Service.
 */
@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void repositoryClassMethods() {
    }

    @Around("repositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object retVal = proceedingJoinPoint.proceed();
        long endTime = System.nanoTime();

        final String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        final String methodName = proceedingJoinPoint.getSignature().getName();

        log.info("EXECUTION of method::'{}' on class '{}', took '{}' ms",
                methodName,
                className,
                TimeUnit.NANOSECONDS
                        .toMillis(endTime - startTime)
        );
        return retVal;
    }

}