package com.toledo.mi_app.model

data class Producto(
    val id: String = "",           // ID único de Firestore
    val codigo: String = "",
    val descripcion: String = "",
    val precio: String = "",       // String para facilitar input, luego convertimos
    val cantidad: String = "",
    val estado: Boolean = true,    // Activo/Inactivo
    val userId: String = ""        // Para filtrar por usuario
)