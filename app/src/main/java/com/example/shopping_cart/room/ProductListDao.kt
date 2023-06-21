package com.example.shopping_cart.room

import androidx.room.*
import com.example.shopping_cart.Model.ProductListModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductListDao {
    @Query("SELECT * from list_products")
    fun obtenerProductList(): Flow<List<ProductListModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun guardarProductoList(productListModel: ProductListModel)

    @Update
    fun actualizarProductList(productListModel: ProductListModel)

    @Query("DELETE FROM list_products")
    suspend fun eliminarTodosList()

    @Delete
    suspend fun eliminarProductList(productListModel: ProductListModel)
}