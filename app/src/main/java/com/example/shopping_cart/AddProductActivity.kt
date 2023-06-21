package com.example.shopping_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shopping_cart.Model.ProductListModel
import com.example.shopping_cart.databinding.ActivityAddProductBinding
import com.example.shopping_cart.viewModel.ProductListViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductListViewModel::class.java)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val extras = intent.extras
        if (extras != null) {
            Picasso.get().load(extras.getString("imagen")).into(binding.imageView2)
            binding.product.text = extras.getString("nombre")
            binding.category.text = extras.getString("categoria")
            binding.precio.text = extras.getString("precio")



            binding.addButton.setOnClickListener {
                GlobalScope.launch {
                    save(extras.getString("imagen"))
                }
            }
        }
    }

    suspend fun save(imagen: String?) {
        var newProduct = ProductListModel(
            0,
            imagen.toString(),
            binding.product.text.toString(),
            binding.category.text.toString(),
            parseFloat(binding.precio.text.toString()),
            parseInt(binding.editTextNumber2.text.toString())
        )
        viewModel.insertProductList(newProduct)

        this.finish()
    }

}