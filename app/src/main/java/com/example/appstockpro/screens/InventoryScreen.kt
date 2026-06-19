package com.example.appstockpro.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appstockpro.model.Producto
import com.example.appstockpro.viewmodel.StockViewModel

/**
 * Pantalla 2: Catálogo de Inventario.
 * Muestra el listado de productos y permite filtrar por stock crítico.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    viewModel: StockViewModel,
    operario: String,
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToReport: () -> Unit
) {
    // Control de filtro de stock
    var mostrarSoloCritico by remember { mutableStateOf(false) }

    val listaAVisualizar = if (mostrarSoloCritico) {
        viewModel.obtenerProductosEnRiesgo()
    } else {
        viewModel.listaProductos
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("Operario: ")
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )) {
                                    append(operario)
                                }
                            },
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            // Acceso al Reporte Financiero
            FloatingActionButton(
                onClick = onNavigateToReport,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(Icons.Default.Description, contentDescription = null)
                    Text("Reporte", style = MaterialTheme.typography.labelSmall, fontSize = 10.sp)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Filtros de visualización
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { mostrarSoloCritico = false },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!mostrarSoloCritico) MaterialTheme.colorScheme.primary else Color.Transparent,
                        contentColor = if (!mostrarSoloCritico) Color.White else MaterialTheme.colorScheme.onSurface
                    ),
                    border = if (mostrarSoloCritico) BorderStroke(1.dp, Color.LightGray) else null
                ) {
                    Text("Ver Todo", fontWeight = FontWeight.SemiBold)
                }

                OutlinedButton(
                    onClick = { mostrarSoloCritico = true },
                    modifier = Modifier.weight(1f).height(48.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (mostrarSoloCritico) MaterialTheme.colorScheme.errorContainer else Color.Transparent,
                        contentColor = if (mostrarSoloCritico) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, if (mostrarSoloCritico) MaterialTheme.colorScheme.error else Color.LightGray)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WarningAmber, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Stock Crítico", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Listado Dinámico de Productos
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
 * Tarjeta individual para cada producto del catálogo.
 */
@Composable
fun ItemProducto(producto: Producto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row {
                    Text(
                        text = "Precio unitario: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = "$${producto.precio}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                
                Row {
                    Text(
                        text = "Stock actual: ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    val stockColor = if (producto.stockActual < 5) Color.Red else Color.Unspecified
                    Text(
                        text = "${producto.stockActual}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = stockColor,
                        fontWeight = if (producto.stockActual < 5) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Editar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
