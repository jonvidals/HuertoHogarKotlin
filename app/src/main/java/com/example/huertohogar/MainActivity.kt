package com.example.huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.huertohogar.navigation.AppNavHost
import com.example.huertohogar.navigation.NavigationEvent
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.ui.theme.HuertohogarTheme
import com.example.huertohogar.viewmodels.CartViewModel
import com.example.huertohogar.viewmodels.MainViewModel
import com.example.huertohogar.viewmodels.ProductListViewModel
import com.example.huertohogar.views.BottomNavBar

class MainActivity : ComponentActivity() {

    private val cartViewModel: CartViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val productListViewModel: ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HuertohogarTheme {
                RootApp(
                    mainViewModel = mainViewModel,
                    productListViewModel = productListViewModel,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootApp(
    mainViewModel: MainViewModel,
    productListViewModel: ProductListViewModel,
    cartViewModel: CartViewModel
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(Unit) {
        mainViewModel.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.NavigateTo -> {
                    val route = when (val screen = event.screen) {
                        is Screen.Detail -> "detail/${screen.itemId}"
                        else -> screen.route
                    }
                    navController.navigate(route)
                }
                NavigationEvent.PopBackStack -> navController.popBackStack()
                NavigationEvent.NavigateUp -> navController.navigateUp()
            }
        }
    }

    // LOGIN → sin drawer ni bottom bar
    if (currentRoute == Screen.Login.route || currentRoute == null) {
        AppNavHost(
            navController = navController,
            mainViewModel = mainViewModel,
            productListViewModel = productListViewModel,
            cartViewModel = cartViewModel
        )
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Text(
                        text = "Huerto Hogar",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    NavigationDrawerItem(
                        label = { Text("Inicio") },
                        selected = currentRoute == Screen.Home.route,
                        onClick = { navController.navigate(Screen.Home.route) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Catálogo") },
                        selected = currentRoute == Screen.ProductList.route,
                        onClick = { navController.navigate(Screen.ProductList.route) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Carrito") },
                        selected = currentRoute == Screen.Cart.route,
                        onClick = { navController.navigate(Screen.Cart.route) }
                    )
                }
            }
        ) {
            Scaffold(
                bottomBar = {
                    BottomNavBar(
                        navController = navController,
                        cartViewModel = cartViewModel
                    )
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    AppNavHost(
                        navController = navController,
                        mainViewModel = mainViewModel,
                        productListViewModel = productListViewModel,
                        cartViewModel = cartViewModel   // ← FALTABA AQUÍ
                    )
                }
            }
        }
    }
}
