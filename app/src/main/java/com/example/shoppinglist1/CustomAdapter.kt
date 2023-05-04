package com.example.shoppinglist1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist1.databinding.ShoppingListItemBinding
import com.example.shoppinglist1.db_models.ListModel

class CustomAdapter(
    private val onItemClick: (ListModel) -> Unit
) : RecyclerView.Adapter<CustomAdapter.ListHolder>() {


    lateinit var binding: ShoppingListItemBinding
    var listItems = mutableListOf<ListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        binding = ShoppingListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val item = listItems[position]
        holder.title.text = item.title
        holder.description.text = item.description
        holder.estimatedCost.text = item.estimatedCost.toString()
        holder.constraint.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun update(list: List<ListModel>) {
        listItems.clear()
        listItems.addAll(list)
        notifyDataSetChanged()
    }

    class ListHolder(binding: ShoppingListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val description = binding.description
        var estimatedCost = binding.estimatedCost
        val constraint = binding.constraint
    }
}