package com.example.shoppinglist1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val KEY_WELCOME = "welcome"
        val KEY_LOGGED_IN = "isLoggedIn"
        val KEY_USER_EMAIL = "userEmail"
        val KEY_USER_FULLNAME = "userFullname"

        val emailAddress = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerNowBtn = findViewById<TextView>(R.id.registerNowButton)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("SmartShoppingPreferences", Context.MODE_PRIVATE)

        if(!sharedPreferences.getBoolean(KEY_WELCOME, false)){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, WelcomeFragment::class.java, null)
                .commit()

            sharedPreferences.edit().putBoolean(KEY_WELCOME, true).apply()
        }
        else if (sharedPreferences.getBoolean(KEY_LOGGED_IN, false)){
            val email = sharedPreferences.getString(KEY_USER_EMAIL, null)
            val fullname = sharedPreferences.getString(KEY_USER_FULLNAME, null)

            // open MainActivity
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("userEmail", email)
            intent.putExtra("userFullname", fullname)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener{
            val emailText = emailAddress.text.toString()
            val passwordText = password.text.toString()

            if(emailText.isBlank() || passwordText.isBlank()){
                Toast.makeText(this, "Please enter your email or password", Toast.LENGTH_SHORT).show()
            }
            else{
                databaseReference.child("users").addListenerForSingleValueEvent(object:
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val emailTextPath = emailText.replace(".", ",")

                        // verific daca email-ul exista in bd
                        if(snapshot.hasChild(emailTextPath)){
                            val getPassword = snapshot.child(emailTextPath).child("password").getValue(String:: class.java)

                            if (getPassword.equals(passwordText)){
                                Toast.makeText(this@LoginActivity, "Successfully logged in", Toast.LENGTH_SHORT).show()

                                val fullnameText = snapshot.child(emailTextPath).child("fullname").getValue(String:: class.java)

                                // update SharedPreferences
                                val editor = sharedPreferences.edit()
                                editor.putBoolean(KEY_LOGGED_IN, true)
                                editor.putString(KEY_USER_EMAIL, emailText)
                                editor.putString(KEY_USER_FULLNAME, fullnameText)
                                editor.apply()

                                // open MainActivity
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.putExtra("userEmail", emailText)
                                intent.putExtra("userFullname", fullnameText)
                                startActivity(intent)
                                finish()
                            }
                            else{
                                Toast.makeText(this@LoginActivity, "Wrong password", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(this@LoginActivity, "Wrong email", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }

        registerNowBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}