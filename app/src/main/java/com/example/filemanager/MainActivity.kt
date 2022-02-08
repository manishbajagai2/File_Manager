package com.example.filemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, HomeFragment()).commit()
    }
}