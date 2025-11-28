package com.toledo.mi_app.auth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.toledo.mi_app.ui.ProductoScreen
import com.toledo.mi_app.viewmodel.ProductoViewModel

// Definiciones de las pantallas (Destinos)
object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PRODUCTO_FORM = "producto_form"
}

// ------------------ FUNCIÓN PRINCIPAL DE LA APP ------------------
@Composable
fun AuthApp() {
    val navController = rememberNavController()

    // Creamos el ViewModel aquí para que sobreviva a la navegación y se comparta entre Home y Formulario
    val productoViewModel: ProductoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        // ------------------ 1. LOGIN ------------------
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ------------------ 2. REGISTRO ------------------
        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ------------------ 3. HOME (LISTADO) ------------------
        composable(Destinations.HOME) {
            HomeScreen(
                viewModel = productoViewModel,
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.HOME) { inclusive = true }
                    }
                },
                onAddProducto = {
                    navController.navigate(Destinations.PRODUCTO_FORM)
                },
                onEditProducto = {
                    navController.navigate(Destinations.PRODUCTO_FORM)
                }
            )
        }

        // ------------------ 4. FORMULARIO DE PRODUCTO ------------------
        composable(Destinations.PRODUCTO_FORM) {
            ProductoScreen(
                viewModel = productoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}