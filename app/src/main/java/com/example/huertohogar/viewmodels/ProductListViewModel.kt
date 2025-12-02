package com.example.huertohogar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.db.ProductLocalDataSource
import com.example.huertohogar.data.repository.ProductRepository
import com.example.huertohogar.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ProductRepository(
        local = ProductLocalDataSource(application.applicationContext)
    )

    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    val products: StateFlow<List<Producto>> = _products

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadFromApi() = viewModelScope.launch {
        repo.cargarDesdeApi()
            .onSuccess { _products.value = it }
            .onFailure { _error.value = "Error al cargar API" }
    }

    fun loadFromLocal() = viewModelScope.launch {
        val list = repo.obtenerLocal()
        if (list.isEmpty()) {
            _error.value = "No hay datos locales guardados"
        } else {
            _products.value = list
        }
    }

    fun saveLocal() = viewModelScope.launch {
        repo.guardarLocal(_products.value)
    }
}
