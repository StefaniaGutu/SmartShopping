package com.example.shoppinglist1.db_models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListModel(val title: String? = null,
                     val description: String? = null,
                     val estimatedCost: Int? = 0,
                     val userEmail: String? = null,
                     val items: ArrayList<ItemModel> = ArrayList()) {
    var id: String? = null

    override fun toString(): String {
        var allItems = ""
        for (item in items) {
            allItems += " - " + item.text
            if(item.isChecked){
                allItems += " (cumparat)"
            }
            allItems += "\n"
        }
        return title + "\n" + description + "\n" + "Estimated cost: " + estimatedCost + "\n" + allItems
    }
}