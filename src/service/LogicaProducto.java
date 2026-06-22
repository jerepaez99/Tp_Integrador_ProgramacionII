/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entidades.Categoria;
import entidades.Producto;
import exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class LogicaProducto {
    
    
    private List<Producto> productos = new ArrayList<>();
    private LogicaCategoria logicaCategoria;
    
    public LogicaProducto(LogicaCategoria logicaCategoria){
        this.logicaCategoria = logicaCategoria;
    }

    //Menus CRUD para Producto
    
    public void addProductoInicial(Producto producto) {
        productos.add(producto);

        if (producto.getCategoria() != null) {
            producto.getCategoria().agregarProducto(producto);
        }
    }    
    
    public Producto findProductoPorID(Long id){
        Producto productoEncontrado = null;
        for (Producto producto : productos){
            if (producto.getId() == id && !producto.isEliminado()){
                productoEncontrado = producto;
                return productoEncontrado;
            }
        }
        throw new EntidadNoEncontradaException("No se encontró un producto con ese ID");
    }
    
    public void listarProductos() {
        System.out.println("=== Listado de Productos ===");

        boolean hayProductosCargados = false;

        for (Producto producto : productos) {
            if (!producto.isEliminado()) {
                String nombreCategoria = producto.getCategoria() != null 
                        ? producto.getCategoria().getNombre() 
                        : "Sin categoría";

                System.out.println(
                        "ID: " + producto.getId()
                        + " | Nombre: " + producto.getNombre()
                        + " | Descripcion: " + producto.getDescripcion()
                        + " | Precio: $" + producto.getPrecio()
                        + " | Stock: " + producto.getStock()
                        + " | Imagen: " + producto.getImagen()
                        + " | Disponible: " + (producto.isDisponible() ? "Sí" : "No")
                        + " | Categoría: " + nombreCategoria
                );

                hayProductosCargados = true;
            }
        }

        if (!hayProductosCargados) {
            throw new EntidadNoEncontradaException("No hay productos cargados");
        }
    }
    
    public void listarProductosPorCategoria(long idCategoria) {
        Categoria categoria = logicaCategoria.findCategoriaPorID(idCategoria);

        System.out.println("=== Productos de la categoría: " + categoria.getNombre() + " ===");

        boolean hayProductosCargados = false;

        for (Producto producto : productos) {
            if (!producto.isEliminado()
                    && producto.getCategoria() != null
                    && producto.getCategoria().getId() == idCategoria) {

                System.out.println(
                        "ID: " + producto.getId()
                        + " | Nombre: " + producto.getNombre()
                        + " | Descripcion: " + producto.getDescripcion()
                        + " | Precio: $" + producto.getPrecio()
                        + " | Stock: " + producto.getStock()
                        + " | Imagen: " + producto.getImagen()
                        + " | Disponible: " + (producto.isDisponible() ? "Sí" : "No")
                );

                hayProductosCargados = true;
            }
        }

        if (!hayProductosCargados) {
            throw new EntidadNoEncontradaException("No hay productos cargados para esta categoría");
        }
    }
    
    public void crearProducto(String nombre, String descripcion, double precio, int stock, String imagen, boolean disponible, long idCategoria) {
        Categoria categoriaProducto = logicaCategoria.findCategoriaPorID(idCategoria);

        Producto nuevoProducto = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoriaProducto);

        productos.add(nuevoProducto);
        categoriaProducto.agregarProducto(nuevoProducto);

        System.out.println("El producto "
                + nuevoProducto.getNombre()
                + " con el ID "
                + nuevoProducto.getId()
                + " ha sido creado y agregado a "
                + categoriaProducto.getNombre());
    }
    
    public void editarPrecioProducto(Long id, double precio){
        Producto productoEditar = findProductoPorID(id);
        
        productoEditar.setPrecio(precio);
        
        System.out.println("El precio se ha editado correctamente");
    }
    
    public void editarDisponibilidadProducto(Long id, boolean disponibilidad){
        Producto productoEditar = findProductoPorID(id);
        
        productoEditar.setDisponible(disponibilidad);
        
        System.out.println("La dosponibilidad se ha editado correctamente");
    }    
    
    public void editarStockProducto(Long id, int stock){
        Producto productoEditar = findProductoPorID(id);
        
        productoEditar.setStock(stock);
        
        System.out.println("El stock se ha editado correctamente");
    }
    
    public void editarCategoriaProducto(Long idProducto, Long idCategoria) {
        Producto productoEditar = findProductoPorID(idProducto);
        Categoria viejaCategoria = productoEditar.getCategoria();
        Categoria nuevaCategoria = logicaCategoria.findCategoriaPorID(idCategoria);

        productoEditar.setCategoria(nuevaCategoria);
        nuevaCategoria.agregarProducto(productoEditar);
        viejaCategoria.eliminarProducto(productoEditar);

        System.out.println("Categoría correctamente modificada");
    }    
    
    public void eliminarProducto(long id) {
        Producto productoEliminar = findProductoPorID(id);

        productoEliminar.setEliminado(true);

        System.out.println("El producto " + productoEliminar.getNombre() + " ha sido eliminado");
    }  
    
}
