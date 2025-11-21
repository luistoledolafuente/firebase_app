package com.toledo.mi_app.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Definiciones de las pantallas (Destinos)
object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
}

// ------------------ FUNCIÓN PRINCIPAL DE LA APP ------------------
@Composable
fun AuthApp() {
    // Crea y recuerda el NavController, que gestiona el back stack y la navegación
    val navController = rememberNavController()

    // Llama al gráfico de navegación principal
    AuthNavGraph(navController)
}

// ------------------ GRAFO DE NAVEGACIÓN ------------------
@Composable
fun AuthNavGraph(navController: NavHostController) {
    // Define el contenedor de navegación (NavHost)
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN // La pantalla inicial de la aplicación
    ) {
        // ------------------ 1. Pantalla de LOGIN ------------------
        composable(Destinations.LOGIN) {
            // Asume que tienes definida la función LoginScreen()
            LoginScreen(
                onNavigateToRegister = {
                    // Navega a la pantalla de registro
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    // Navega a la pantalla HOME y elimina la pantalla de LOGIN del stack
                    // (inclusive = true) asegura que LOGIN sea borrada.
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ------------------ 2. Pantalla de REGISTRO ------------------
        composable(Destinations.REGISTER) {
            // Asume que tienes definida la función RegisterScreen()
            RegisterScreen(
                onRegisterSuccess = {
                    // Tras el registro exitoso, navega a HOME y borra el registro del stack
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    // Vuelve a la pantalla anterior (LOGIN)
                    navController.popBackStack()
                }
            )
        }

        // ------------------ 3. Pantalla HOME (Principal) ------------------
        composable(Destinations.HOME) {
            // Asume que tienes definida la función HomeScreen()
            HomeScreen(
                onLogout = {
                    // Tras cerrar sesión, navega a LOGIN y borra HOME del stack.
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}