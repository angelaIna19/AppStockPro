package com.example.appstockpro.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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

    // Estado para el stock real del producto (usamos String para permitir el input manual)
    var stockInput by remember { mutableStateOf(producto.stockActual.toString()) }
    val stockActualValue = stockInput.toIntOrNull() ?: 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edición de Stock", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre y Descripción centrados
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjeta de Stock Actual
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCF7F2)),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Stock Actual",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    // INPUT GRANDE: Ahora el usuario puede tocar el número y escribir
                    BasicTextField(
                        value = stockInput,
                        onValueChange = { 
                            if (it.all { char -> char.isDigit() } && it.length <= 4) stockInput = it 
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.displayLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 100.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text(
                        text = "unidades",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Panel de ajuste de unidades
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Sección de Restar
                    Button(
                        onClick = { 
                            if (stockActualValue > 0) {
                                stockInput = (stockActualValue - 1).toString()
                            }
                        },
                        enabled = stockActualValue > 0,
                        modifier = Modifier.size(64.dp),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("-", fontSize = 32.sp, fontWeight = FontWeight.Light)
                    }

                    // Divisor Vertical
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .width(1.dp)
                            .background(Color(0xFFE0E0E0))
                    )

                    // Sección de Sumar
                    Button(
                        onClick = { 
                            stockInput = (stockActualValue + 1).toString()
                        },
                        modifier = Modifier.size(64.dp),
                        shape = MaterialTheme.shapes.medium,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("+", fontSize = 32.sp, fontWeight = FontWeight.Light)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Guardar y Volver
            Button(
                onClick = {
                    viewModel.actualizarStock(productoId, stockActualValue)
                    onNavigateBack()
                },
                enabled = stockInput.isNotBlank(), // Validación: No permite guardar si está vacío
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Guardar y Volver", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
