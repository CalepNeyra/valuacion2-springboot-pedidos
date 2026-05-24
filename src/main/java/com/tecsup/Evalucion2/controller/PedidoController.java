package com.tecsup.Evalucion2.controller;

import com.tecsup.Evalucion2.dto.PedidoRequest;
import com.tecsup.Evalucion2.entity.Pedido;
import com.tecsup.Evalucion2.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listar();
    }

    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping
    public Pedido registrar(@Valid @RequestBody PedidoRequest request) {
        return pedidoService.registrar(request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
    }
}