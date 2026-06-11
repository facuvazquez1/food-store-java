# Food Store - Sistema de Gestion de Pedidos

Trabajo Practico Integrador de Programacion 2.

## Descripcion

Food Store es una aplicacion de consola desarrollada en Java que permite gestionar categorias, productos, usuarios y pedidos de comida.

El sistema trabaja completamente en memoria utilizando colecciones de Java. No utiliza base de datos ni sistema de login.

## Tecnologias utilizadas

* Java 21
* Apache NetBeans
* Java with Ant
* Programacion Orientada a Objetos
* Colecciones Java
* Scanner para entrada por consola

## Funcionalidades principales

El sistema permite realizar operaciones CRUD sobre:

* Categorias
* Productos
* Usuarios
* Pedidos

Tambien permite:

* Asociar productos a categorias.
* Asociar pedidos a usuarios.
* Agregar detalles a un pedido.
* Calcular automaticamente el total del pedido.
* Validar datos ingresados por consola.
* Manejar errores mediante excepciones propias.
* Aplicar baja logica usando el atributo `eliminado`.

## Estructura del proyecto

```text
src/
└── integrado/
    └── prog2/
        ├── Main.java
        ├── entities/
        │   ├── Base.java
        │   ├── Calculable.java
        │   ├── Categoria.java
        │   ├── Producto.java
        │   ├── Usuario.java
        │   ├── Pedido.java
        │   └── DetallePedido.java
        ├── enums/
        │   ├── Rol.java
        │   ├── Estado.java
        │   └── FormaPago.java
        ├── exception/
        │   ├── CampoObligatorioException.java
        │   ├── CantidadInvalidaException.java
        │   ├── CategoriaConProductosActivosException.java
        │   ├── EntidadDuplicadaException.java
        │   ├── EntidadNoEncontradaException.java
        │   ├── OperacionInvalidaException.java
        │   ├── PedidoSinUsuarioException.java
        │   ├── PrecioInvalidoException.java
        │   └── StockInvalidoException.java
        ├── service/
        │   ├── CategoriaService.java
        │   ├── ProductoService.java
        │   ├── UsuarioService.java
        │   └── PedidoService.java
        └── ui/
            ├── ConsolaUtils.java
            ├── MenuPrincipal.java
            ├── CategoriaMenu.java
            ├── ProductoMenu.java
            ├── UsuarioMenu.java
            └── PedidoMenu.java
```

## Reglas de negocio implementadas

* El precio de un producto no puede ser negativo.
* El stock de un producto no puede ser negativo.
* Un pedido no puede crearse sin usuario.
* Un pedido debe tener al menos un detalle.
* La cantidad de cada detalle debe ser mayor a cero.
* El mail del usuario debe ser unico.
* No se puede eliminar una categoria que tenga productos activos asociados.
* Las bajas son logicas: los objetos no se eliminan de la coleccion, solo se marca `eliminado = true`.
* Los listados muestran solamente registros activos.
* Los productos eliminados no rompen los detalles de pedidos historicos.
* Los usuarios eliminados no rompen pedidos historicos.

## Como ejecutar el proyecto

### Desde Apache NetBeans

1. Abrir Apache NetBeans.
2. Seleccionar `File > Open Project`.
3. Buscar la carpeta del proyecto `FoodStore`.
4. Abrir el proyecto.
5. Click derecho sobre el proyecto.
6. Seleccionar `Clean and Build`.
7. Seleccionar `Run Project`.

### Desde consola

Luego de compilar el proyecto, ejecutar:

```bash
java -jar dist/FoodStore.jar
```

## Menu principal

Al iniciar el programa se muestra el siguiente menu:

```text
=== SISTEMA DE PEDIDOS (FOOD STORE) ===
1. Categorias
2. Productos
3. Usuarios
4. Pedidos
0. Salir
Seleccione:
```

Cada seccion cuenta con su propio submenu para listar, crear, editar y eliminar registros.

## Ejemplo de uso

Flujo basico recomendado:

1. Crear una categoria.
2. Crear un producto asociado a esa categoria.
3. Crear un usuario.
4. Crear un pedido asociado a ese usuario.
5. Agregar productos al pedido.
6. Verificar el total calculado.
7. Listar el pedido creado.

Ejemplo de calculo:

```text
Producto: Banana
Precio: 900.0
Cantidad: 2
Total: 1800.0
```

## Validaciones destacadas

El sistema valida:

* Opciones fuera de rango en los menus.
* Letras cuando se espera un numero.
* IDs inexistentes.
* Precio negativo.
* Stock negativo.
* Cantidad invalida en detalles.
* Categoria duplicada.
* Mail duplicado.
* Categoria inexistente al crear producto.
* Usuario inexistente al crear pedido.
* Stock insuficiente al crear detalle de pedido.

## Aclaraciones

* El sistema no utiliza base de datos.
* La informacion se guarda solamente mientras el programa esta en ejecucion.
* Al cerrar el programa, los datos cargados se pierden.
* No se implementa login ni autenticacion.
* No se descuentan unidades del stock al crear pedidos; solo se valida que haya stock suficiente.
* Las funcionalidades opcionales, como filtros por categoria o usuario, no fueron implementadas.

## Video de explicación

[Video demostrativo](https://drive.google.com/file/d/1NFu914BBMljv-DoY24gs3akgagPYwWkX/view?usp=drive_link)

## Autor

* Estudiante: Facundo Gaston Vazquez
* Comision: 10
* Materia: Programacion 2
* Proyecto: Trabajo Practico Integrador - Food Store
