package com.example.librarymanagementadmin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagementadmin.models.issued_books
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class IssuedBookAdapter(private val userList: ArrayList<issued_books>) : RecyclerView.Adapter<IssuedBookAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book1, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.BookName.text = currentitem.bookName
        holder.AuthorName.text = currentitem.authorName
        holder.branch.text = currentitem.branch
        holder.IssuerName.text = currentitem.issuerName
        holder.Rollno.text = currentitem.rollNo

        // Convert timestamp to date string
        val timestamp = currentitem.startDate?.get("date") as Long
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(date)
        holder.requestdate.text = formattedDate

        val timestamp1 = currentitem.endDate?.get("date") as Long
        val date1 = Date(timestamp1)
        val dateFormat1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate1 = dateFormat1.format(date1)
        holder.returnDate.text = formattedDate1

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, BookReturnedActivity::class.java)

            intent.putExtra("BookName", currentitem.bookName)
            intent.putExtra("AuthorName", currentitem.authorName)
            intent.putExtra("Branch", currentitem.branch)
            intent.putExtra("IssueName", currentitem.issuerName)
            intent.putExtra("Rollno", currentitem.rollNo)
            intent.putExtra("IssueDate", formattedDate)
            intent.putExtra("ReturnDate", formattedDate1)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val BookName: TextView = itemView.findViewById(R.id.userName)
        val AuthorName: TextView = itemView.findViewById(R.id.AuthorName)
        val branch: TextView = itemView.findViewById(R.id.branch)
        val IssuerName: TextView = itemView.findViewById(R.id.IssuerName)
        val Rollno: TextView = itemView.findViewById(R.id.Rollno)
        val requestdate: TextView = itemView.findViewById(R.id.requestdate)
        val returnDate: TextView = itemView.findViewById(R.id.returndate)
    }
}
