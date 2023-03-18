package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.librarymanagementadmin.databinding.ActivityRequestDetailsBinding
import com.example.librarymanagementadmin.models.issued_books
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import java.util.*
import kotlin.collections.HashMap

class Request_detailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestDetailsBinding
    private lateinit var database: DatabaseReference
    private lateinit var databases: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bookName = intent.getStringExtra("BookName")
        val authorName = intent.getStringExtra("AuthorName")
        val branch = intent.getStringExtra("Branch")
        val issueName = intent.getStringExtra("IssueName")
        val rollno = intent.getStringExtra("Rollno")


        binding.idTVTitle.text = bookName
        binding.idTVSubTitle.text = authorName
        binding.idTVbranch.text = branch
        binding.IssuerName.text = issueName
        binding.Rollno.text = rollno

        val database = FirebaseDatabase.getInstance().reference.child("Issued_Books")

        binding.idaccept.setOnClickListener {

            val currentTimestamp = System.currentTimeMillis()

            val endDateTimestamp = currentTimestamp + (86400000 * 14)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = endDateTimestamp

            val currentDate = hashMapOf(
                "date" to currentTimestamp
            )

            val endDate = hashMapOf(
                "date" to endDateTimestamp
            )


            val issued_books = issued_books(bookName, authorName, branch, issueName, rollno, currentDate,endDate)


            // Write the "issued_books" object to the database
            database.child(issueName.toString()).setValue(issued_books)
                .addOnSuccessListener {
                    Toast.makeText(this,"Request Accepted", Toast.LENGTH_SHORT).show()
                    val databases = FirebaseDatabase.getInstance().getReference("Issue_details_request")
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

        binding.idreject.setOnClickListener {
            val databases = FirebaseDatabase.getInstance().getReference("Issue_details_request")
            val childRef = databases.child(issueName!!)
            childRef.removeValue().addOnSuccessListener {
                Toast.makeText(this, "Request Rejected", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

            }
        }
    }

}
