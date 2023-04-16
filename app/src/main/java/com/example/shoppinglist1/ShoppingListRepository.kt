package com.example.shoppinglist1

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.shoppinglist1.db.ListModel
import com.example.shoppinglist1.db.ShoppingListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.values
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListRepository(context: Context) {
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

//    suspend fun getUserLists(userEmail: String): List<ShoppingListModel>{
//        val shoppingLists: List<ShoppingListModel>
//
//        databaseReference.child("shoppingLists").addListenerForSingleValueEvent(object:
//            ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val lists = snapshot.getValue()
//
//                val objects = lists.
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        }
//    }

    suspend fun insertList(list: ListModel){
        return withContext(Dispatchers.IO){
            val id = databaseReference.child("shoppingLists").push().key!!
            databaseReference.child("shoppingLists").child(id).setValue(list)
        }
    }


}