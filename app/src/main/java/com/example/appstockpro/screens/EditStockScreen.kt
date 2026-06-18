package com.example.appstockpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appstockpro.viewmodel.StockViewModel

/**
 * Pantalla 3: Edición de Stock.
 * Permite modificar la cantidad de un producto específico.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStockScreen(
    viewModel: StockViewModel,
    productoId: Int,
    onNavigateBack: () -> Unit
) {
    // Buscamos el producto en el ViewModel usando el ID recibido
    val producto = viewModel.obtenerProducto(productoId)

    // Si el producto no existe, mostramos un error (aunque no debería pasar)
    if (producto == null) {
        Text("Producto no encontrado")
        return
    }

    // Estado local para manejar el stock mientras editamos antes de guardar
    // Inicializamos con el stock actual del producto
    var stockTemporal by remember { mutableStateOf(producto.stockActual) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edición de Stock") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Requisito: Información en tamaño de texto grande
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "Stock Actual", style = MaterialTheme.typography.labelLarge)
            
            // Requisito: Stock actual resaltado
            Text(
                text = "$stockTemporal",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Requisito: Interacción con botones para Sumar (+1) y Restar (-1)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Validación: El botón de restar se deshabilita si el stock es cero
                Button(
                    onClick = { if (stockTemporal > 0) stockTemporal-- },
                    enabled = stockTemporal > 0,
                    modifier = Modifier.size(64.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("-1", fontSize = 20.sp)
                }

                Button(
                    onClick = { stockTemporal++ },
                    modifier = Modifier.size(64.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("+1", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Requisito: Botón de "Guardar y Volver"
            Button(
                onClick = {
                    // Lógica: Actualizar directamente en el ViewModel
                    viewModel.actualizarStock(productoId, stockTemporal)
                    onNavigateBack() // Ejecuta popBackStack() definido en AppNavigation
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Guardar y Volver")
            }
        }
    }
}
