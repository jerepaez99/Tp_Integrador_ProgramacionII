/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entidades.DetallePedido;
import entidades.Pedido;
import entidades.Producto;
import entidades.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.NumeroInvalidoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jeremías Paez
 */
public class LogicaPedido {
    
    private List<Pedido> pedidos = new ArrayList<>();
    
    private LogicaUsuario logicaUsuario;
    private LogicaProducto logicaProducto;
    
    public LogicaPedido(LogicaUsuario logicaUsuario, LogicaProducto logicaProducto){
        this.logicaProducto = logicaProducto;
        this.logicaUsuario = logicaUsuario;
    }
    
    public void addPedidoInicial(Pedido pedido) {
        pedidos.add(pedido);

        if (pedido.getUsuario() != null) {
            pedido.getUsuario().agregarPedido(pedido);
        }
    }    
    
    public Pedido findPedidoPorID(long id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id && !pedido.isEliminado()) {
                return pedido;
            }
        }

        throw new EntidadNoEncontradaException("No se encontró un pedido activo con ese ID");
    }

    public void listarPedidos() {
        System.out.println("=== Listado de Pedidos ===");

        boolean hayPedidosCargados = false;

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()) {
                String nombreUsuario = pedido.getUsuario() != null
                        ? pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido()
                        : "Sin usuario";

                System.out.println(
                        "ID: " + pedido.getId()
                        + " | Usuario: " + nombreUsuario
                        + " | Estado: " + pedido.getEstado()
                        + " | Forma de pago: " + pedido.getFormaPago()
                        + " | Total: $" + pedido.getTotal()
                        + " | Fecha: " + pedido.getFecha()
                );

                hayPedidosCargados = true;
            }
        }

        if (!hayPedidosCargados) {
            throw new EntidadNoEncontradaException("No hay pedidos cargados");
        }
    }
    
    public void listarPedidosPorUsuario(long idUsuario) {
        Usuario usuario = logicaUsuario.findUsuarioPorID(idUsuario);

        System.out.println("=== Pedidos del usuario: " 
                + usuario.getNombre() + " " + usuario.getApellido() + " ===");

        boolean hayPedidosCargados = false;

        for (Pedido pedido : pedidos) {
            if (!pedido.isEliminado()
                    && pedido.getUsuario() != null
                    && pedido.getUsuario().getId() == idUsuario) {

                System.out.println(
                        "ID: " + pedido.getId()
                        + " | Estado: " + pedido.getEstado()
                        + " | Forma de pago: " + pedido.getFormaPago()
                        + " | Total: $" + pedido.getTotal()
                        + " | Fecha: " + pedido.getFecha()
                );

                hayPedidosCargados = true;
            }
        }

        if (!hayPedidosCargados) {
            throw new EntidadNoEncontradaException("No hay pedidos cargados para este usuario");
        }
    }

    public Pedido crearPedidoTemporal(long idUsuario, FormaPago formaPago){
        Usuario usuario = logicaUsuario.findUsuarioPorID(idUsuario);
        
        return new Pedido(Estado.PENDIENTE, formaPago, usuario);
    }
    
    public void agregarDetallePedido(Pedido pedido, long idProducto, int cantidad){
        Producto productoDetalle = logicaProducto.findProductoPorID(idProducto);
        
        if (pedido == null) {
            throw new EntidadNoEncontradaException("Pedido inválido");
        }
        
        pedido.addDetallePedido(cantidad, productoDetalle);
        
    }
    
    public void confirmarPedido(Pedido nuevoPedido){
        
        if (nuevoPedido == null) {
            throw new EntidadNoEncontradaException("Pedido inválido");
        }
        
        if (nuevoPedido.getDetalles().isEmpty()){
            throw new EntidadNoEncontradaException("El pedido debe tener al menos un detalle");
        }
        
        validarStockTotalPedido(nuevoPedido);
        descontarStock(nuevoPedido);
        
        nuevoPedido.calcularTotal();
        
        pedidos.add(nuevoPedido);
        
        if (nuevoPedido.getUsuario() != null){
            nuevoPedido.getUsuario().agregarPedido(nuevoPedido);
        }
        
        System.out.println("Pedido creado correctamente con el ID: "
                + nuevoPedido.getId()
                + " total = "
                + nuevoPedido.getTotal());
    }
    
    public void actualizarFormaPago(long idPedido, FormaPago formaPago){
        Pedido pedidoActualizar = findPedidoPorID(idPedido);
        
        pedidoActualizar.setFormaPago(formaPago);
        
        System.out.println("Pedido actualizado correctamente");
    }
    
    public void actualizarEstado(long idPedido, Estado estado){
        Pedido pedidoActualizar = findPedidoPorID(idPedido);
        
        pedidoActualizar.setEstado(estado);
        
        System.out.println("Pedido actualizado correctamente");
    }    
    
    public void eliminarPedido(long idPedido) {
        Pedido pedido = findPedidoPorID(idPedido);

        pedido.setEliminado(true);

        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setEliminado(true);
        }

        System.out.println("Pedido eliminado correctamente");
    }
    
    private void validarStockTotalPedido(Pedido pedido) {
        Map<Producto, Integer> cantidadesPorProducto = new HashMap<>();

        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();

            int cantidadActual = cantidadesPorProducto.getOrDefault(producto, 0);
            cantidadesPorProducto.put(producto, cantidadActual + detalle.getCantidad());
        }

        for (Map.Entry<Producto, Integer> entry : cantidadesPorProducto.entrySet()) {
            Producto producto = entry.getKey();
            int cantidadSolicitada = entry.getValue();

            if (producto.getStock() < cantidadSolicitada) {
                throw new NumeroInvalidoException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            
            if (!producto.isDisponible()) {
                throw new EntidadNoEncontradaException("El producto: "+ producto.getNombre() + " no se encuentra disponible");
            }
        }
    }    

    private void descontarStock(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto producto = detalle.getProducto();
            int nuevoStock = producto.getStock() - detalle.getCantidad();

            producto.setStock(nuevoStock);
        }
    }
}    
    
