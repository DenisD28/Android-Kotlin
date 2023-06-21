package com.example.shopping_cart.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_products")
data class ProductListModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "image") val imagen: String,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "categoria") val cat: String,
    @ColumnInfo(name = "precio") val precio: Float,
    @ColumnInfo(name = "count_product") var count: Int
    //@ColumnInfo(name = "id_user") val user: String
)