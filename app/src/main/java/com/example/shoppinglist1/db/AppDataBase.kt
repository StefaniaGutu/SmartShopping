package com.example.shoppinglist1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShoppingListModel::class], version = 1, exportSchema = false)
abstract class AppDataBase :RoomDatabase() {
    abstract fun listDao() : ListDao

    companion object{
        private var INSTANCE:AppDataBase?=null

        fun getInstance(context: Context): AppDataBase?{
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDataBase::class.java, "lists.dp"
                )
                    .build()
            }
            return INSTANCE
        }
    }
}