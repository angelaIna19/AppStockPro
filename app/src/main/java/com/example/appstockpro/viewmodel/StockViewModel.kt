package com.example.appstockpro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.appstockpro.model.Producto

/**
 * StockViewModel: El cerebro de la aplicación.
 * Gestiona el estado global del inventario y la lógica de negocio.
 * Sigue el patrón MVVM exigido.
 */
class StockViewModel : ViewModel() {

    // Usamos mutableStateListOf para que Compose detecte cambios automáticamente.
    val listaProductos = mutableStateListOf(
        Producto(1, "Laptop Pro", "Computadora de alto rendimiento para desarrollo", 1200.0, 10),
        Producto(2, "Mouse Gamer", "Mouse ergonómico con sensor óptico", 25.0, 3), // Stock Crítico (< 5)
        Producto(3, "Teclado Mecánico", "Switches rojos silenciosos", 80.0, 15),
        Producto(4, "Monitor 4K", "Pantalla de 27 pulgadas ultra HD", 450.0, 4),    // Stock Crítico (< 5)
        Producto(5, "Auriculares BT", "Cancelación de ruido activa", 150.0, 8),
        Producto(6, "Webcam HD", "Cámara para streaming 1080p", 60.0, 0)          // Stock en Cero
    )

    // --- FUNCIONES DE NEGOCIO ---

    /**
     * Busca un producto por su ID único.
     */
    fun obtenerProducto(id: Int): Producto? {
        return listaProductos.find { it.id == id }
    }

    /**
     * Actualiza el stock de un producto específico.
     * Validación: No permite que el stock sea menor a cero.
     */
    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        val index = listaProductos.indexOfFirst { it.id == id }
        if (index != -1 && nuevaCantidad >= 0) {
            // Reemplazamos el objeto para que Compose detecte el cambio de estado
            listaProductos[index] = listaProductos[index].copy(stockActual = nuevaCantidad)
        }
    }

    /**
     * Calcula el valor total de la inversión en bodega.
     * Fórmula: Sumatoria de (Precio * Stock) de todos los productos.
     * Requisito: Los cálculos pertenecen al ViewModel, no a la Vista.
     */
    fun calcularValorTotalInventario(): Double {
        return listaProductos.sumOf { it.precio * it.stockActual }
    }

    /**
     * Obtiene la lista de productos que tienen menos de 5 unidades.
     * Utilizado para el filtro "Stock Crítico" de la Pantalla 2.
     */
    fun obtenerProductosEnRiesgo(): List<Producto> {
        return listaProductos.filter { it.stockActual < 5 }
    }

    /**
     * Cuenta cuántos productos tienen stock igual a cero.
     * Requisito para la Pantalla 4 (Reporte).
     */
    fun obtenerProductosEnCero(): Int {
        return listaProductos.count { it.stockActual == 0 }
    }
}
