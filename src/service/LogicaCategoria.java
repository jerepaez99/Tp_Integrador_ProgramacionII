/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entidades.Categoria;
import entidades.Producto;
import exception.CadenaInvalidaException;
import exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class LogicaCategoria {
    
    private List<Categoria> categorias = new ArrayList<>();
    
    
    public List<Categoria> getCategorias(){
        return categorias;
    }

    public void addCategoriaInicial(Categoria categoria) {
        categorias.add(categoria);
    }
    
    public Categoria findCategoriaPorID(Long id){
        Categoria categoriaEncontrada = null;
        
        for (Categoria categoria : categorias){
            if (categoria.getId() == id && !categoria.isEliminado()){
                categoriaEncontrada = categoria;
                return categoriaEncontrada;
            }
            
        }
        throw new EntidadNoEncontradaException("No se encontró una categoría con ese ID");
    } 
    
    public void listarCategorias() {
        System.out.println("=== Listado de Categorías ===");

        boolean hayCategoriasCargadas = false;

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                System.out.println(
                        "ID: " + categoria.getId()
                        + " | Nombre: " + categoria.getNombre()
                        + " | Descripción: " + categoria.getDescripcion()
                        + " | Cantidad de productos: " + categoria.getProductosValidos().size()
                );

                hayCategoriasCargadas = true;
            }
        }

        if (!hayCategoriasCargadas) {
            throw new EntidadNoEncontradaException("No hay categorías cargadas");
        }
    }
    
    public void crearCategoria(String nombre, String descripcion){
            for (Categoria categoria : categorias){
                if (categoria.getNombre().equalsIgnoreCase(nombre) && !categoria.isEliminado()) {
                    throw new CadenaInvalidaException("El nombre ya existe, intente nuevamente");
                }
            }
            
            Categoria nuevaCategoria = new Categoria(nombre, descripcion);
            categorias.add(nuevaCategoria);
            System.out.println("Se ha creado correctamente la categoria " 
                    + nuevaCategoria.getNombre() 
                    + " con el ID: " + nuevaCategoria.getId());
    }  
    
    public void editarNombreCategoria(long id, String nuevoNombre) {
        Categoria categoriaEditar = findCategoriaPorID(id);

        categoriaEditar.setNombre(nuevoNombre);

        System.out.println("Nombre correctamente modificado");
    }

    public void editarDescripcionCategoria(long id, String nuevaDescripcion) {
        Categoria categoriaEditar = findCategoriaPorID(id);

        categoriaEditar.setDescripcion(nuevaDescripcion);

        System.out.println("Descripción correctamente modificada");
    }
    
    public void eliminarCategoria(long id) {
        Categoria categoriaEliminar = findCategoriaPorID(id);

        categoriaEliminar.setEliminado(true);
        
        for (Producto producto : categoriaEliminar.getProductos()){
            producto.setEliminado(true);
        }

        System.out.println("La categoría " + categoriaEliminar.getNombre() + " ha sido eliminada");
    }
    
}
