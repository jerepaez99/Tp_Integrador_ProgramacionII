/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class Categoria extends Base {
    
    private String nombre;
    private String descripcion;
    private List<Producto> productos;
    
    public Categoria(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        } else {
            System.out.println("Nombre inválido, no se realizó ningun cambio");
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()){
            this.descripcion = descripcion;
        } else {
            System.out.println("Descripción inválida, no se realizó ningun cambio");
        }
    }

    public List<Producto> getProductos() {
        return productos;
    }
    
    public List<Producto> getProductosValidos() {
        List <Producto> productosValidos = new ArrayList<>();
        
        for (Producto producto : productos) {
            if (!producto.isEliminado() && producto.isDisponible()) {
                productosValidos.add(producto);
            }
        }
        return productosValidos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    
    public void agregarProducto(Producto producto){
        if (producto != null && !productos.contains(producto)) {
            productos.add(producto);
            producto.setCategoria(this);
        }
    }
    
    public void eliminarProducto(Producto producto){
        if (productos.contains(producto)) {
            productos.remove(producto);
        } else {
            System.out.println("No se ha podido eliminar el producto");
        }
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + " | Categoría: " + nombre
                + " | Descripción: " + descripcion
                + " | Productos activos: " + productos.size();
    }
    
    
    
}
