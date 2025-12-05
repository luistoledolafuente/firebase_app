package com.toledo.mi_app.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Gestionar Producto", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(24.dp))

        // Campo Nombre
        OutlinedTextField(
            value = viewModel.productoActual.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre del Producto") },
            leadingIcon = { Icon(Icons.Default.Label, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Campo Categoría
        OutlinedTextField(
            value = viewModel.productoActual.categoria,
            onValueChange = { viewModel.onCategoriaChange(it) },
            label = { Text("Categoría") },
            leadingIcon = { Icon(Icons.Default.Category, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Campo Precio
        OutlinedTextField(
            value = viewModel.productoActual.precio,
            onValueChange = { viewModel.onPrecioChange(it) },
            label = { Text("Precio (S/.)") },
            leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(16.dp))

        // Campo Stock
        OutlinedTextField(
            value = viewModel.productoActual.stock,
            onValueChange = { viewModel.onStockChange(it) },
            label = { Text("Stock Disponible") },
            leadingIcon = { Icon(Icons.Default.Inventory, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(32.dp))

        // Botón Guardar
        Button(
            onClick = {
                if (viewModel.productoActual.nombre.isNotEmpty()) {
                    viewModel.guardarProducto {
                        onNavigateBack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Producto")
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onNavigateBack) {
            Text("Cancelar")
        }
    }
}