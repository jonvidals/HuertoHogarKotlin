package com.example.huertohogar.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.huertohogar.viewmodels.MainViewModel
import com.example.huertohogar.viewmodels.CartViewModel
import com.example.huertohogar.viewmodels.ProductListViewModel
import com.example.huertohogar.views.CartScreen
import com.example.huertohogar.views.HomeScreenWithDrawer
import com.example.huertohogar.views.LoginScreen
import com.example.huertohogar.views.ProductDetailScreen
import com.example.huertohogar.views.ProductListScreen
import com.example.huertohogar.views.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    productListViewModel: ProductListViewModel,
    cartViewModel: CartViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onContinue = { navController.navigate("home") }
            )
        }

        composable("home") {
            HomeScreenWithDrawer(
                viewModel = mainViewModel,
                onNavigateToCatalog = { navController.navigate("product_list") }
            )
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                viewModel = mainViewModel
            )
        }

        composable("product_list") {
            ProductListScreen(
                listViewModel = productListViewModel,
                cartViewModel = cartViewModel,
                onProductClick = { id -> navController.navigate("detail/$id") },
                onBackClick = { navController.popBackStack() },
                onCartClick = { navController.navigate("cart") }
            )
        }

        composable(
            route = "detail/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            ProductDetailScreen(
                productId = id,
                onNavigateUp = { navController.navigateUp() }
            )
        }

        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
