package com.toledo.mi_app.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview // Importación necesaria para el Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
// import com.toledo.mi_app.ui.theme.MiAppTheme // Posiblemente necesario para el tema

@Composable
fun HomeScreen(
    onLogout: () -> Unit
) {
    // Inicializa FirebaseAuth
    val auth = FirebaseAuth.getInstance()

    // Obtiene el email del usuario actual o usa un email de ejemplo para el Preview/Fallback
    // Nota: El auth.currentUser solo es válido en tiempo de ejecución de la app.
    val email = auth.currentUser?.email ?: "usuario.ejemplo@tecsup.edu.pe"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Bienvenido/a 👋",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Sesión iniciada como: $email",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    // 1. Cierra la sesión en Firebase
                    auth.signOut()
                    // 2. Ejecuta la función de navegación
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

// ------------------ PREVIEW ------------------

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Es recomendable envolver el preview con el tema de tu aplicación para una mejor visualización.
    // MiAppTheme { // Descomenta y ajusta si tienes un tema personalizado
    HomeScreen(
        onLogout = { /* No-op para Preview */ }
    )
    // }
}