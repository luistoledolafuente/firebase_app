package com.toledo.mi_app.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.toledo.mi_app.ui.auth.LoginScreen
import com.toledo.mi_app.ui.auth.RegisterScreen
import com.toledo.mi_app.ui.home.HomeScreen
import com.toledo.mi_app.ui.product.ProductoScreen
import com.toledo.mi_app.viewmodel.ProductoViewModel

// Definiciones de las pantallas (Destinos)
object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val PRODUCTO_FORM = "producto_form"
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()

    // ViewModel compartido
    val productoViewModel: ProductoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {

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

        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

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

        composable(Destinations.PRODUCTO_FORM) {
            ProductoScreen(
                viewModel = productoViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
