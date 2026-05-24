package com.tecsup.Evalucion2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequest {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @Valid
    @NotNull(message = "El pedido debe tener detalles")
    @Size(min = 1, message = "El pedido debe contener al menos un producto")
    private List<DetallePedidoRequest> detalles;
}