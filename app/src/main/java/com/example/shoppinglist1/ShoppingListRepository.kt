package com.example.shoppinglist1

import android.content.Context
import com.example.shoppinglist1.db_models.ListModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListRepository(context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

    suspend fun insertList(list: ListModel){
        return withContext(Dispatchers.IO){
            val id = databaseReference.child("shoppingLists").push().key!!
            databaseReference.child("shoppingLists").child(id).setValue(list)
        }
    }
}