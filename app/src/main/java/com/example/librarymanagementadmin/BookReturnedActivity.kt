package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.librarymanagementadmin.databinding.ActivityBookReturnedBinding
import com.example.librarymanagementadmin.models.issued_books
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class BookReturnedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookReturnedBinding
    private lateinit var database: DatabaseReference
    private lateinit var databases: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookReturnedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookName = intent.getStringExtra("BookName")
        val authorName = intent.getStringExtra("AuthorName")
        val branch = intent.getStringExtra("Branch")
        val issueName = intent.getStringExtra("IssueName")
        val rollno = intent.getStringExtra("Rollno")
        val startDate = intent.getStringExtra("IssueDate")
        val endDate = intent.getStringExtra("ReturnDate")
        val uid = intent.getStringExtra("UID")



        binding.idTVTitle.text = bookName
        binding.idTVSubTitle.text = authorName
        binding.idTVbranch.text = branch
        binding.IssuerName.text = issueName
        binding.Rollno.text = rollno
        binding.IssueDate.text = startDate
        binding.LreturnDate.text = endDate

        database = FirebaseDatabase.getInstance().reference.child("Returned_Books")
        binding.idmark.setOnClickListener {

            val lreturnDate = HashMap<String, Any>()
            lreturnDate["date"] = ServerValue.TIMESTAMP

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = dateFormat.parse(startDate.toString()) // convert start date to Date object
            val timestamp = date!!.time // get time in milliseconds
            val issueDate = HashMap<String, Any>()
            issueDate["date"] = timestamp

            val issued_books = issued_books(bookName, authorName, branch, issueName, rollno, issueDate,lreturnDate,
                uid!!
            )


            // Write the "issued_books" object to the database
            database.child(issueName.toString()).setValue(issued_books)
                .addOnSuccessListener {
                    Toast.makeText(this,"BOOK RETURNED", Toast.LENGTH_SHORT).show()
                    val databases = FirebaseDatabase.getInstance().getReference("Issued_Books")
                    val childRef = databases.child(issueName!!)
                    childRef.removeValue().addOnSuccessListener {
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                    }
                }.addOnFailureListener {
                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }
        }

    }

}