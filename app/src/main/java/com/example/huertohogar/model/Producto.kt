package com.example.huertohogar.model

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val categoria_id: Int,
    val imagen: String,
    val stock: Int,
    val unidad: String,
    val destacado: Boolean,
    val tienda_id: Int,
    val created_at: String?,
    val updated_at: String?,
    val categoria_nombre: String
)