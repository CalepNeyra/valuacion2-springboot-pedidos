package com.tecsup.Evalucion2.aspect;

import com.tecsup.Evalucion2.entity.AuditoriaLog;
import com.tecsup.Evalucion2.repository.AuditoriaLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditoriaAspect {

    private final AuditoriaLogRepository auditoriaLogRepository;

    @AfterReturning(
            "execution(* com.tecsup.evaluacion2.service.*.*(..)) " +
                    "&& !execution(* com.tecsup.evaluacion2.service.UsuarioDetailsServiceImpl.*(..)) " +
                    "&& !execution(* com.tecsup.evaluacion2.service.AuditoriaLogService.*(..))"
    )
    public void registrarAuditoria(JoinPoint joinPoint) {
        String metodo = joinPoint.getSignature().getName();

        AuditoriaLog log = new AuditoriaLog();
        log.setAccion(obtenerAccion(metodo));
        log.setMetodo(metodo);
        log.setFecha(LocalDateTime.now());
        log.setUsuario(obtenerUsuarioActual());

        auditoriaLogRepository.save(log);
    }

    private String obtenerAccion(String metodo) {
        if (metodo.startsWith("guardar") || metodo.startsWith("registrar")) {
            return "REGISTRAR";
        }

        if (metodo.startsWith("actualizar")) {
            return "ACTUALIZAR";
        }

        if (metodo.startsWith("eliminar")) {
            return "ELIMINAR";
        }

        if (metodo.startsWith("listar") || metodo.startsWith("buscar")) {
            return "CONSULTAR";
        }

        return "ACCION";
    }

    private String obtenerUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            return "ANONIMO";
        }

        return authentication.getName();
    }
}