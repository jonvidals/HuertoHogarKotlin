package com.example.huertohogar.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogar.data.db.ProductLocalDataSource
import com.example.huertohogar.data.repository.ProductRepository
import com.example.huertohogar.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = ProductRepository(
        local = ProductLocalDataSource(application.applicationContext)
    )

    private val _product = MutableStateFlow<Producto?>(null)
    val product: StateFlow<Producto?> = _product.asStateFlow()

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            _product.value = repo.obtenerProductoPorSkuLocal(id)
        }
    }
}
