/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class Usuario extends Base{
    
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        this.pedidos = new ArrayList<>();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public void agregarPedido(Pedido p){
        if (p != null && !pedidos.contains(p)){
            pedidos.add(p);
            if (p.getUsuario()!= this){
                p.setUsuario(this);
                }
            }
        }
    
    public void eliminarPedido(Pedido p){
        if (p != null && pedidos.contains(p)){
            pedidos.remove(p);
            if (p.getUsuario() == this) {
                p.setUsuario(null);
                p.setEliminado(true);
            }
        }
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", celular=" + celular + ", contrasenia=" + contrasenia + ", rol=" + rol + ", pedidos=" + pedidos + '}';
    }
    
    
}
