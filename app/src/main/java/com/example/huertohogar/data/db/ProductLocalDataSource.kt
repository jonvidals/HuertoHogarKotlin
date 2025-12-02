package com.example.huertohogar.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.huertohogar.model.Producto

class ProductLocalDataSource(context: Context) {

    private val dbHelper = ProductDBHelper(context)

    fun guardarProductos(lista: List<Producto>) {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            db.delete("productos", null, null)

            for (p in lista) {
                val values = ContentValues().apply {
                    put("id", p.id)
                    put("nombre", p.nombre)
                    put("descripcion", p.descripcion)
                    put("precio", p.precio)
                    put("categoria_id", p.categoria_id)
                    put("imagen", p.imagen)
                    put("stock", p.stock)
                    put("unidad", p.unidad)
                    put("destacado", if (p.destacado) 1 else 0)
                    put("tienda_id", p.tienda_id)
                    put("created_at", p.created_at)
                    put("updated_at", p.updated_at)
                    put("categoria_nombre", p.categoria_nombre)
                }

                db.insertWithOnConflict(
                    "productos",
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    fun obtenerProductos(): List<Producto> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "productos",
            arrayOf(
                "id", "nombre", "descripcion", "precio",
                "categoria_id", "imagen", "stock",
                "unidad", "destacado", "tienda_id",
                "created_at", "updated_at", "categoria_nombre"
            ),
            null,
            null,
            null,
            null,
            null
        )

        val lista = mutableListOf<Producto>()

        cursor.use {
            while (it.moveToNext()) {
                lista.add(
                    Producto(
                        id = it.getInt(0),
                        nombre = it.getString(1),
                        descripcion = it.getString(2),
                        precio = it.getString(3),
                        categoria_id = it.getInt(4),
                        imagen = it.getString(5),
                        stock = it.getInt(6),
                        unidad = it.getString(7),
                        destacado = it.getInt(8) == 1,
                        tienda_id = it.getInt(9),
                        created_at = it.getString(10),
                        updated_at = it.getString(11),
                        categoria_nombre = it.getString(12)
                    )
                )
            }
        }

        db.close()
        return lista
    }

    fun hayDatosLocales(): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM productos", null)
        var count = 0
        cursor.use {
            if (it.moveToFirst()) count = it.getInt(0)
        }
        db.close()
        return count > 0
    }

    fun obtenerProductoPorId(id: Int): Producto? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "productos",
            arrayOf(
                "id", "nombre", "descripcion", "precio",
                "categoria_id", "imagen", "stock",
                "unidad", "destacado", "tienda_id",
                "created_at", "updated_at", "categoria_nombre"
            ),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var producto: Producto? = null

        cursor.use {
            if (it.moveToFirst()) {
                producto = Producto(
                    id = it.getInt(0),
                    nombre = it.getString(1),
                    descripcion = it.getString(2),
                    precio = it.getString(3),
                    categoria_id = it.getInt(4),
                    imagen = it.getString(5),
                    stock = it.getInt(6),
                    unidad = it.getString(7),
                    destacado = it.getInt(8) == 1,
                    tienda_id = it.getInt(9),
                    created_at = it.getString(10),
                    updated_at = it.getString(11),
                    categoria_nombre = it.getString(12)
                )
            }
        }

        db.close()
        return producto
    }
}
