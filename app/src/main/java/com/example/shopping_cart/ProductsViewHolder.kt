package com.example.shopping_cart


import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_cart.Model.ProductItemResponse
import com.example.shopping_cart.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemProductBinding.bind(view)

    fun bind(productItemResponse: ProductItemResponse, onItemSelected: (String) -> Unit) {
        Picasso.get().load(productItemResponse.thumbnail).into(binding.imageView)
        binding.textViewTitle.text = productItemResponse.title
        binding.textViewCategory.text = productItemResponse.category
        binding.textViewPrice.text = productItemResponse.price.toString()

        binding.buttonActionProduct.setOnClickListener {
            onItemSelected(productItemResponse.id.toString())

            var intent = Intent(binding.root.context, AddProductActivity::class.java)
            intent.putExtra("id", productItemResponse.id.toString())
            intent.putExtra("nombre", productItemResponse.title.toString())
            intent.putExtra("precio", productItemResponse.price.toString())
            intent.putExtra("categoria", productItemResponse.category.toString())
            intent.putExtra("imagen", productItemResponse.thumbnail)
            binding.root.context.startActivity(intent)
        }


    }
}