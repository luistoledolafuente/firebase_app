package com.toledo.mi_app.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.toledo.mi_app.viewmodel.ProductoViewModel

@Composable
fun ProductoScreen(
    viewModel: ProductoViewModel,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gestión de Producto", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        // Campo: NOMBRE (Obligatorio)
        OutlinedTextField(
            value = viewModel.productoActual.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: CATEGORÍA
        OutlinedTextField(
            value = viewModel.productoActual.categoria,
            onValueChange = { viewModel.onCategoriaChange(it) },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: PRECIO
        OutlinedTextField(
            value = viewModel.productoActual.precio,
            onValueChange = { viewModel.onPrecioChange(it) },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo: STOCK
        OutlinedTextField(
            value = viewModel.productoActual.stock,
            onValueChange = { viewModel.onStockChange(it) },
            label = { Text("Stock") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                // Validación simple sugerida
                if (viewModel.productoActual.nombre.isNotEmpty()) {
                    viewModel.guardarProducto {
                        onNavigateBack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        TextButton(onClick = onNavigateBack) {
            Text("Cancelar")
        }
    }
}