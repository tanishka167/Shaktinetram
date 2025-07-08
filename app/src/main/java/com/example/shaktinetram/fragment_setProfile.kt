package com.example.shaktinetram

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_setProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_setProfile : Fragment() {

    private lateinit var userprofile: ImageView
    private lateinit var username: TextView
    private lateinit var useremail: TextView
    private lateinit var useraddr: TextView
    private lateinit var updateProfileBtn: Button
    private lateinit var logoutBtn: TextView
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private lateinit var progressBar: View




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_set_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userprofile= view.findViewById<ImageView>(R.id.profilePic)
        username= view.findViewById<TextView>(R.id.username)
        useremail= view.findViewById<TextView>(R.id.userEmail)
        useraddr= view.findViewById<TextView>(R.id.address)
        updateProfileBtn= view.findViewById<Button>(R.id.update)
        logoutBtn= view.findViewById<TextView>(R.id.logout_btn)



        progressBar=view.findViewById<View>(R.id.progressBar)

        userprofile.setOnClickListener {
            openImageChooser()
        }

        val name = arguments?.getString("name")
        val email = arguments?.getString("email")
        val address = arguments?.getString("address")

        username.text = name
        useremail.text = email
        useraddr.text = address

        updateProfileBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
            (activity as Register).loadFragment(HomeFragment(),true)
        }

        logoutBtn.setOnClickListener{
            (activity as? Register)?.loadFragment(register_fragment(), false)
        }

    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            userprofile.setImageURI(uri)

        }
    }

    private fun openImageChooser() {
        imagePickerLauncher.launch("image/*")
    }

}