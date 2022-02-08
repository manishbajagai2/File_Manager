package com.example.filemanager

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Homearraylist(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var img : ImageView = itemView.findViewById(R.id.singleimghome)
    var tx1 : TextView = itemView.findViewById(R.id.singletexthome)
}