# Food Store - Sistema de Gestión de Pedidos de Comida

Trabajo Práctico Integrador desarrollado para la materia **Programación 2**

## Descripción del proyecto

**Food Store** es una aplicación de consola desarrollada en Java que permite gestionar productos, categorías, usuarios y pedidos de un negocio de comidas.

El sistema fue desarrollado aplicando conceptos de Programación Orientada a Objetos, utilizando clases, herencia, encapsulamiento, interfaces, enums, colecciones dinámicas y manejo de excepciones propias.

La información se almacena en memoria durante la ejecución del programa mediante colecciones, sin utilizar una base de datos real.

## Tecnologías utilizadas

* Java 25
* NetBeans IDE
* Programación Orientada a Objetos
* Colecciones en memoria
* Manejo de excepciones propias
* Aplicación de consola

## Funcionalidades principales

El sistema permite realizar operaciones CRUD sobre las siguientes entidades:

* Categorías
* Productos
* Usuarios
* Pedidos

Además, permite:

* Crear pedidos asociados a usuarios existentes.
* Agregar productos y cantidades a un pedido.
* Actualizar estado y forma de pago de un pedido.
* Realizar baja lógica mediante el atributo `eliminado`.
* Validar entradas incorrectas desde consola.
* Controlar IDs inexistentes.
* Evitar cierres inesperados mediante excepciones personalizadas.

## Estructura del proyecto

El proyecto se encuentra organizado en paquetes según responsabilidad:

```text
src/
├── entidades/
│   ├── Base.java
│   ├── Categoria.java
│   ├── Producto.java
│   ├── Usuario.java
│   ├── Pedido.java
│   └── DetallePedido.java
│
├── enums/
│   ├── Rol.java
│   ├── Estado.java
│   └── FormaPago.java
│
├── exception/
│   ├── NumeroInvalidoException.java
│   ├── CadenaInvalidaException.java
│   └── EntidadNoEncontradaException.java
│
├── interfaces/
│   └── Calculable.java
│
├── service/
│   ├── LogicaCategoria.java
│   ├── LogicaProducto.java
│   ├── LogicaUsuario.java
│   └── LogicaPedido.java
│
└── main/
    ├── TPI.java
    ├── MenuConsola.java
    └── Validaciones.java
```

## Arquitectura aplicada

El proyecto se organiza separando responsabilidades:

* Las entidades representan el modelo principal del sistema.
* Las clases de lógica administran las colecciones y las operaciones CRUD.
* La clase `MenuConsola` gestiona la interacción con el usuario mediante consola.
* La clase `Validaciones` centraliza la lectura y validación de datos ingresados.
* Las excepciones propias permiten controlar errores frecuentes de forma clara y personalizada.

Esta organización permite mantener un código más ordenado y fácil de mantener.

## Modelo de entidades

El sistema está basado en un modelo UML propuesto por la cátedra.

Las entidades principales son:

* `Categoria`
* `Producto`
* `Usuario`
* `Pedido`
* `DetallePedido`

Todas heredan de la clase abstracta `Base`, que contiene atributos comunes como:

* `id`
* `eliminado`
* `createdAt`

También se implementa la interfaz `Calculable`, utilizada por la clase `Pedido` para calcular el total final.

## Reglas de negocio implementadas

* No se permite crear productos con precio negativo.
* No se permite crear productos con stock negativo.
* No se permite crear detalles de pedido con cantidad menor o igual a cero.
* No se permite crear pedidos sin un usuario válido.
* El mail del usuario debe ser único.
* Las entidades eliminadas se marcan con baja lógica.
* Las entidades eliminadas no se muestran en los listados.
* Si se ingresa un ID inexistente, el sistema informa el error correspondiente.
* Si se ingresa un dato inválido, el sistema muestra un mensaje y continúa funcionando.

## Cómo ejecutar el proyecto

1. Descargar o clonar el repositorio.
2. Abrir el proyecto en NetBeans.
3. Verificar que el proyecto utilice Java 21+.
4. Ejecutar la clase principal:

```text
main.TPI
```

5. Utilizar el menú de consola para navegar por las opciones del sistema.

## Menú principal

Al iniciar el programa, se muestra un menú similar al siguiente:

```text
=== SISTEMA DE PEDIDOS FOOD STORE ===
1. Categorías
2. Productos
3. Usuarios
4. Pedidos
0. Salir
Seleccione una opción:
```

Cada opción permite acceder a un submenú con operaciones de listado, creación, edición y eliminación.

## Datos de prueba

El sistema incluye datos iniciales cargados desde la clase `MenuConsola`, permitiendo probar el funcionamiento del programa sin necesidad de cargar todos los registros manualmente.

Entre los datos de prueba se incluyen:

* Categorías
* Productos
* Usuarios
* Pedidos con detalles

## Documentación

La documentación técnica y académica del proyecto se encuentra incluida en el archivo PDF correspondiente.

## Video demostrativo

Link al video demostrativo del sistema: https://drive.google.com/open?id=1MtPLZ23_TF1iXK4gsGZDJH-0gCMyje-W&usp=drive_fs 


## Autor

Nombre del estudiante: Jeremías Páez

Carrera: Tecnicatura Universitaria en Programación

Materia: Programación 2

Institución: Universidad Tecnológica Nacional

Año: 2026
