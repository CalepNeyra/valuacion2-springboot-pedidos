package com.tecsup.Evalucion2.service;

import com.tecsup.Evalucion2.entity.Cliente;
import com.tecsup.Evalucion2.exception.BadRequestException;
import com.tecsup.Evalucion2.exception.ResourceNotFoundException;
import com.tecsup.Evalucion2.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    public Cliente guardar(Cliente cliente) {
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente datos) {
        Cliente cliente = buscarPorId(id);

        if (!cliente.getCorreo().equalsIgnoreCase(datos.getCorreo())
                && clienteRepository.existsByCorreo(datos.getCorreo())) {
            throw new BadRequestException("El correo ya está registrado");
        }

        cliente.setNombres(datos.getNombres());
        cliente.setApellidos(datos.getApellidos());
        cliente.setCorreo(datos.getCorreo());
        cliente.setTelefono(datos.getTelefono());
        cliente.setDireccion(datos.getDireccion());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}