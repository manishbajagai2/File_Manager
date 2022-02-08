package com.example.filemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.adapter.CategoryviewAdapter
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class HomeCategory : AppCompatActivity() {

    lateinit var category : String
    lateinit var categoryname : TextView
    lateinit var filelink : String
    lateinit var categoryrecycler : RecyclerView
    lateinit var categoryviewAdapter: CategoryviewAdapter
    lateinit var datas : MutableList<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_category)

        category = intent.getStringExtra("category").toString()
        categoryname = findViewById<View>(R.id.homecategory) as TextView
        categoryrecycler = findViewById<View>(R.id.homecategoryrecycler) as RecyclerView
        categoryrecycler.layoutManager = LinearLayoutManager(this)

        datas = ArrayList()
        categoryviewAdapter = CategoryviewAdapter(this@HomeCategory, datas as ArrayList<File>, category)
        categoryrecycler.adapter = categoryviewAdapter
        categoryname.text = category

        filelink = System.getenv("EXTERNAL_STORAGE")
        getting(filelink)


    }

    private fun getting(path: String) {
        val file = File(path)
        if(file.isDirectory && file.canRead()){
            val file1 = file.listFiles()
            for(singlefile in file1){
                if(singlefile.isDirectory && singlefile.canRead()){
                    getting(singlefile.absolutePath)
                }else if(singlefile.isFile && singlefile.canRead()){
                    displayfile(singlefile)
                }
            }
        }else if(file.isFile && file.canRead()){
            displayfile(file)
        }
    }

    private fun displayfile(singlefile: File) {
        when (category){
            "Image" -> if(singlefile.name.lowercase(Locale.getDefault())
                    .endsWith(".png") || singlefile.name.lowercase(Locale.getDefault())
                    .contains(".jpg")
                        || singlefile.name.lowercase(Locale.getDefault())
                .endsWith(".jpeg") || singlefile.name.lowercase(Locale.getDefault())
                .endsWith(".gif")){
                    datas.add(singlefile)
                }
            "Video" -> if(singlefile.name.lowercase(Locale.getDefault())
                    .endsWith(".mp4") || singlefile.name.lowercase(Locale.getDefault())
                    .contains(".avi")){
                    datas.add(singlefile)
                }
            "Audio" -> if(singlefile.name.lowercase(Locale.getDefault())
                    .endsWith(".mp3")){
                        datas.add(singlefile)
                    }
            "Document" -> if(singlefile.name.lowercase(Locale.getDefault())
                    .endsWith(".txt") || singlefile.name.lowercase(Locale.getDefault())
                    .endsWith(".pdf") ){
                    datas.add(singlefile)
            }
        }
    }
}