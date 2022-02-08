package com.example.filemanager.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.HomeCategory
import com.example.filemanager.Homearraylist
import com.example.filemanager.Model1
import com.example.filemanager.R

class HomearraylistAdapter(var model1ArrayList : ArrayList<Model1>, var context : Context) :
RecyclerView.Adapter<Homearraylist>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Homearraylist {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.homesingle, parent, false)
        return Homearraylist(view)
    }

    override fun onBindViewHolder(holder: Homearraylist, position: Int) {
        val temp = model1ArrayList[position]
        holder.img.setImageResource(temp.image)
        holder.tx1.text = temp.text1
        holder.itemView.setOnClickListener{
            Toast.makeText(context, "Fetching ${holder.tx1.text}s", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, HomeCategory ::class.java)
            intent.putExtra("category", temp.text1)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return model1ArrayList.size
    }

}