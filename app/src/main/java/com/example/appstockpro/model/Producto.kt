package com.example.appstockpro.model

/**
 * El paquete Model contiene la definición de nuestra entidad Producto.
 * Es el contrato que asegura que tanto el ViewModel como las Vistas manejen la misma estructura de datos
 * Clase de datos que representa un producto en el inventario.
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    var stockActual: Int
)
