package com.tecsup.Evalucion2.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.tecsup.evaluacion2.service.*.*(..))")
    public void inicioMetodo(JoinPoint joinPoint) {
        System.out.println("Iniciando método: " + joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* com.tecsup.evaluacion2.service.*.*(..))")
    public void finMetodo(JoinPoint joinPoint) {
        System.out.println("Finalizando método: " + joinPoint.getSignature().getName());
    }
}