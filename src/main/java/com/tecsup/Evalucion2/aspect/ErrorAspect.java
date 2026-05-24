package com.tecsup.Evalucion2.aspect;

import com.tecsup.Evalucion2.entity.AuditoriaLog;
import com.tecsup.Evalucion2.repository.AuditoriaLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ErrorAspect {

    private final AuditoriaLogRepository auditoriaLogRepository;

    @AfterThrowing(
            pointcut = "execution(* com.tecsup.evaluacion2.service.*.*(..))",
            throwing = "ex"
    )
    public void capturarError(JoinPoint joinPoint, Exception ex) {
        System.out.println("Error en método: " + joinPoint.getSignature().getName());
        System.out.println("Detalle del error: " + ex.getMessage());

        AuditoriaLog log = new AuditoriaLog();
        log.setAccion("ERROR");
        log.setMetodo(joinPoint.getSignature().getName());
        log.setFecha(LocalDateTime.now());
        log.setUsuario(obtenerUsuarioActual());

        auditoriaLogRepository.save(log);
    }

    private String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            return "ANONIMO";
        }

        return authentication.getName();
    }
}