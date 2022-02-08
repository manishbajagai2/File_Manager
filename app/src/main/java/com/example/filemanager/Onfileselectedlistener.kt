package com.example.filemanager

import java.io.File

interface Onfileselectedlistener {
    fun onfileclick(file : File)
    fun onfilelongclick(file : File, position : Int)
}