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

class RegisterActivity : AppCompatActivity() {

    // cream obiectul DatabaseReference, pt a accesa Realtime Database din Firebase
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://shoppinglist-e5b53-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullname = findViewById<EditText>(R.id.fullname)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)

        val registerButton = findViewById<Button>(R.id.registerButton)
        val loginNowButton = findViewById<TextView>(R.id.loginNowButton)

        registerButton.setOnClickListener {
            val fullnameText = fullname.text.toString()
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()

            if(fullnameText.isBlank() || emailText.isBlank() || passwordText.isBlank() || confirmPasswordText.isBlank()){
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else if (!passwordText.equals(confirmPasswordText)) {
                Toast.makeText(this, "Passwords are not matching", Toast.LENGTH_SHORT).show()
            }
            else{
                databaseReference.child("users").addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val emailTextPath = emailText.replace(".", ",")

                        // verific ca email-ul sa nu fi fost deja folosit
                        if(snapshot.hasChild(emailTextPath)){
                            Toast.makeText(this@RegisterActivity, "Already exist an account with this email address", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            // trimitem datele la firebase Realtime Database
                            // folosesc email-ul ca Id, restul informatiilor fiind puse "sub" email-ul corespunzator
                            databaseReference.child("users").child(emailTextPath).child("fullname").setValue(fullnameText)
                            databaseReference.child("users").child(emailTextPath).child("password").setValue(passwordText)

                            Toast.makeText(this@RegisterActivity, "Account created successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
        }

        loginNowButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}