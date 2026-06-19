package com.example.appstockpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.appstockpro.viewmodel.StockViewModel
import java.text.NumberFormat
import java.util.Locale

/**
 * Pantalla 4: Reporte Financiero.
 * Muestra el resumen económico calculado en bodega.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: StockViewModel,
    onNavigateBack: () -> Unit
) {
    // Consulta directa de cálculos matemáticos al ViewModel (Exigencia técnica)
    val capitalTotal = viewModel.calcularValorTotalInventario()
    val productosEnCero = viewModel.obtenerProductosEnCero()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reporte de Inventario") },
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
            verticalArrangement = Arrangement.Center
        ) {
            // Icono Financiero Decorativo
            Icon(
                imageVector = Icons.Default.MonetizationOn,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Capital Invertido Total",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )

            // Formateo de moneda regional con separador de miles
            val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
                minimumFractionDigits = 2
                maximumFractionDigits = 2
            }
            val formattedTotal = formatter.format(capitalTotal)

            Text(
                text = "$$formattedTotal",
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Indicador de alerta para productos agotados
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (productosEnCero > 0) 
                        MaterialTheme.colorScheme.errorContainer 
                    else 
                        MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Productos Agotados",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Stock en cero actualmente",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text(
                        text = "$productosEnCero",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (productosEnCero > 0) Color.Red else Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Catálogo")
            }
        }
    }
}
