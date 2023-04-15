package com.example.shoppinglist1.db

import androidx.room.*
import com.example.shoppinglist1.db.ShoppingListModel

@Dao
interface ListDao {
    @Query("SELECT * FROM list_item")
    suspend fun getAllLists() : List<ShoppingListModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: ShoppingListModel)

    @Query("DELETE FROM list_item WHERE id = :id")
    suspend fun deleteList(id: Int)

    @Query("SELECT * FROM list_item WHERE id LIKE :id")
    suspend fun getListDetails(id: Int) : ShoppingListModel

    @Update
    suspend fun updateList(list: ShoppingListModel)
}