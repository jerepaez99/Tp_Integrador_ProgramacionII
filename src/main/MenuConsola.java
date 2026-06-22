/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entidades.Categoria;
import entidades.Pedido;
import entidades.Producto;
import entidades.Usuario;
import enums.Estado;
import enums.FormaPago;
import enums.Rol;
import exception.CadenaInvalidaException;
import exception.EntidadNoEncontradaException;
import exception.NumeroInvalidoException;
import service.Validaciones;
import service.LogicaCategoria;
import service.LogicaPedido;
import service.LogicaProducto;
import service.LogicaUsuario;

/**
 *
 * @author Jeremías Paez
 */
public class MenuConsola {
    
    private Validaciones validador = new Validaciones();
    private LogicaCategoria logicaCategoria = new LogicaCategoria();
    private LogicaProducto logicaProducto = new LogicaProducto(logicaCategoria);
    private LogicaUsuario logicaUsuario = new LogicaUsuario();
    private LogicaPedido logicaPedido = new LogicaPedido(logicaUsuario, logicaProducto);
    
    public void iniciar() {
        cargarDatosIniciales();
        menuPrincipal();
    }
    
    public void cargarDatosIniciales(){
        Categoria bebidas = new Categoria("Bebidas", "Bebidas frías, calientes y sin alcohol");
        Categoria comidas = new Categoria("Comidas", "Platos principales y comidas rápidas");
        Categoria postres = new Categoria("Postres", "Opciones dulces y productos de pastelería");
        Categoria snacks = new Categoria("Snacks", "Productos rápidos para acompañar pedidos");

        logicaCategoria.addCategoriaInicial(bebidas);
        logicaCategoria.addCategoriaInicial(comidas);
        logicaCategoria.addCategoriaInicial(postres);
        logicaCategoria.addCategoriaInicial(snacks);

        Producto cocaCola = new Producto("Coca Cola 500ml", 1500.0, "Gaseosa individual de 500ml", 20, "coca_cola_500ml.jpg", true, bebidas);
        Producto hamburguesa = new Producto("Hamburguesa completa", 6500.0, "Hamburguesa con queso, lechuga, tomate y papas", 12, "hamburguesa_completa.jpg", true, comidas);
        Producto brownie = new Producto("Brownie con nuez", 2800.0, "Porción de brownie artesanal con nuez", 10, "brownie_nuez.jpg", true, postres);
        Producto papasFritas = new Producto("Papas fritas", 3500.0, "Porción mediana de papas fritas", 15, "papas_fritas.jpg", true, snacks);
        
        logicaProducto.addProductoInicial(cocaCola);
        logicaProducto.addProductoInicial(hamburguesa);
        logicaProducto.addProductoInicial(brownie);
        logicaProducto.addProductoInicial(papasFritas);
        
        Usuario usuario1 = new Usuario("Juan", "Pérez", "juan.perez@mail.com", "2604000001", "1234", Rol.USUARIO);
        Usuario usuario2 = new Usuario("María", "Gómez", "maria.gomez@mail.com", "2604000002", "1234", Rol.USUARIO);
        Usuario usuario3 = new Usuario("Lucía", "Fernández", "lucia.fernandez@mail.com", "2604000003", "1234", Rol.USUARIO);
        Usuario usuario4 = new Usuario("Admin", "Sistema", "admin@foodstore.com", "2604000004", "admin123", Rol.ADMIN);

        logicaUsuario.addUsuarioInicial(usuario1);
        logicaUsuario.addUsuarioInicial(usuario2);
        logicaUsuario.addUsuarioInicial(usuario3);
        logicaUsuario.addUsuarioInicial(usuario4);

        Pedido pedido1 = new Pedido(Estado.PENDIENTE, FormaPago.EFECTIVO, usuario1);
        Pedido pedido2 = new Pedido(Estado.CONFIRMADO, FormaPago.TARJETA, usuario2);
        Pedido pedido3 = new Pedido(Estado.TERMINADO, FormaPago.TRANSFERENCIA, usuario3);
        Pedido pedido4 = new Pedido(Estado.CANCELADO, FormaPago.EFECTIVO, usuario1);
        
        pedido1.addDetallePedido(2, cocaCola);
        pedido1.addDetallePedido(1, hamburguesa);
        pedido1.calcularTotal();

        pedido2.addDetallePedido(1, brownie);
        pedido2.addDetallePedido(1, papasFritas);
        pedido2.calcularTotal();        

        pedido3.addDetallePedido(2, hamburguesa);
        pedido3.addDetallePedido(2, cocaCola);
        pedido3.calcularTotal();
        
        pedido4.addDetallePedido(1, papasFritas);
        pedido4.addDetallePedido(1, brownie);
        pedido4.calcularTotal();

        logicaPedido.addPedidoInicial(pedido1);
        logicaPedido.addPedidoInicial(pedido2);
        logicaPedido.addPedidoInicial(pedido3);
        logicaPedido.addPedidoInicial(pedido4);        
    }
    
