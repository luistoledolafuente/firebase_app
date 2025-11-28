package com.toledo.mi_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Text("Gestionar Producto", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.productoActual.codigo,
            onValueChange = { viewModel.onCodigoChange(it) },
            label = { Text("Código") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.productoActual.descripcion,
            onValueChange = { viewModel.onDescChange(it) },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.productoActual.precio,
            onValueChange = { viewModel.onPrecioChange(it) },
            label = { Text("Precio") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = viewModel.productoActual.cantidad,
            onValueChange = { viewModel.onCantChange(it) },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Estado (Activo): ")
            Switch(
                checked = viewModel.productoActual.estado,
                onCheckedChange = { viewModel.onEstadoChange(it) }
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.guardarProducto {
                    onNavigateBack()
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