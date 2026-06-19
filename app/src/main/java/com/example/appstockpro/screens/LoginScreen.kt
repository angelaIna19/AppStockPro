package com.example.appstockpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Pantalla 1: Ingreso de Operario.
 * Proporciona una interfaz de bienvenida y valida el nombre del trabajador.
 */
@Composable
fun LoginScreen(onIngresar: (String) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    
    // Lógica de validación: 3+ letras y sin caracteres numéricos
    val tieneLongitudValida = nombre.trim().length >= 3
    val soloContieneLetras = nombre.all { it.isLetter() || it.isWhitespace() }
    val esValido = tieneLongitudValida && soloContieneLetras && nombre.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        // Icono Superior Decorativo
        Surface(
            modifier = Modifier.size(120.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        ) {
            Icon(
                imageVector = Icons.Default.Inventory2,
                contentDescription = null,
                modifier = Modifier.padding(24.dp).size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Identidad Visual
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "StockPro",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Gestión de Inventario",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Texto para el Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Operario") },
            placeholder = { Text("Ingrese su nombre") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            isError = nombre.isNotEmpty() && !soloContieneLetras,
            supportingText = {
                if (nombre.isNotEmpty() && !soloContieneLetras) {
                    Text("El nombre solo debe contener letras")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de Acción
        Button(
            onClick = { onIngresar(nombre) },
            enabled = esValido,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Continuar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pie de página de Seguridad
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.alpha(0.6f)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Su información es segura y confidencial",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
