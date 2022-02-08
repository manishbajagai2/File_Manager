package com.example.filemanager

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class Fileviewholders(itemView : View) : RecyclerView.ViewHolder(itemView) {
    var filename : TextView = itemView.findViewById(R.id.filecontainerfilename)
    var filesize : TextView = itemView.findViewById(R.id.filecontainerfilesize)
    var container : CardView = itemView.findViewById(R.id.container)
    var imgfile : ImageView = itemView.findViewById(R.id.filecontainerfiletype)
}