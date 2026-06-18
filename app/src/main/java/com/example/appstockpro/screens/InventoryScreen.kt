package com.example.appstockpro.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appstockpro.model.Producto
import com.example.appstockpro.viewmodel.StockViewModel

/**
 * Pantalla 2: Catálogo de Inventario.
 * Muestra la lista de productos y permite filtrar por stock crítico.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    viewModel: StockViewModel,
    operario: String,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToReport: () -> Unit
) {
    // Estado para controlar qué lista mostrar (Todo o Stock Crítico)
    var mostrarSoloCritico by remember { mutableStateOf(false) }

    // Obtenemos la lista filtrada o completa según el estado del botón
    val listaAVisualizar = if (mostrarSoloCritico) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.listaProductos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Operario: $operario", style = MaterialTheme.typography.titleMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            // Requisito: Botón Flotante para ir a la Pantalla 4 (Reporte)
            FloatingActionButton(onClick = onNavigateToReport) {
                Icon(Icons.Default.Info, contentDescription = "Ver Reporte")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // UI: Filtros (Botones)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { mostrarSoloCritico = false },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!mostrarSoloCritico) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Ver Todo")
                }
                Button(
                    onClick = { mostrarSoloCritico = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (mostrarSoloCritico) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Stock Crítico")
                }
            }

            // Requisito: Lista (LazyColumn) con tarjetas (Card)
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaAVisualizar) { producto ->
                    ItemProducto(
                        producto = producto,
                        onClick = { onNavigateToEdit(producto.id) }
                    )
                }
            }
        }
    }
}

/**
 * Componente para cada tarjeta de producto.
 */
@Composable
fun ItemProducto(producto: Producto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Precio unitario: $${producto.precio}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            // Requisito: Si el stock es menor a 5, el texto de la cantidad debe ponerse en Rojo
            val stockColor = if (producto.stockActual < 5) Color.Red else Color.Unspecified
            
            Text(
                text = "Stock actual: ${producto.stockActual}",
                style = MaterialTheme.typography.bodyLarge,
                color = stockColor,
                fontWeight = if (producto.stockActual < 5) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