    /*
    public String[] crud = {
        "1. Listar",
        "2. Crear",
        "3. Editar",
        "4. Eliminar",
        "0. Salir"};    
        */
    
    private void menuPrincipal() {
        int opcion = -1;

        do {
            try {
                System.out.println("\n=== SISTEMA FOOD STORE ===");
                System.out.println("1. Categorías");
                System.out.println("2. Productos");
                System.out.println("3. Usuarios");
                System.out.println("4. Pedidos");
                System.out.println("0. Salir");

                opcion = validador.leerEntero("Seleccione una opción: ");

                switch (opcion) {
                    case 1 -> menuCategorias();
                    case 2 -> menuProductos();
                    case 3 -> menuUsuarios();
                    case 4 -> menuPedidos();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Debe ingresar una opción válida");
                }

            } catch (NumeroInvalidoException |
                     CadenaInvalidaException |
                     EntidadNoEncontradaException e) {

                System.out.println(e.getMessage());
            }

        } while (opcion != 0);
    }

    public void menuCategorias() {
        
        int opcionCategoria = -1;

        do {
            try {
                System.out.println("\n=== MENU CATEGORIAS ===");
                System.out.println("1. Listar");
                System.out.println("2. Crear");
                System.out.println("3. Editar");
                System.out.println("4. Eliminar");
                System.out.println("0. Volver al menu");
                
                //Cada uno de los imputs llama al validador para impedir el cierre del programa si el usuario coloca un input distinto al esperado
                opcionCategoria = validador.leerEntero("Seleccione una opción: ");
                
                //Cada uno de los menus contiene un Switch para elegir cada una de las opciones
                switch (opcionCategoria) {
                    case 1 -> logicaCategoria.listarCategorias();

                    case 2 -> {
                        String nombre = validador.leerCadena("Ingrese el nombre de la nueva categoría: ");
                        String descripcion = validador.leerCadena("Ingrese la descripción de la nueva categoría: ");
                        
                        logicaCategoria.crearCategoria(nombre, descripcion);
                    }
                    case 3 -> {
                        logicaCategoria.listarCategorias();

                        Long idEditar = validador.leerLongPositivo("Ingrese el ID de la categoría que desea editar: ");
                        //Se llama a findCategoria para validar que el ID colocado por el usuario realmente corresponde a una categoria válida y existente
                        logicaCategoria.findCategoriaPorID(idEditar);

                        System.out.println("¿Que desea editar?");
                        System.out.println("1. Nombre");
                        System.out.println("2. Descripcion");

                        int opcionEditar = validador.leerEntero("Seleccione una opcion: ");
                        
                        switch (opcionEditar) {
                            case 1 -> {
                                String nuevoNombre = validador.leerCadena("Ingrese el nuevo nombre: ");
                                logicaCategoria.editarNombreCategoria(idEditar, nuevoNombre);
                            }
                            case 2 -> {
                                String nuevaDescripcion = validador.leerCadena("Ingrese la nueva descripción: ");
                                logicaCategoria.editarDescripcionCategoria(idEditar, nuevaDescripcion);
                            }
                            default -> {
                                System.out.println("Intente Nuevamente");
                            }
                        }
                    }

                    case 4 -> {
                        logicaCategoria.listarCategorias();

                        long idEliminar = validador.leerLongPositivo("Ingrese el ID de la categoría que desea eliminar: ");
                        
                        Categoria categoriaEliminar = logicaCategoria.findCategoriaPorID(idEliminar);
                        
                        String confirmacion = validador.leerCadena("Usted va a eliminar la categoría "
                                + categoriaEliminar.getNombre() 
                                + ". ¿Está usted seguro/a? Esto eliminará todos los productos asociados a esta categoría s/n");

                        switch (confirmacion.trim().toLowerCase()){
                            case "s" -> logicaCategoria.eliminarCategoria(idEliminar);
                            case "n" -> System.out.println("operacion cancelada.");
                            default -> System.out.println("Debe ingresar s o n. Intente nuevamente.");
                        }
                    }

                    case 0 -> System.out.println("Volviendo al menú principal...");

                    default -> System.out.println("Opcion inválida, intente nuevamente");
                }

            } catch (NumeroInvalidoException |
                     CadenaInvalidaException |
                     EntidadNoEncontradaException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } while (opcionCategoria != 0);
    }
    
        private void menuProductos() {
        
        int opcionProductos = -1;
        
        do {
            try {
                
                System.out.println("\n=== MENU PRODUCTOS ===");
                System.out.println("1. Listar");
                System.out.println("2. Crear");
                System.out.println("3. Editar");
                System.out.println("4. Eliminar");
                System.out.println("0. Volver al menu");
                
                opcionProductos = validador.leerEntero("Ingrese una opción: ");
                
                switch (opcionProductos) {
                    case 1 -> {
                        String inputListado = validador.leerCadena("Si desea listar los productos por categoría ingrese C. Para listado general ingrese G");
                        switch (inputListado.trim().toLowerCase()) {
                            case "g" -> logicaProducto.listarProductos();
                            case "c" -> {
                                logicaCategoria.listarCategorias();
                                
                                Long idCategoria = validador.leerLongPositivo("Ingrese el ID de la categoría: ");
                                
                                logicaProducto.listarProductosPorCategoria(idCategoria);
                            }
                            default -> System.out.println("Debe ingresar g o c, intente nuevamente");
                        }
                    }
                    case 2 -> {
                        String nombre = validador.leerCadena("Ingrese el nombre del nuevo producto: ");
                        String descripcion = validador.leerCadena("Ingrese la descripción del nuevo producto: ");
                        double precio = validador.leerDoubleNoNegativo("Ingrese el precio del nuevo producto: ");
                        int stock = validador.leerEnteroNoNegativo("Ingrese el stock dle nuevo producto: ");
                        String imagen = validador.leerCadena("Ingrese el nombre de la imagen del nuevo producto: ");
                        
                        logicaCategoria.listarCategorias();
                        Long idCategoria = validador.leerLongPositivo("Ingrese el ID de la categoria");
                        //Se llama a findCategoriaporID para verificar que el id ingresado por el usuario corresponde a una categoria válida
                        logicaCategoria.findCategoriaPorID(idCategoria);
                        
                        String disponibleOpcion = validador.leerCadena("¿El producto se encuentra disponible? s/n");
                        
                        boolean disponible;
                        
                        switch (disponibleOpcion.trim().toLowerCase()){
                            case "s" -> { 
                                disponible = true;
                                logicaProducto.crearProducto(nombre, descripcion, precio, stock, imagen, disponible, idCategoria);
                            }
                            case "n" -> {
                                disponible = false;
                                logicaProducto.crearProducto(nombre, descripcion, precio, stock, imagen, disponible, idCategoria);
                            }
                            default -> System.out.println("Debe ingresar s o n. Intente nuevamente.");
                        }
                        
                        
                    }
                    case 3 -> {
                        logicaProducto.listarProductos();
                        Long idProductoEditar = validador.leerLongPositivo("Ingrese el ID del producto que desea editar: ");
                        //Se llama a findProductoPorID para verificar que el id ingresado por el usuario corresponde a un producto válido
                        logicaProducto.findProductoPorID(idProductoEditar);
                        
                        System.out.println("¿Que desea editar?");
                        System.out.println("1. Precio");
                        System.out.println("2. Stock");
                        System.out.println("3. Categoría");
                        System.out.println("4. Disponibilidad");
                        
                        int editarOpcion = validador.leerEnteroNoNegativo("Ingrese una opción: ");
                        
                        switch (editarOpcion) {
                            case 1 -> {
                                double nuevoPrecio = validador.leerDoubleNoNegativo("Ingrese el nuevo precio: ");
                                logicaProducto.editarPrecioProducto(idProductoEditar, nuevoPrecio);
                            }
                            case 2 -> {
                                int nuevoStrock = validador.leerEnteroNoNegativo("Ingrese el nuevo stock: ");
                                logicaProducto.editarStockProducto(idProductoEditar, nuevoStrock);
                            }
                            case 3 -> {
                                logicaCategoria.listarCategorias();
                                Long idNuevaCategoriaProducto = validador.leerLongPositivo("Ingrese el ID de la categoria a la que quiere trasladar este producto: ");
                                logicaProducto.editarCategoriaProducto(idProductoEditar, idNuevaCategoriaProducto);
                            }
                            case 4 -> {
                                if (!logicaProducto.findProductoPorID(idProductoEditar).isDisponible()) {
                                logicaProducto.editarDisponibilidadProducto(idProductoEditar, true); 
                                } else {
                                    logicaProducto.editarDisponibilidadProducto(idProductoEditar, false); 
                                }
                            }
                            default -> System.out.println("Opción inválida, intente nuevamente");
                        }
                    }
                    case 4 -> {
                        logicaProducto.listarProductos();

                        long idEliminar = validador.leerLongPositivo("Ingrese el ID del producto que desea eliminar: ");

                        Producto productoEliminar = logicaProducto.findProductoPorID(idEliminar);
                        

                        String confirmacion = validador.leerCadena("Usted va a eliminar el producto "
                                + productoEliminar.getNombre() 
                                + ". ¿Está usted seguro/a? s/n");

                        switch (confirmacion.trim().toLowerCase()){
                            case "s" -> logicaProducto.eliminarProducto(idEliminar);
                            case "n" -> System.out.println("operacion cancelada.");
                            default -> System.out.println("Debe ingresar 's' o 'n'");
                        }
                    }
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Debe ingresar una opción válida");
                        
                }
                
            } catch (NumeroInvalidoException |
                     CadenaInvalidaException |
                     EntidadNoEncontradaException e) {

                System.out.println("Error: " + e.getMessage());
            } 
        
        } while (opcionProductos != 0 ); 
    }      
        
    private void menuUsuarios(){
        
        int opcionUsuarios = -1;
        
        do {
            try {
                System.out.println("\n=== MENU USUARIOS ===");
                System.out.println("1. Listar");
                System.out.println("2. Crear");
                System.out.println("3. Editar");
                System.out.println("4. Eliminar");
                System.out.println("0. Volver al menu");
                
                opcionUsuarios = validador.leerEnteroNoNegativo("Ingrese una opción: ");
                
                switch (opcionUsuarios) {
                    case 1 -> logicaUsuario.listarUsuarios();
                    case 2 -> {
                        String nombre = validador.leerCadenaSinNumerosNiCaracteresEspeciales("Ingrese el nombre del usuario: ");
                        String apellido = validador.leerCadenaSinNumerosNiCaracteresEspeciales("Ingrese el apellido del usuario: ");
                        String mail = validador.leerCadena("Ingrese el mail del usuario: ");
                        //Se llama a la funcion validarMail para verificar de antemano que el mail ingresado es válido, asi impedimos que se continue con el proceso en vano
                        logicaUsuario.validarMail(mail);
                        String celular = validador.leerCadena("Ingrese el celular del usuario: ");
                        String contrasenia = validador.leerCadena("Ingrese la contraseña del usuario: ");
                        Rol rol = leerRol();

                        logicaUsuario.crearUsuario(nombre, apellido, mail, celular, contrasenia, rol);
                    }
                    case 3 -> {
                        logicaUsuario.listarUsuarios();
                        
                        long idUsuarioEditar = validador.leerLongPositivo("Ingrese el ID del ususario que desea editar: ");
                        //Se llama a findUsuarioPorID de antemano para verificar que el ID colocado corresponda a un usuario válido
                        logicaUsuario.findUsuarioPorID(idUsuarioEditar);
                        
                        System.out.println("¿Que desea editar?");
                        System.out.println("1. Nombre");
                        System.out.println("2. Apellido");
                        System.out.println("3. Mail");  
                        System.out.println("4. Celular");
                        System.out.println("5. Contraseña");
                        System.out.println("6. Rol");  
                        
                        int opcionEditar = validador.leerEnteroNoNegativo("Seleccione una opcion: ");
                        
                        switch (opcionEditar){
                            case 1 -> {
                                String nuevoNombre = validador.leerCadena("Ingrese el nuevo nombre: ");
                                logicaUsuario.editarNombreUsuario(idUsuarioEditar, nuevoNombre);
                            }
                            case 2 -> {
                                String nuevoApellido = validador.leerCadena("Ingrese el nuevo apellido: ");
                                logicaUsuario.editarApellidoUsuario(idUsuarioEditar, nuevoApellido);
                            }
                            case 3 -> {
                                String nuevoMail = validador.leerCadena("Ingrese el nuevo mail: ");
                                logicaUsuario.validarMail(nuevoMail);
                                logicaUsuario.validarMailUnico(nuevoMail, idUsuarioEditar);
                                logicaUsuario.editarMailUsuario(idUsuarioEditar, nuevoMail);                               
                            }
                            case 4 -> {
                                String nuevoCelular = validador.leerCadena("Ingrese el nuevo celular: ");
                                logicaUsuario.editarCelularUsuario(idUsuarioEditar, nuevoCelular);
                            }
                            case 5 -> {
                                String nuevaContrasenia = validador.leerCadena("Ingrese la nueva contraseña: ");
                                logicaUsuario.editarContraseniaUsuario(idUsuarioEditar, nuevaContrasenia);
                            }
                            case 6 -> {
                                Rol nuevoRol = leerRol();
                                logicaUsuario.editarRolUsuario(idUsuarioEditar, nuevoRol);
                            }
                            default -> System.out.println("Opción inválida, intente nuevamente");
                        }
                    }
                    case 4 -> {
                        logicaUsuario.listarUsuarios();
                        
                        long idEliminar = validador.leerLongPositivo("Ingrese el ID del usuario que desea eliminar: ");
                        
                        Usuario usuarioEliminar = logicaUsuario.findUsuarioPorID(idEliminar);
                        
                        String confirmacion = validador.leerCadena("Usted va a eliminar el usuario "
                                + usuarioEliminar.getMail()
                                + ". ¿Está usted seguro/a? s/n");

                        switch (confirmacion.trim().toLowerCase()){
                            case "s" -> logicaUsuario.eliminarUsuario(idEliminar);
                            case "n" -> System.out.println("operacion cancelada.");
                            default -> System.out.println("Debe ingresar 's' o 'n'");
                        } 
                    }
                    case 0 -> System.out.println("Volviendo al menu principal...");
                    default -> System.out.println("Debe ingresar una opción válida");
                    
                }
                
            } catch (NumeroInvalidoException |
                     CadenaInvalidaException |
                     EntidadNoEncontradaException e) {

                System.out.println("Error: " + e.getMessage());
        } 
            } while (opcionUsuarios != 0);
        
    }
    
    private void menuPedidos(){
        int opcionPedidos = -1;

        
        do {
            try{
                
                System.out.println("\n=== MENU PEDIDOS ===");
                System.out.println("1. Listar");
                System.out.println("2. Crear");
                System.out.println("3. Editar estado o forma de pago");
                System.out.println("4. Eliminar");
                System.out.println("0. Volver al menu");
                
                opcionPedidos = validador.leerEnteroNoNegativo("Ingrese una opcion: ");
                
                switch (opcionPedidos) {
                    case 1 -> {
                        String inputLista = validador.leerCadena("Ingrese G para listado general o U para listar por usuario: ");
                        
                        switch (inputLista.trim().toLowerCase()) {
                            case "g" -> logicaPedido.listarPedidos();
                            case "u" -> {
                                logicaUsuario.listarUsuarios();
                                long idUsuario = validador.leerLongPositivo("Ingrese la ID del usuario: ");
                                logicaPedido.listarPedidosPorUsuario(idUsuario);
                            }
                            default -> System.out.println("Opcion inválida, debe ingresar 'u' o 'g'");
                        }
                    }
                    case 2 -> {
                        logicaUsuario.listarUsuarios();
                        
                        long idUsuario = validador.leerLongPositivo("Ingrese el ID del usuario que realiza el pedido: ");
                        //Se llama a findUsuarioPorID para asegurarnos que el administrador del sistema colocó un ID que corresponde a un usuario válido antes de continuar
                        logicaUsuario.findUsuarioPorID(idUsuario);
                        
                        FormaPago formaPago = leerFormaPago();
                        
                        Pedido pedidoTemporal = logicaPedido.crearPedidoTemporal(idUsuario, formaPago);
                        
                        boolean continuar = true;
                        
                        while (continuar) {
                            logicaProducto.listarProductos();
                            
                            long idProducto = validador.leerLongPositivo("Ingrese el id del producto que desea agregar al pedido: ");
                            //Se llama a findproductoporID para asegurarnos que el ID colocado corresponde a un producto valido antes de continuar
                            logicaProducto.findProductoPorID(idProducto);
                            int cantidad = validador.leerEnteroPositivo("Ingrese la cantidad de unidades del producto: ");
                            
                            logicaPedido.agregarDetallePedido(pedidoTemporal, idProducto, cantidad);
                            
                            String inputContinuar = "";
                            
                            //Bucles anidados para asegurarnos que el flujo no se rompa
                            do {
                                inputContinuar = validador.leerCadena("Desea continuar agregando otro producto: s/n");
                            switch (inputContinuar.trim().toLowerCase()) {
                                    case "s" -> continuar = true;
                                    case "n" -> continuar = false;
                                    default -> System.out.println("Debe ingresar 's' o 'n'"); 
                                }
                            } while (!inputContinuar.equalsIgnoreCase("s") && !inputContinuar.equalsIgnoreCase("n"));
                        }
                     logicaPedido.confirmarPedido(pedidoTemporal);
                    }
                    case 3 -> {
                        logicaPedido.listarPedidos();
                        
                        long idPedidoActualizar = validador.leerLongPositivo("Ingrese el ID del pedido que desea actualizar: ");
                        logicaPedido.findPedidoPorID(idPedidoActualizar);
                        
                        System.out.println("¿Que desea editar?");
                        System.out.println("1. Forma de pago");
                        System.out.println("2. Estado");
                        
                        int opcionEditar = validador.leerEnteroNoNegativo("Ingrese una opcion: ");
                        
                            switch (opcionEditar) {
                                case 1 -> {
                                    FormaPago nuevaFormaPago = leerFormaPago();
                                    logicaPedido.actualizarFormaPago(idPedidoActualizar, nuevaFormaPago);   
                                }
                                case 2 -> {
                                    Estado nuevoEstado = leerEstado();
                                    logicaPedido.actualizarEstado(idPedidoActualizar, nuevoEstado);   
                                }
                                default -> System.out.println("Debe ingresar una opción válida");
                            }
                    }
                    case 4 -> {
                        logicaPedido.listarPedidos();

                        long idEliminar = validador.leerLongPositivo("Ingrese el ID del pedido que desea eliminar: ");

                        Pedido pedidoEliminar = logicaPedido.findPedidoPorID(idEliminar);

                        String confirmacion = validador.leerCadena(
                                "Usted va a eliminar el pedido ID "
                                + pedidoEliminar.getId()
                                + ". ¿Está seguro/a? s/n: "
                        );

                        switch (confirmacion.trim().toLowerCase()) {
                            case "s" -> logicaPedido.eliminarPedido(idEliminar);
                            case "n" -> System.out.println("Operación cancelada.");
                            default -> System.out.println("Debe ingresar 's' o 'n'");
                        }
                    }
                    case 0 -> System.out.println("Volviendo al menu principal...");
                    default -> System.out.println("Debe ingresar una opción válida");
                }    
                
                
            } catch (NumeroInvalidoException
                    | CadenaInvalidaException
                    | EntidadNoEncontradaException e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } while (opcionPedidos != 0);
    }
    
    private FormaPago leerFormaPago() {
        System.out.println("Seleccione la forma de pago:");
        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");

        int opcion = validador.leerEntero("Ingrese una opción: ");

        return switch (opcion) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            case 3 -> FormaPago.EFECTIVO;
            default -> throw new NumeroInvalidoException("Forma de pago inválida");
        };
    } 

    private Rol leerRol() {
        System.out.println("Seleccione el rol:");
        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");

        int opcion = validador.leerEntero("Ingrese una opción: ");

        return switch (opcion) {
            case 1 -> Rol.ADMIN;
            case 2 -> Rol.USUARIO;
            default -> throw new NumeroInvalidoException("Rol inválido");
        };
    }
    
    private Estado leerEstado() {
        System.out.println("Seleccione el estado:");
        System.out.println("1. PENDIENTE");
        System.out.println("2. CONFIRMADO");
        System.out.println("3. TERMINADO");
        System.out.println("4. CANCELADO");

        int opcion = validador.leerEntero("Ingrese una opción: ");

        return switch (opcion) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> throw new NumeroInvalidoException("Estado inválido");
    };
}
}
    


