package com.example.shoppinglist1.db

import androidx.room.ColumnInfo
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListModel(val title: String? = null,
                     val description: String? = null,
                     val estimatedCost: Int? = 0,
                     val userEmail: String? = null) {
    var id: String? = null

    override fun toString(): String = title + "\n" + description + "\n" + "Estimated cost: " + estimatedCost
}