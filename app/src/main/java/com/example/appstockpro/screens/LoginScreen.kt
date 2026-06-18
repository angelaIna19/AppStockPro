package com.example.appstockpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Pantalla 1: Ingreso de Operario.
 * Permite al trabajador ingresar su nombre para acceder al sistema.
 * 
 * @param onIngresar Función lambda que se ejecuta al presionar el botón, pasando el nombre.
 */
@Composable
fun LoginScreen(onIngresar: (String) -> Unit) {
    // Estado local para el nombre del operario
    var nombre by remember { mutableStateOf("") }
    
    // Lógica (Estado): El botón solo se habilita si el nombre tiene al menos 3 caracteres
    val esValido = nombre.trim().length >= 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // UI: Título limpio
        Text(
            text = "Bienvenido a StockPro",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // UI: TextField para el nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Operario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // UI: Button de "Ingresar al Sistema"
        // Navegación: Al presionar, se viaja a la Pantalla 2
        Button(
            onClick = { onIngresar(nombre) },
            enabled = esValido, // Se habilita según la lógica de 3 caracteres
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar al Sistema")
        }
    }
}
