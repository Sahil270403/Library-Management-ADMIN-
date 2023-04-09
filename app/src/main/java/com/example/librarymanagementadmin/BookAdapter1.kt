package com.example.librarymanagementadmin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.models.book_details

class BookAdapter1 (private val userList: ArrayList<book_details>) : RecyclerView.Adapter<BookAdapter1.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book2, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.BookName.text = currentitem.bookName
        holder.AuthorName.text = currentitem.authorName
        holder.branch.text = currentitem.branch
        holder.Description.text = currentitem.description

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, Update_Book_detailsActivity::class.java)
            // pass any extras you need to the activity using intent.putExtra(key, value)
            intent.putExtra("BookName", currentitem.bookName)
            intent.putExtra("AuthorName", currentitem.authorName)
            intent.putExtra("Branch", currentitem.branch)
            intent.putExtra("Description", currentitem.description)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val BookName: TextView = itemView.findViewById(R.id.userName)
        val AuthorName: TextView = itemView.findViewById(R.id.rollNo)
        val branch: TextView = itemView.findViewById(R.id.branch)
        val Description: TextView = itemView.findViewById(R.id.descrpt)
    }
}


