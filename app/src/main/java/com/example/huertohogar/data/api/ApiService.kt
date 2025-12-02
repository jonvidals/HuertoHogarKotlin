package com.example.huertohogar.data.api

import com.example.huertohogar.model.Producto
import retrofit2.http.GET

interface ApiService {
    @GET("productos")
    suspend fun getProducts(): List<Producto>
}
