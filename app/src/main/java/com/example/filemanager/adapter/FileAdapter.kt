package com.example.filemanager.adapter

import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.Fileviewholders
import com.example.filemanager.Onfileselectedlistener
import com.example.filemanager.R
import java.io.File
import java.util.*

class FileAdapter(
    private val context : Context,
    private val files : List<File>,
    private val onfileselectedlisteners: Onfileselectedlistener
) : RecyclerView.Adapter<Fileviewholders>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fileviewholders {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.filecontainerlayout, parent, false)
        return Fileviewholders(view)
    }

    override fun onBindViewHolder(holder: Fileviewholders, position: Int) {
        val temp = files[position]
        holder.filename.text = temp.name
        var items = 0
        if(temp.isDirectory){
            val files1 = temp.listFiles()
            for(singlefile in files1){
                if(!singlefile.isHidden)
                    items++
            }
            holder.filesize.text = "$items Files"
            holder.imgfile.setImageResource(R.drawable.folder)
        }else{
            holder.filesize.text = Formatter.formatShortFileSize(context, temp.length())
            getimg(temp, holder)
        }
        holder.container.setOnClickListener { onfileselectedlisteners.onfileclick(temp) }
        holder.container.setOnLongClickListener{
            onfileselectedlisteners.onfilelongclick(temp, position)
            true
        }
    }

    private fun getimg(temp: File, holder: Fileviewholders) {
        if(temp.name.lowercase(Locale.getDefault())
                .endsWith(".jpg") || temp.name.lowercase(Locale.getDefault())
                .endsWith(".jpeg") || temp.name.lowercase(Locale.getDefault())
            .endsWith(".png")) {
            holder.imgfile.setImageResource(R.drawable.images)
        } else if(temp.name.lowercase(Locale.getDefault())
                .endsWith(".pdf") && !temp.isDirectory){
            holder.imgfile.setImageResource(R.drawable.file)
        }else if(temp.name.lowercase(Locale.getDefault())
                .endsWith(".mp3") || temp.name.lowercase(Locale.getDefault())
                .endsWith(".wav")){
            holder.imgfile.setImageResource(R.drawable.audio)
        }else if(temp.name.lowercase(Locale.getDefault())
                .endsWith(".mp4")){
            holder.imgfile.setImageResource(R.drawable.video)
        }

    }

    override fun getItemCount(): Int {
        return files.size
    }
}