package com.example.huertohogar.viewmodels

import androidx.lifecycle.ViewModel
import com.example.huertohogar.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class CartItem(
    val product: Producto,
    var quantity: Int
)

class CartViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<CartItem>>(emptyList())
    val items: StateFlow<List<CartItem>> = _items

    fun add(product: Producto) {
        _items.update { current ->
            val existing = current.find { it.product.id == product.id }
            if (existing != null) {
                current.map {
                    if (it.product.id == product.id)
                        it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                current + CartItem(product, 1)
            }
        }
    }

    fun removeOne(productId: Int) {
        _items.update { current ->
            current.mapNotNull {
                when {
                    it.product.id != productId -> it
                    it.quantity > 1 -> it.copy(quantity = it.quantity - 1)
                    else -> null
                }
            }
        }
    }
    fun total(): Double {
        return _items.value.sumOf { item ->
            val precio = item.product.precio.toDoubleOrNull() ?: 0.0
            precio * item.quantity
        }
    }
    fun count(): Int {
        return _items.value.sumOf { it.quantity }
    }
}
