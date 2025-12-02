package com.example.huertohogar.data.repository

import com.example.huertohogar.data.api.ApiClient
import com.example.huertohogar.data.db.ProductLocalDataSource
import com.example.huertohogar.model.Producto

class ProductRepository(
    private val local: ProductLocalDataSource
) {

    private val api = ApiClient.api

    suspend fun cargarDesdeApi(): Result<List<Producto>> {
        return runCatching { api.getProducts() }
    }

    fun guardarLocal(lista: List<Producto>) {
        local.guardarProductos(lista)
    }

    fun obtenerLocal(): List<Producto> {
        return local.obtenerProductos()
    }

    fun hayDatosLocales(): Boolean {
        return local.hayDatosLocales()
    }

    fun obtenerProductoPorSkuLocal(id: Int): Producto? {
        return local.obtenerProductoPorId(id)
    }
}
