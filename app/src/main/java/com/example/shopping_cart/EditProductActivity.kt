package com.example.shopping_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shopping_cart.databinding.ActivityAddProductBinding
import com.example.shopping_cart.databinding.ActivityEditProductBinding
import com.example.shopping_cart.viewModel.ProductListViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Integer.parseInt

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding
    private lateinit var viewModel: ProductListViewModel
    private var product = ShoppingListActivity.product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductListViewModel::class.java)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        Picasso.get().load(product.imagen).into(binding.imageView2)
        binding.product.setText(product.nombre)
        binding.category.setText(product.cat)
        binding.precio.setText(product.precio.toString())
        binding.editTextNumber2.setText(product.count.toString())

        binding.addButton.setOnClickListener {
            GlobalScope.launch {
                update(product.imagen)
            }
        }
    }

    suspend fun update(imagen: String?) {
        product.count = parseInt(binding.editTextNumber2.text.toString())
        viewModel.updateProductList(product)

        this.finish()
    }
}