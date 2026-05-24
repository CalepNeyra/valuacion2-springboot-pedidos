package com.tecsup.Evalucion2.service;

import com.tecsup.Evalucion2.entity.Categoria;
import com.tecsup.Evalucion2.entity.Producto;
import com.tecsup.Evalucion2.exception.BadRequestException;
import com.tecsup.Evalucion2.exception.ResourceNotFoundException;
import com.tecsup.Evalucion2.repository.CategoriaRepository;
import com.tecsup.Evalucion2.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
    }

    public Producto guardar(Producto producto) {
        Categoria categoria = obtenerCategoria(producto);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto producto = buscarPorId(id);
        Categoria categoria = obtenerCategoria(datos);

        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());
        producto.setStock(datos.getStock());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productoRepository.delete(producto);
    }

    private Categoria obtenerCategoria(Producto producto) {
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            throw new BadRequestException("Debe indicar la categoría del producto");
        }

        return categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }
}