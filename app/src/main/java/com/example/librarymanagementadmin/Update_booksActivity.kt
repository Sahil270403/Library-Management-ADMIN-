package com.example.librarymanagementadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.databinding.ActivityUpdateBooksBinding
import com.example.librarymanagementadmin.models.book_details
import com.google.firebase.database.*

class Update_booksActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<book_details>
    private lateinit var ProgressBar: ProgressBar
    private lateinit var binding : ActivityUpdateBooksBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBooksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ProgressBar = findViewById(R.id.progressBar1)
        ProgressBar.visibility = View.VISIBLE

        userRecyclerview = findViewById(R.id.sortNameRecyclerView3)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<book_details>()
        getUserData()
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Book_details")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    userArrayList.clear()

                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(book_details::class.java)
                        userArrayList.add(user!!)
                    }

                    userRecyclerview.adapter = BookAdapter1(userArrayList)
                }
                ProgressBar.visibility = View.GONE
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
