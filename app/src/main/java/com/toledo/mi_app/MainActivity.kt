package com.toledo.mi_app // Asegúrate de que este sea el paquete correcto de tu app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.toledo.mi_app.ui.AuthApp // Asume que AuthApp está en el subpaquete .auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            // Llamamos a nuestra app con navegación (AuthApp)
            AuthApp()
        }
    }
}