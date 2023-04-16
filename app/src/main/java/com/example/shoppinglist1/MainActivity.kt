package com.example.shoppinglist1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val USER_EMAIL = "user_email"
const val USER_FULLNAME = "user_fullname"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val KEY_WELCOME = "welcome"

        val userEmail = intent.getStringExtra("userEmail")
        val userFullname = intent.getStringExtra("userFullname")

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)

        if(!sharedPreferences.getBoolean(KEY_WELCOME, false)){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WelcomeFragment::class.java, null)
                .commit()

            sharedPreferences.edit().putBoolean(KEY_WELCOME, true).apply()
        }
        else{
            //trimit email-ul prin bundle catre edit fragment
            val bundle = Bundle()
            bundle.putString(USER_EMAIL, userEmail)
            bundle.putString(USER_FULLNAME, userFullname)

            val fragment = ListsFragment()
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, null)
                .commit()
        }
    }
}