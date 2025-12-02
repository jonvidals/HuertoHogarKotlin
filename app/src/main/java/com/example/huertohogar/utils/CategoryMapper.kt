package com.example.huertohogar.utils

object CategoryMapper {

    fun fromId(id: Int): String {
        return when (id) {
            1 -> "Verduras"
            2 -> "Frutas"
            3 -> "Hierbas"
            4 -> "Legumbres"
            else -> "Categoría desconocida"
        }
    }
}