package com.example.appstockpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appstockpro.navigation.AppNavigation
import com.example.appstockpro.ui.theme.AppStockProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppStockProTheme {
                // Llamamos al gestor de navegación principal
                AppNavigation()
            }
        }
    }
}
