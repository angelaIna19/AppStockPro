package com.example.appstockpro.navigation

/**
 * Definición de las rutas de la aplicación.
 * El uso de una sealed class ayuda a evitar errores de escritura en las rutas.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Catalogo : Screen("catalogo/{nombreOperario}") {
        fun createRoute(nombre: String) = "catalogo/$nombre"
    }
    object Edicion : Screen("edicion/{productoId}") {
        fun createRoute(id: Int) = "edicion/$id"
    }
    object Reporte : Screen("reporte")
}
