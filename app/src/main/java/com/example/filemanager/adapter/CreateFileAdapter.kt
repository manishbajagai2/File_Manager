package com.example.filemanager.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CreateFileAdapter(var fileArray : ArrayList<String>, var myContext : Context) : BaseAdapter(){
    override fun getCount(): Int {
        return fileArray.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var cv = convertView
        if(cv == null){
            val textView = TextView(myContext)
            textView.setTextColor(Color.rgb(0,0,0))
            textView.textSize = 20F
            textView.text = fileArray[position]
            textView.height = 125
            cv = textView
        }
        return cv
    }
}