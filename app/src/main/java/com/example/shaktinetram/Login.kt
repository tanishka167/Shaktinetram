package com.example.shaktinetram

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.concurrent.TimeUnit

class Login : AppCompatActivity() {
    private lateinit var tvemail: EditText
    private lateinit var tvpswrd: EditText
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvRedirectSignUp: TextView


    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        tvemail = findViewById(R.id.userEmail)
        tvpswrd=findViewById(R.id.userpswrd)
        loginBtn = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.progressBar)
        tvRedirectSignUp=findViewById(R.id.signupText)

        auth=FirebaseAuth.getInstance()

        loginBtn.setOnClickListener{
            login()
        }

        tvRedirectSignUp.setOnClickListener{
            val intent=Intent(this,register_fragment::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(){
        val sEmail=tvemail.text.toString().trim()
        val sPswrd=tvpswrd.text.toString().trim()

        if(sEmail.isEmpty()){
            tvemail.error="Email is required"
            tvemail.requestFocus()
            return
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
            tvemail.error = "Enter a valid email"
            tvemail.requestFocus()
            return
        }

        if(sPswrd.isEmpty()){
            tvpswrd.error="Password is required"
            tvpswrd.requestFocus()
            return
        }

        progressBar.visibility=View.VISIBLE

        auth.signInWithEmailAndPassword(sEmail, sPswrd).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                updateUI(user)
            } else
                Toast.makeText(this, "Log In failed: ${it.exception?.localizedMessage}",Toast.LENGTH_LONG).show()
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        val intent=Intent(this,drawerNavigation::class.java)
        startActivity(intent)
        finish()
    }
}