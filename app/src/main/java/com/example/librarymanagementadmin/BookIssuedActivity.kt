package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.databinding.ActivityBookIssuedBinding
import com.example.librarymanagementadmin.models.issue_details
import com.example.librarymanagementadmin.models.issued_books
import com.google.firebase.database.*

class BookIssuedActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<issued_books>
    private lateinit var binding: ActivityBookIssuedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookIssuedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerview = findViewById(R.id.sortNameRecyclerView1)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<issued_books>()
        getUserData()
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Issued_Books")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    userArrayList.clear()

                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(issued_books::class.java)
                        userArrayList.add(user!!)
                    }

                    userRecyclerview.adapter = IssuedBookAdapter(userArrayList)
                }
                binding.progressBar3.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}