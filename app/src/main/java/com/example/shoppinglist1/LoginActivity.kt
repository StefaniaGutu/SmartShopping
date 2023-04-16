package com.example.shoppinglist1

import android.content.Intent
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

        val emailAddress = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerNowBtn = findViewById<TextView>(R.id.registerNowButton)

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