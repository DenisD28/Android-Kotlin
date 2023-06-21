package com.example.shopping_cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopping_cart.Model.ProductListModel
import com.example.shopping_cart.databinding.ActivityShoppingListBinding
import com.example.shopping_cart.viewModel.ProductListViewModel
import retrofit2.Retrofit

class ShoppingListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingListBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var shopingAdapter: ShoppingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ProductListViewModel::class.java)
        observeEvents()

    }

    private fun observeEvents() {
        viewModel.productList.observe(this, Observer { list ->
            list?.let {
                shopingAdapter = ShoppingAdapter(it, viewModel)
                binding.recyclerViewProducts.adapter = shopingAdapter
                shopingAdapter.notifyDataSetChanged()
            }
        })
    }

    companion object{
        lateinit var product : ProductListModel
    }

}