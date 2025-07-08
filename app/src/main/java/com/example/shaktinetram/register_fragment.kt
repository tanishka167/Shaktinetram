package com.example.shaktinetram

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth


class register_fragment : Fragment() {

    private lateinit var tvname: TextView
    private lateinit var tvaddr: TextView
    private lateinit var tvemail: TextView
    private lateinit var tvpsswrd:TextView
    private lateinit var btnRegister:Button
    private lateinit var progressBar: View

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvname = view.findViewById<TextView>(R.id.username)
        tvaddr = view.findViewById<TextView>(R.id.useraddress)
        tvemail = view.findViewById<TextView>(R.id.userEmail)
        tvpsswrd = view.findViewById<TextView>(R.id.userPsswrd)
        btnRegister=view.findViewById(R.id.registerButton)
        progressBar = view.findViewById(R.id.progressBar)

        auth = Firebase.auth

        btnRegister.setOnClickListener{
            val sName=tvname.text.toString().trim()
            val sAddr=tvaddr.text.toString().trim()
            val sEmail=tvemail.text.toString().trim()
            val sPsswrd=tvpsswrd.text.toString().trim()

            var isValid=true

            if(sName.isEmpty()){
                tvname.error="Name is required"
                isValid=false
            }
            if(sEmail.isEmpty()){
                tvemail.error="Email is required"
                isValid=false
            }
            if(sAddr.isEmpty()){
                tvaddr.error="Address is required"
                isValid=false
            }
            if(sPsswrd.isEmpty()){
                tvpsswrd.error="Password is required"
                isValid=false
            }

            if (isValid) {
                progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(sEmail, sPsswrd)
                    .addOnCompleteListener(requireActivity()) { task ->
                        progressBar.visibility = View.INVISIBLE

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                requireContext(),
                                "Authentication failed: ${task.exception?.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
//                        updateUI(null)
                        }
                    }
            }
        }


    }

    private fun updateUI(user: FirebaseUser?) {
        val bundle = Bundle().apply {
            putString("name", tvname.text.toString().trim())
            putString("email", tvemail.text.toString().trim())
            putString("address", tvaddr.text.toString().trim())
        }

        val nextFragment = fragment_setProfile()
        nextFragment.arguments = bundle

        (activity as? Register)?.loadFragment(nextFragment, true)
    }
}