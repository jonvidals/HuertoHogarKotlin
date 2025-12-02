package com.example.huertohogar.navigation

sealed class Screen(val route: String) {

    data object Login : Screen("login")
    data object Home : Screen("home")
    data object ProductList : Screen("product_list")
    data object Cart : Screen("cart")
    data object Profile : Screen("profile")

    data class Detail(val itemId: String) : Screen("detail/$itemId") {
        companion object {
            const val baseRoute = "detail"
            const val arg = "itemId"
        }
    }
}
