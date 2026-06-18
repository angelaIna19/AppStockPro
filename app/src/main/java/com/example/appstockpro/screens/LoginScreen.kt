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
    
    // Lógica (Estado): 
    // 1. Debe tener al menos 3 caracteres.
    // 2. Solo debe permitir letras y espacios (bloqueando números).
    val tieneLongitudValida = nombre.trim().length >= 3
    val soloContieneLetras = nombre.all { it.isLetter() || it.isWhitespace() }
    val esValido = tieneLongitudValida && soloContieneLetras && nombre.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // UI: Título
        Text(
            text = "Bienvenido a StockPro",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // UI: TextField para el nombre con validación visual
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Operario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nombre.isNotEmpty() && !soloContieneLetras,
            supportingText = {
                if (nombre.isNotEmpty() && !soloContieneLetras) {
                    Text("El nombre solo debe contener letras")
                }
            }
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
