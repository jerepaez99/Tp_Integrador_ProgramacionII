/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entidades.Usuario;
import enums.Rol;
import exception.CadenaInvalidaException;
import exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeremías Paez
 */
public class LogicaUsuario {
    
    private List<Usuario> usuarios = new ArrayList<>();
    
    public void addUsuarioInicial(Usuario usuario){
        usuarios.add(usuario);
    }
    
    public Usuario findUsuarioPorID(long id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id && !usuario.isEliminado()) {
                return usuario;
            }
        }

        throw new EntidadNoEncontradaException("No se encontró un usuario activo con ese ID");
    }


    public void listarUsuarios() {
        System.out.println("=== Listado de Usuarios ===");

        boolean hayUsuariosCargados = false;

        for (Usuario usuario : usuarios) {
            if (!usuario.isEliminado()) {
                System.out.println(
                        "ID: " + usuario.getId()
                        + " | Nombre: " + usuario.getNombre()
                        + " | Apellido: " + usuario.getApellido()
                        + " | Mail: " + usuario.getMail()
                        + " | Celular: " + usuario.getCelular()
                        + " | Rol: " + usuario.getRol()
                );

                hayUsuariosCargados = true;
            }
        }

        if (!hayUsuariosCargados) {
            throw new EntidadNoEncontradaException("No hay usuarios cargados");
        }
    }    
    public void crearUsuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        validarMail(mail);
        validarMailUnico(mail, null);

        Usuario nuevoUsuario = new Usuario(nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(nuevoUsuario);

        System.out.println("Se ha creado correctamente el usuario "
                + nuevoUsuario.getNombre()
                + " " + nuevoUsuario.getApellido()
                + " con el ID: " + nuevoUsuario.getId());
    }

    public void editarNombreUsuario(long id, String nuevoNombre) {
        Usuario usuario = findUsuarioPorID(id);
        usuario.setNombre(nuevoNombre);

        System.out.println("Nombre correctamente modificado");
    }

    public void editarApellidoUsuario(long id, String nuevoApellido) {
        Usuario usuario = findUsuarioPorID(id);
        usuario.setApellido(nuevoApellido);

        System.out.println("Apellido correctamente modificado");
    }
    
    public void editarContraseniaUsuario(long id, String nuevaContrasenia) {
        Usuario usuario = findUsuarioPorID(id);
        usuario.setContrasenia(nuevaContrasenia);

        System.out.println("Contraseña correctamente modificada");
    }    

    public void editarMailUsuario(long id, String nuevoMail) {
        Usuario usuario = findUsuarioPorID(id);

        validarMail(nuevoMail);
        validarMailUnico(nuevoMail, id);

        usuario.setMail(nuevoMail);

        System.out.println("Mail correctamente modificado");
    }

    public void editarCelularUsuario(long id, String nuevoCelular) {
        Usuario usuario = findUsuarioPorID(id);
        usuario.setCelular(nuevoCelular);

        System.out.println("Celular correctamente modificado");
    }

    public void editarRolUsuario(long id, Rol nuevoRol) {
        Usuario usuario = findUsuarioPorID(id);
        usuario.setRol(nuevoRol);

        System.out.println("Rol correctamente modificado");
    }

    public void eliminarUsuario(long id) {
        Usuario usuario = findUsuarioPorID(id);

        usuario.setEliminado(true);

        System.out.println("El usuario "
                + usuario.getNombre()
                + " " + usuario.getApellido()
                + " ha sido eliminado");
    }

    public void validarMail(String mail) {
        if (mail == null || mail.trim().isEmpty()) {
            throw new CadenaInvalidaException("El mail no puede estar vacío");
        }

        if (!mail.contains("@") || !mail.contains(".")) {
            throw new CadenaInvalidaException("El formato del mail no es válido");
        }
    }

    public void validarMailUnico(String mail, Long idActual) {
        for (Usuario usuario : usuarios) {
            boolean mismoMail = usuario.getMail() != null && usuario.getMail().equalsIgnoreCase(mail.trim());
            boolean usuarioActivo = !usuario.isEliminado();
            boolean esOtroUsuario = idActual == null || usuario.getId() != idActual;

            if (mismoMail && usuarioActivo && esOtroUsuario) {
                throw new CadenaInvalidaException("Ya existe un usuario activo con ese mail");
            }
        }
    }    
    
}
