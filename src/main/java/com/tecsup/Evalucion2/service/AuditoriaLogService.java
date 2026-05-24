package com.tecsup.Evalucion2.service;

import com.tecsup.Evalucion2.entity.AuditoriaLog;
import com.tecsup.Evalucion2.repository.AuditoriaLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaLogService {

    private final AuditoriaLogRepository auditoriaLogRepository;

    public List<AuditoriaLog> listar() {
        return auditoriaLogRepository.findAll();
    }
}