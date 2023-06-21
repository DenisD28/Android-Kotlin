package com.example.shopping_cart.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shopping_cart.Model.ProductListModel
import com.example.shopping_cart.repository.ProductListRepository
import com.example.shopping_cart.room.RoomDabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application) : AndroidViewModel(application) {
    val db = Firebase.firestore
    val productList: LiveData<List<ProductListModel>>
    val repository: ProductListRepository

    init {
        val productListDao = RoomDabase.getDatabase(application).ProductListDao()
        repository = ProductListRepository(productListDao)
        productList = repository.ObtenerProductList()
    }

    fun insertProductList(product: ProductListModel) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.guardarProductList(product)

            // Create a new user with a first and last name
            val newProduct = hashMapOf(
                "category" to product.cat,
                "count" to product.count,
                "image" to product.imagen,
                "name" to product.nombre,
                "price" to product.precio,
            )

// Add a new document with a generated ID
            db.collection("Products")
                .add(newProduct)
                .addOnSuccessListener { documentReference ->
                    Log.d("test", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("test", "Error adding document", e)
                }
        }

    fun updateProductList(product: ProductListModel) =
        viewModelScope.launch(Dispatchers.IO) {


            repository.modificarProductList(product)

            db.collection("Products").get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        if (product.nombre == document["name"]) {

                            db.collection("Products").document(document.id)
                                .update("count", product.count)
                                .addOnSuccessListener {
                                    Log.d(
                                        "test",
                                        "DocumentSnapshot successfully updated!"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w(
                                        "test",
                                        "Error updating document",
                                        e
                                    )
                                }
                        }

                    }
                }
                .addOnFailureListener { e ->
                    // Ocurrió un error al intentar obtener los documentos
                }

        }

    fun deleteProductList(product: ProductListModel) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarProductList(product)

            db.collection("Products").get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        if (product.nombre == document["name"]) {

                            db.collection("Products").document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d(
                                        "test",
                                        "DocumentSnapshot successfully deleted!"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w(
                                        "test",
                                        "Error deleting document",
                                        e
                                    )
                                }
                        }

                    }
                }
                .addOnFailureListener { e ->
                    // Ocurrió un error al intentar obtener los documentos
                }

        }
}

