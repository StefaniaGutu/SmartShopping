package com.example.shoppinglist1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist1.db_models.ItemModel


class CheckboxRecyclerViewAdapter(var list: MutableList<ItemModel>): RecyclerView.Adapter<CheckboxRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.checkbox_rox, parent, false)

        return ViewHolder(view)
    }

    fun update(new_list: MutableList<ItemModel>) {
        list = new_list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkbox.isChecked = list.get(position).isChecked

        holder.checkbox.text = list.get(position).text
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var checkbox = itemView.findViewById<CheckBox>(R.id.checkbox)
        var deleteItem = itemView.findViewById<ImageView>(R.id.deleteItemButton)

        init {
            checkbox.setOnClickListener {
                if(!list.get(adapterPosition).isChecked){
                    checkbox.isChecked = true
                    list[adapterPosition].isChecked = true
                }
                else{
                    checkbox.isChecked = false
                    list[adapterPosition].isChecked = false
                }
            }

            deleteItem.setOnClickListener{
                list.removeAt(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }
}
