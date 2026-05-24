package com.tecsup.Evalucion2.repository;


import com.tecsup.Evalucion2.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCorreo(String correo);

    Optional<Cliente> findByCorreo(String correo);
}