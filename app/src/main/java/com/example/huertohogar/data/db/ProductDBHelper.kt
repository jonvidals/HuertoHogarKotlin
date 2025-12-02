package com.example.huertohogar.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDBHelper(context: Context) :
    SQLiteOpenHelper(context, "products.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
        CREATE TABLE productos (
            id INTEGER PRIMARY KEY,
            nombre TEXT,
            descripcion TEXT,
            precio TEXT,
            categoria_id INTEGER,
            imagen TEXT,
            stock INTEGER,
            unidad TEXT,
            destacado INTEGER,
            tienda_id INTEGER,
            created_at TEXT,
            updated_at TEXT,
            categoria_nombre TEXT
        )
        """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS productos")
        onCreate(db)
    }
}
