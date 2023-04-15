package com.example.shoppinglist1

import android.content.Context
import com.example.shoppinglist1.db.AppDataBase
import com.example.shoppinglist1.db.ListDao
import com.example.shoppinglist1.db.ShoppingListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepository(context: Context) {
    var db: ListDao = AppDataBase.getInstance(context)?.listDao()!!

    suspend fun getAllLists(): List<ShoppingListModel>{
        return withContext(Dispatchers.IO){
            db.getAllLists()
        }
    }

    suspend fun insert(list: ShoppingListModel){
        return withContext(Dispatchers.IO){
            db.insertAll(list)
        }
    }

    suspend fun delete(id: Int){
        return withContext(Dispatchers.IO){
            db.deleteList(id)
        }
    }

    suspend fun getListDetails(id: Int) : ShoppingListModel {
        return withContext(Dispatchers.IO){
            db.getListDetails(id)
        }
    }

    suspend fun updateListDetails(listModel: ShoppingListModel) {
        return withContext(Dispatchers.IO){
            db.updateList(listModel)
        }
    }
}