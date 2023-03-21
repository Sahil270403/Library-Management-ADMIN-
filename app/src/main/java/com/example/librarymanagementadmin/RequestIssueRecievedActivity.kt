package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.databinding.ActivityRequestIssueRecievedBinding
import com.example.librarymanagementadmin.models.issue_details
import com.google.firebase.database.*

class RequestIssueRecievedActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var binding: ActivityRequestIssueRecievedBinding
    private lateinit var userArrayList : ArrayList<issue_details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestIssueRecievedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerview = findViewById(R.id.sortNameRecyclerView)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<issue_details>()
        getUserData()
    }
        private fun getUserData() {

            dbref = FirebaseDatabase.getInstance().getReference("Issue_details_request")

            dbref.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        userArrayList.clear()

                        for (userSnapshot in snapshot.children){

                            val user = userSnapshot.getValue(issue_details::class.java)
                            userArrayList.add(user!!)
                        }

                        userRecyclerview.adapter = BookAdapter(userArrayList)
                    }
                    binding.progressBar2.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
    }