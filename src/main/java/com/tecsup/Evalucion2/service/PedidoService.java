package com.tecsup.Evalucion2.service;

import com.tecsup.Evalucion2.dto.DetallePedidoRequest;
import com.tecsup.Evalucion2.dto.PedidoRequest;
import com.tecsup.Evalucion2.entity.Cliente;
import com.tecsup.Evalucion2.entity.DetallePedido;
import com.tecsup.Evalucion2.entity.Pedido;
import com.tecsup.Evalucion2.entity.Producto;
import com.tecsup.Evalucion2.exception.ResourceNotFoundException;
import com.tecsup.Evalucion2.exception.StockInsuficienteException;
import com.tecsup.Evalucion2.repository.ClienteRepository;
import com.tecsup.Evalucion2.repository.PedidoRepository;
import com.tecsup.Evalucion2.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado"));
    }

    @Transactional
    public Pedido registrar(PedidoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFecha(LocalDate.now());
        pedido.setCliente(cliente);

        List<DetallePedido> detalles = new ArrayList<>();
        double total = 0.0;

        for (DetallePedidoRequest item : request.getDetalles()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto: " + producto.getNombre()
                );
            }

            double subtotal = item.getCantidad() * producto.getPrecio();

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(subtotal);
            detalle.setPedido(pedido);

            detalles.add(detalle);
            total += subtotal;
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        return pedidoRepository.save(pedido);
    }

    public void eliminar(Long id) {
        Pedido pedido = buscarPorId(id);
        pedidoRepository.delete(pedido);
    }
}