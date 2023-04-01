package com.example.shoppinglist1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val KEY_WELCOME = "welcome"

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)

        if(!sharedPreferences.getBoolean(KEY_WELCOME, false)){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WelcomeFragment::class.java, null)
                .commit()

            sharedPreferences.edit().putBoolean(KEY_WELCOME, true).apply()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ListsFragment::class.java, null)
                .commit()
        }
    }
}