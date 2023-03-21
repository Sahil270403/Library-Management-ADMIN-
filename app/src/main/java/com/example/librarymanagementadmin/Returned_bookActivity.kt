package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.databinding.ActivityReturnedBookBinding
import com.example.librarymanagementadmin.models.issued_books
import com.google.firebase.database.*

class Returned_bookActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<issued_books>
    private lateinit var binding : ActivityReturnedBookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReturnedBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerview = findViewById(R.id.sortNameRecyclerView2)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<issued_books>()
        getUserData()
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Returned_Books")

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
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}