package com.example.shoppinglist1

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

const val KEY_WELCOME = "welcome"
const val KEY_LOGGED_IN = "isLoggedIn"
const val KEY_USER_EMAIL = "userEmail"
const val KEY_USER_FULLNAME = "userFullname"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)

        if(!sharedPreferences.getBoolean(KEY_WELCOME, false)){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WelcomeFragment::class.java, null)
                .commit()

            sharedPreferences.edit().putBoolean(KEY_WELCOME, true).apply()
        }
        else if (!sharedPreferences.getBoolean(KEY_LOGGED_IN, false)){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, LoginFragment::class.java, null)
                .commit()
        }
        else{
            val email = sharedPreferences.getString(KEY_USER_EMAIL, null)
            val fullname = sharedPreferences.getString(KEY_USER_FULLNAME, null)

            // open MainActivity
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("userEmail", email)
            intent.putExtra("userFullname", fullname)
            startActivity(intent)
            finish()
        }
    }
}