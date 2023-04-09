package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.librarymanagementadmin.databinding.ActivityUpdateBookDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Update_Book_detailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateBookDetailsBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val BookName = intent.getStringExtra("BookName")
        val AuthorName = intent.getStringExtra("AuthorName")
        val Description = intent.getStringExtra("Description")


        binding.BookName.text = Editable.Factory.getInstance().newEditable(BookName)
        binding.AuthorName.text = Editable.Factory.getInstance().newEditable(AuthorName)
        binding.Description.text = Editable.Factory.getInstance().newEditable(Description)


        binding.updateBtn.setOnClickListener {

            val bookName = binding.BookName.text.toString()
            val authorName = binding.AuthorName.text.toString()
            val branch = binding.branchSpinner.selectedItem.toString()
            val description = binding.Description.text.toString()

            if (BookName != null) {
                updateData(bookName,authorName,branch,description,BookName)
            }

        }

    }

    private fun updateData(bookName: String, authorName: String, branch: String, description: String,BookName : String) {

        database = FirebaseDatabase.getInstance().getReference("Book_details")
        val user = mapOf<String,String>(
            "authorName" to authorName.toString(),
            "bookName" to bookName.toString(),
            "branch" to branch,
            "description" to description.toString()
        )
        database.child(BookName).updateChildren(user).addOnSuccessListener {

            Toast.makeText(this,"Successfuly Updated",Toast.LENGTH_SHORT).show()
            finish()

        }.addOnFailureListener{

            Toast.makeText(this,"Failed to Update",Toast.LENGTH_SHORT).show()

        }}
}