package com.tecsup.Evalucion2.controller;

import com.tecsup.Evalucion2.entity.AuditoriaLog;
import com.tecsup.Evalucion2.service.AuditoriaLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@RequiredArgsConstructor
public class AuditoriaLogController {

    private final AuditoriaLogService auditoriaLogService;

    @GetMapping
    public List<AuditoriaLog> listar() {
        return auditoriaLogService.listar();
    }
}