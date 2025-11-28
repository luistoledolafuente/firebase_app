package com.toledo.mi_app.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.toledo.mi_app.viewmodel.ProductoViewModel

@Composable
fun HomeScreen(
    viewModel: ProductoViewModel, // Inyectamos el ViewModel
    onLogout: () -> Unit,
    onAddProducto: () -> Unit,
    onEditProducto: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val email = auth.currentUser?.email

    // Cargar productos al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.obtenerProductos()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.limpiarFormulario() // Limpiamos para uno nuevo
                onAddProducto()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Hola, $email", style = MaterialTheme.typography.bodyMedium)
                Button(onClick = {
                    auth.signOut()
                    onLogout()
                }) {
                    Text("Salir")
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Mis Productos", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(viewModel.listaProductos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = producto.descripcion, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Código: ${producto.codigo}")
                            Text(text = "Precio: S/. ${producto.precio} - Cant: ${producto.cantidad}")
                            Text(
                                text = if (producto.estado) "ACTIVO" else "INACTIVO",
                                color = if (producto.estado) Color.Green else Color.Red
                            )

                            Row(modifier = Modifier.align(Alignment.End)) {
                                IconButton(onClick = {
                                    viewModel.prepararEdicion(producto)
                                    onEditProducto()
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = { viewModel.eliminarProducto(producto.id) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}