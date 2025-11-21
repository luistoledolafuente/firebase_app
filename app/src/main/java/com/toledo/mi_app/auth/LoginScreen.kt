package com.toledo.mi_app.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview // Importación necesaria para el Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.toledo.mi_app.R // Asegúrate de que este import sea correcto para tu proyecto


@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ------------------ TÍTULO SUPERIOR ------------------
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
                .fillMaxWidth(), // Añadido para que el texto se centre correctamente
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Programación Móvil - Tecsup",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        // ------------------ CONTENIDO PRINCIPAL ------------------
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // IMAGEN Tecsup
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // Reemplaza R.drawable.tecsup con el recurso real si es necesario
                contentDescription = "Logo Tecsup",
                modifier = Modifier
                    .size(120.dp) // Ajusta el tamaño si quieres más grande o más pequeño
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Iniciar Sesión",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        // En el preview, esta línea no hará nada ya que no hay contexto de Android real.
                        // Solo se evalúa en la ejecución normal de la app.
                        Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true // Iniciar carga

                    // Esta lógica de Firebase no se ejecutará correctamente en el Preview.
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            isLoading = false // Finalizar carga
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Inicio exitoso ✅", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: ${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                },
                enabled = !isLoading, // Deshabilitar el botón durante la carga
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isLoading) "Cargando.." else "Ingresar")
            }

            TextButton(
                onClick = onNavigateToRegister
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }


        // ------------------ PIE DE PÁGINA ------------------
        Text(
            text = "Juan León S. - Tecsup",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

// ------------------ PREVIEW ------------------

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Es una buena práctica envolver el Preview con el tema de tu aplicación
    // Si tienes un tema definido, descomenta o usa el nombre de tu tema.
    // MiAppTheme {
    LoginScreen(
        onNavigateToRegister = { /* No-op para Preview */ },
        onLoginSuccess = { /* No-op para Preview */ }
    )
    // }
}