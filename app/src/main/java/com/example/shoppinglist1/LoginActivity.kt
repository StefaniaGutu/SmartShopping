package com.example.shoppinglist1

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

open class LoginActivity : AppCompatActivity() {

    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailAddress = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerNowBtn = findViewById<TextView>(R.id.registerNowButton)

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, GoogleSignInActivity::class.java))
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

                                // open MainActivity
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
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