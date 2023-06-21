package com.example.shopping_cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_cart.Model.ProductItemResponse


//class ProductsAdapter(var productList: List<ProductItemResponse> = emptyList(), private val onItemSelected: () -> Unit):
//    RecyclerView.Adapter<ProductsViewHolder>() {

class ProductsAdapter(var productList: List<ProductItemResponse> = emptyList(), private val onItemSelected: (String) -> Unit):
    RecyclerView.Adapter<ProductsViewHolder>() {


    fun updateList(productList: List<ProductItemResponse>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(viewholder: ProductsViewHolder, position: Int) {
        viewholder.bind(productList[position], onItemSelected)

    }

    override fun getItemCount(): Int = productList.size
}