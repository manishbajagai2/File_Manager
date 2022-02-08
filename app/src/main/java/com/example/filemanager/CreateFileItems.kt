package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.filemanager.adapter.CreateFileAdapter
import java.io.*
import java.lang.StringBuilder

class CreateFileItems : AppCompatActivity() {
    lateinit var etName : EditText
    lateinit var btCreate : Button
    lateinit var btOpen : Button
    lateinit var sName : String
    lateinit var fileArray : ArrayList<String>
    lateinit var listview : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_file_items)

        etName = findViewById(R.id.et_name)
        btCreate = findViewById(R.id.bt_create)
        btOpen = findViewById(R.id.bt_open)

        fileArray = ArrayList()
        listview = findViewById(R.id.fileList)

        btCreate.setOnClickListener {
            sName = etName.text.toString().trim(' ')
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
             == PackageManager.PERMISSION_GRANTED
            ){
                createFolder()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            }
        }
        btOpen.setOnClickListener {
            sName = etName.text.toString().trim(' ')
            val uri = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/" + sName + "/")
            startActivity(Intent(Intent.ACTION_GET_CONTENT).setDataAndType(uri, "*/*"))
        }
    }

    private fun createFolder() {
        val folder = File(Environment.getExternalStorageDirectory(), sName)
        if(folder.exists()){
            Toast.makeText(applicationContext, "Folder already exists", Toast.LENGTH_LONG).show()
        }else{
            folder.mkdir()
            if(folder.isDirectory){
                Toast.makeText(applicationContext, "Folder is created successfully", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Folder could not be created", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun createFile(view : View){
        val layout = layoutInflater.inflate(R.layout.create_file, null, false)
        AlertDialog.Builder(this)
            .setTitle("Create File")
            .setView(layout)
            .setPositiveButton("Create"){dialog, _ ->
                val myFileName = layout.findViewById<EditText>(R.id.fileName)
                val myFileContent = layout.findViewById<EditText>(R.id.fileContent)
                addFile(myFileName.text.toString(), myFileContent.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Close"){dialog, _ -> dialog.dismiss()}
            .show()
    }

    private fun addFile(file_name: String, file_content: String) {
        try {
            val reader = openFileOutput(file_name, MODE_PRIVATE)
            reader.write(file_content.toByteArray())
            reader.close()
            loadFiles()
        } catch (e : FileNotFoundException){
            e.printStackTrace()
        } catch (e : IOException){
            e.printStackTrace()
        }

    }

    private fun loadFiles() {
        val currentDir = filesDir
        fileArray.clear()
        fileArray.addAll(listOf(*currentDir.list()))
        val adapter = CreateFileAdapter(fileArray,this)
        listview.adapter = adapter
        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            showFileContent(fileArray[position])
        }
        listview.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                .setTitle("Do you want to delete this file ?")
                .setPositiveButton("Yes") {dialog, _ ->
                    delete_file(fileArray[position])
                    dialog.dismiss()
                }
                .setNegativeButton("Close") { dialog, _ -> dialog.dismiss()}
                .show()
            true
        }
    }

    private fun showFileContent(s: String) {
        val sb = StringBuilder()
        val file = File(filesDir,s)
        try{
            val br = BufferedReader(FileReader(file))
            var line : String?
            while(br.readLine().also{line = it} != null){
                sb.append(line)
                sb.append("\n")
            }
        } catch (e : FileNotFoundException){
            e.printStackTrace()
        } catch (e : IOException){
            e.printStackTrace()
        }
        val layout = layoutInflater.inflate(R.layout.create_file, null, false)
        val myFileName = layout.findViewById<EditText>(R.id.fileName)
        val myFileContent = layout.findViewById<EditText>(R.id.fileContent)
        myFileName.setText(s)
        myFileContent.setText(sb)
        AlertDialog.Builder(this)
            .setTitle("Update / Make changes to File")
            .setView(layout)
            .setPositiveButton("Update") {dialog, _ ->
                updateFile(myFileName.text.toString(), myFileContent.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss()}
            .show()
    }

    private fun updateFile(file_name: String, file_content: String) {
        try {
            val reader = openFileOutput(file_name, MODE_PRIVATE)
            reader.write(file_content.toByteArray())
            reader.close()
            loadFiles()
        } catch (e : FileNotFoundException){
            e.printStackTrace()
        } catch (e : IOException){
            e.printStackTrace()
        }
    }

    private fun delete_file(s: String) {
        val file = File(filesDir,s)
        file.delete()
        loadFiles()
    }
}