package com.toledo.mi_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toledo.mi_app.model.Producto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var listaProductos by mutableStateOf<List<Producto>>(emptyList())
        private set

    // Estado para el formulario
    var productoActual by mutableStateOf(Producto())

    fun obtenerProductos() {
        val userId = auth.currentUser?.uid ?: return

        // REQUISITO: Filtrar por usuario autenticado
        db.collection("productos")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val productos = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Producto::class.java)?.copy(id = doc.id)
                    }
                    listaProductos = productos
                }
            }
    }

    fun guardarProducto(onSuccess: () -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val nuevoProducto = productoActual.copy(userId = userId)

        if (productoActual.id.isEmpty()) {
            // Crear nuevo
            db.collection("productos").add(nuevoProducto)
                .addOnSuccessListener { onSuccess() }
        } else {
            // Actualizar existente
            db.collection("productos").document(productoActual.id).set(nuevoProducto)
                .addOnSuccessListener { onSuccess() }
        }
    }

    fun eliminarProducto(id: String) {
        db.collection("productos").document(id).delete()
    }

    // Función para preparar el formulario para editar
    fun prepararEdicion(producto: Producto) {
        productoActual = producto
    }

    // Función para limpiar el formulario
    fun limpiarFormulario() {
        productoActual = Producto()
    }

    // Actualizadores de estado para los TextField
    fun onCodigoChange(nuevo: String) { productoActual = productoActual.copy(codigo = nuevo) }
    fun onDescChange(nuevo: String) { productoActual = productoActual.copy(descripcion = nuevo) }
    fun onPrecioChange(nuevo: String) { productoActual = productoActual.copy(precio = nuevo) }
    fun onCantChange(nuevo: String) { productoActual = productoActual.copy(cantidad = nuevo) }
    fun onEstadoChange(nuevo: Boolean) { productoActual = productoActual.copy(estado = nuevo) }
}