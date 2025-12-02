package com.example.huertohogar.viewmodels

import androidx.lifecycle.ViewModel
import com.example.huertohogar.navigation.NavigationEvent
import com.example.huertohogar.navigation.Screen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel : ViewModel() {

    private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun navigateTo(screen: Screen) {
        _navigationEvents.trySend(NavigationEvent.NavigateTo(screen))
    }

    fun navigateUp() {
        _navigationEvents.trySend(NavigationEvent.NavigateUp)
    }

    fun popBack() {
        _navigationEvents.trySend(NavigationEvent.PopBackStack)
    }
}
