package com.example.shopping_cart

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_cart.Model.ProductListModel
import com.example.shopping_cart.databinding.ItemProductBinding
import com.example.shopping_cart.databinding.ItemProductlistBinding
import com.example.shopping_cart.viewModel.ProductListViewModel
import com.squareup.picasso.Picasso

class ShoppingAdapter(var datalist: List<ProductListModel>, var viewModel: ProductListViewModel): RecyclerView.Adapter<ShoppingAdapter.ShoppingHolder>() {

    inner class ShoppingHolder(val binding: ItemProductlistBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingHolder{
        val binding = ItemProductlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingHolder, position: Int) {

        var item = datalist[position]
        Picasso.get().load(item.imagen).into(holder.binding.imageView)
        holder.binding.textViewTitle.text = item.nombre
        holder.binding.textViewCategory.text = item.cat
        holder.binding.textViewPrice.text = item.precio.toString()
        holder.binding.textViewCount.text = item.count.toString()
        holder.binding.deleteButton.setOnClickListener{
            viewModel.deleteProductList(item)
        }

        holder.binding.editButton.setOnClickListener{
            var intent = Intent(holder.binding.root.context, EditProductActivity::class.java)
            ShoppingListActivity.product = item
            holder.binding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datalist.size
    }
}
