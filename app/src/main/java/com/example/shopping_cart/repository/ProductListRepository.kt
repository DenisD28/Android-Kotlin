package com.example.shopping_cart.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.shopping_cart.Model.ProductListModel
import com.example.shopping_cart.room.ProductListDao
import com.google.firebase.ktx.Firebase

class ProductListRepository(private val ProductListDao: ProductListDao) {
    fun ObtenerProductList(): LiveData<List<ProductListModel>> =
        ProductListDao.obtenerProductList().asLiveData()

    suspend fun guardarProductList(product: ProductListModel) {
        val db = Firebase

        Log.i("datas", product.imagen)
        ProductListDao.guardarProductoList(product)
    }

    suspend fun modificarProductList(product: ProductListModel){
        ProductListDao.actualizarProductList(product)
    }

    suspend fun eliminarProductList(product: ProductListModel) {
        ProductListDao.eliminarProductList(product)
    }
}