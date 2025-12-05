package com.toledo.mi_app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toledo.mi_app.model.Producto

class ProductoViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    var listaProductos by mutableStateOf<List<Producto>>(emptyList())
        private set

    var productoActual by mutableStateOf(Producto())

    fun obtenerProductos() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("products") // OJO: El PDF pide colección "products" (Pág 2, punto 3)
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
            db.collection("products").add(nuevoProducto) // Colección "products"
                .addOnSuccessListener { onSuccess() }
        } else {
            db.collection("products").document(productoActual.id).set(nuevoProducto)
                .addOnSuccessListener { onSuccess() }
        }
    }

    fun eliminarProducto(id: String) {
        db.collection("products").document(id).delete()
    }

    fun prepararEdicion(producto: Producto) {
        productoActual = producto
    }

    fun limpiarFormulario() {
        productoActual = Producto()
    }

    fun onNombreChange(nuevo: String) { productoActual = productoActual.copy(nombre = nuevo) }
    fun onPrecioChange(nuevo: String) { productoActual = productoActual.copy(precio = nuevo) }
    fun onStockChange(nuevo: String) { productoActual = productoActual.copy(stock = nuevo) }
    fun onCategoriaChange(nuevo: String) { productoActual = productoActual.copy(categoria = nuevo) }
}