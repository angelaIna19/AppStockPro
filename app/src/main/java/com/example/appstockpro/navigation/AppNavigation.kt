package com.example.appstockpro.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appstockpro.screens.LoginScreen
import com.example.appstockpro.viewmodel.StockViewModel

/**
 * Gestor de navegación de la aplicación (NavHost).
 * Aquí se definen todas las pantallas y cómo interactúan con el único ViewModel compartido.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    // Instancia única del ViewModel compartida por todas las pantallas
    val viewModel: StockViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla 1: Login
        composable(Screen.Login.route) {
            LoginScreen { nombre ->
                // Navegación con paso de argumentos (Nombre del operario)
                navController.navigate(Screen.Catalogo.createRoute(nombre))
            }
        }

        // Pantalla 2: Catálogo de Inventario
        composable(
            route = Screen.Catalogo.route,
            arguments = listOf(navArgument("nombreOperario") { type = NavType.StringType })
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombreOperario") ?: ""
            // Marcador de posición para la Pantalla 2
            PlaceholderScreen("Catálogo - Operario: $nombre")
        }

        // Pantalla 3: Edición de Stock
        composable(
            route = Screen.Edicion.route,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("productoId") ?: 0
            // Marcador de posición para la Pantalla 3
            PlaceholderScreen("Edición Producto ID: $id")
        }

        // Pantalla 4: Reporte Financiero
        composable(Screen.Reporte.route) {
            // Marcador de posición para la Pantalla 4
            PlaceholderScreen("Reporte Financiero")
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    androidx.compose.material3.Text(text = "Proximamente: $text")
}
