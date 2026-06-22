/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.NumeroInvalidoException;
import interfaces.Calculable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class Pedido extends Base implements Calculable{
    
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles = new ArrayList<>();
    private Usuario usuario;

    public Pedido(Estado estado, FormaPago formaPago, Usuario usuario) {
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.total = getTotal();
        this.formaPago = formaPago;
        this.usuario = usuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        if (this.usuario == usuario){
            return;
        }
        
        if (this.usuario != null){
            this.usuario.eliminarPedido(this);
        }
        
        this.usuario = usuario;
        
        if (usuario != null && !usuario.getPedidos().contains(this)){
            usuario.agregarPedido(this);
        }
    }

    @Override
    public void calcularTotal() {
        this.total = 0.0;
        
        for (DetallePedido detalle : detalles){
            this.total += detalle.getSubtotal();
        }
    }
    
    public void addDetallePedido(int cantidad, Producto producto) {
        if (producto == null || producto.isEliminado()) {
            throw new EntidadNoEncontradaException("Producto inválido");
        }

        if (!producto.isDisponible()) {
            throw new EntidadNoEncontradaException("El producto no se encuentra disponible");
        }

        if (cantidad <= 0) {
            throw new NumeroInvalidoException("La cantidad debe ser mayor a 0");
        }

        if (producto.getStock() < cantidad) {
            throw new NumeroInvalidoException("Stock insuficiente para el producto: " + producto.getNombre());
        }

        detalles.add(new DetallePedido(cantidad, producto));
        calcularTotal();
    }

    
    public DetallePedido findDetallePedidoByProducto(Producto producto){
        DetallePedido encontrado = null;
        int i = 0;
        while (i < detalles.size() && !this.detalles.get(i).getProducto().equals(producto)){
            i++;
        }
        if (i < detalles.size()){
            encontrado = this.detalles.get(i);
        }
        return encontrado;
    }
        
        public void deleteDetallePedidoByProducto(Producto producto){
            DetallePedido detalle = findDetallePedidoByProducto(producto);
            
            if (detalle != null) {
                detalles.remove(detalle);
                calcularTotal();
            }
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + getId() +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", formaPago=" + formaPago +
                ", usuario=" + (usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario") +
                '}';
    }
    
    
        
}
