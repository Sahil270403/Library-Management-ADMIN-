package com.example.librarymanagementadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.librarymanagementadmin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.carview1.setOnClickListener {

            val intent = Intent(this,Upload_book_details::class.java)
            startActivity(intent)
        }

        binding.carview2.setOnClickListener {

            val intent = Intent(this,RequestIssueRecievedActivity::class.java)
            startActivity(intent)
        }

        binding.carview3.setOnClickListener {

            val intent = Intent(this,BookIssuedActivity::class.java)
            startActivity(intent)
        }

        binding.carview4.setOnClickListener {

            val intent = Intent(this,Returned_bookActivity::class.java)
            startActivity(intent)
        }


        binding.carview5.setOnClickListener {

            val intent = Intent(this,Update_booksActivity::class.java)
            startActivity(intent)
        }

        binding.carview6.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleSignInClient = GoogleSignIn.getClient(this , gso)
            googleSignInClient.signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}