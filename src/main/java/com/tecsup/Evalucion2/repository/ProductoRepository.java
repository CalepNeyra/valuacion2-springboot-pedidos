package com.tecsup.Evalucion2.repository;

import com.tecsup.Evalucion2.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}