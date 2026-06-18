package com.example.appstockpro.model

/**
 * Clase de datos que representa un producto en el inventario.
 * Contiene la información básica requerida por el examen.
 * 
 * @param id Identificador único del producto.
 * @param nombre Nombre descriptivo del producto.
 * @param descripcion Detalle de lo que es el producto.
 * @param precio Valor unitario en dólares.
 * @param stockActual Cantidad disponible en bodega (usamos var para permitir cambios).
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    var stockActual: Int
)
