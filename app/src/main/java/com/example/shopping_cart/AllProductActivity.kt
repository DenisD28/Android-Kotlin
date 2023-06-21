package com.example.shopping_cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_cart.Model.ProductItemResponse
import com.example.shopping_cart.Model.ProductResponse
import com.example.shopping_cart.databinding.ActivityAllProductBinding
import com.example.shopping_cart.repository.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        retrofit = getRetroFit()

        loaderSpinner()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.infousuario) {
            Toast.makeText(this, "info", Toast.LENGTH_SHORT).show()
        }

        if (item.itemId == R.id.btnProductList) {
            val intent = Intent(this, ShoppingListActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loaderSpinner() {
        val spinner = binding.spinnerCategory
        val lista = resources.getStringArray(R.array.options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    val selectedItem = parent?.getItemAtPosition(position).toString()
                    // Aquí puedes hacer lo que necesites con el elemento seleccionado
                    Log.i("testApi", selectedItem)
                    loaderViewData(selectedItem)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Este método se llama cuando no se selecciona ningún elemento
                }
            }

        this.adapter = ProductsAdapter {
            actionsProductID(it)
        }
        binding.recyclerViewProducts.setHasFixedSize(true)
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducts.adapter = this.adapter
    }

    private fun loaderViewData(category: String) {

        binding.progressBarProducts.isVisible = true

        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<ProductResponse> =

                if (category.isEmpty()) {
                    retrofit.create(ApiService::class.java).getProducts(category)
                } else {
                    retrofit.create(ApiService::class.java).getCategoryProducts(category)
                }

            if (myResponse.isSuccessful) {
                var response: ProductResponse? = myResponse.body()
                if (response != null) {
                    Log.i("testApi", response.products.toString())

                    runOnUiThread {
                        adapter.updateList(response.products)
                        binding.progressBarProducts.isVisible = false
                    }
                }
            } else {
                Log.i("testApi", "Se jodio esta mierda :(")
            }
        }
    }

    private fun actionsProductID(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<ProductItemResponse> =
                retrofit.create(ApiService::class.java).getProduct(id)
            if (myResponse.isSuccessful) {
                var response: ProductItemResponse? = myResponse.body()
                if (response != null) {
                    //aqui puede extraer los datos del producto para guardarlos en la base de datos
                    //ejemplo val id = response.id.toString()
                    //Log.i("dato", response.title.toString())
                }
            } else {
                Log.i("testApi", "Se jodio esta mierda :(")
            }
        }
    }

    private fun getRetroFit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}