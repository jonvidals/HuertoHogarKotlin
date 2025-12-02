package com.example.huertohogar.views

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.huertohogar.R
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.viewmodels.CartViewModel

@Composable
fun BottomNavBar(
    navController: NavHostController,
    cartViewModel: CartViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // cantidad de productos
    val items by cartViewModel.items.collectAsState()
    val count = items.sumOf { it.quantity }

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
            icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.ProductList.route,
            onClick = { navController.navigate(Screen.ProductList.route) { launchSingleTop = true } },
            icon = { Icon(painterResource(R.drawable.ic_store), contentDescription = "Catálogo") },
            label = { Text("Catálogo") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Cart.route,
            onClick = { navController.navigate(Screen.Cart.route) { launchSingleTop = true } },
            icon = {
                BadgedBox(
                    badge = {
                        if (count > 0) {
                            Badge { Text(count.toString()) }
                        }
                    }
                ) {
                    Icon(
                        painterResource(R.drawable.ic_cart),
                        contentDescription = "Carrito"
                    )
                }
            },
            label = { Text("Carrito") }
        )
    }
}
