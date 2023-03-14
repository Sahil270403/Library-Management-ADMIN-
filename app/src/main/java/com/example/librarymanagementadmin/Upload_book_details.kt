package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.librarymanagementadmin.databinding.ActivityUploadBookDetailsBinding
import com.example.librarymanagementadmin.models.book_details
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Upload_book_details : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBookDetailsBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.registerBtn.setOnClickListener {

            val BookName = binding.BookName.text.toString()
            val AuthorName = binding.AuthorName.text.toString()
            val branch = binding.branch.text.toString()
            val Description = binding.Description.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Book_details")
            val Book_details = book_details(BookName,AuthorName,branch,Description)
            database.child(BookName).setValue(Book_details).addOnSuccessListener {

                binding.BookName.text.clear()
                binding.AuthorName.text.clear()
                binding.branch.text.clear()
                binding.Description.text.clear()

                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}