package com.example.huertohogar.navigation

sealed class NavigationEvent {
    data class NavigateTo(val screen: Screen) : NavigationEvent()
    data object NavigateUp : NavigationEvent()
    data object PopBackStack : NavigationEvent()
}
