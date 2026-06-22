/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Jeremías Paez
 */
public class DetallePedido extends Base{
    
    private int cantidad;
    private double subtotal;
    private Producto producto;
    
    public DetallePedido(int cantidad, Producto producto){
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return calcularSubtotal();
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    private double calcularSubtotal(){
        return cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "id=" + getId() + "cantidad=" + cantidad + ", subtotal=" + getSubtotal() + ", producto=" + producto.getNombre() + '}';
    }


    
}
