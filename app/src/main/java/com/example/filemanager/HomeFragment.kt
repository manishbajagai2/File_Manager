package com.example.filemanager

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanager.adapter.HomearraylistAdapter
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.jar.Manifest

class HomeFragment : Fragment() {
    lateinit var recyclerView1 : RecyclerView
    lateinit var model1ArrayList : ArrayList<Model1>
    lateinit var otherFiles : TextView
    lateinit var gotolocal : LinearLayout
    lateinit var create : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homefragment, container, false)
        recyclerView1 = view.findViewById(R.id.homerecycler1)
        otherFiles = view.findViewById(R.id.other)
        gotolocal = view.findViewById(R.id.gotolocal)
        gotolocal.setOnClickListener {
            requireFragmentManager().beginTransaction().replace(R.id.framelayout, Internalfragment())
                .addToBackStack(null).commit()
        }

        create = view.findViewById(R.id.create)
        create.setOnClickListener {
            var intent = Intent(requireContext(), CreateFileItems::class.java)
            startActivity(intent)
        }
        runtimepermission()
        return view

    }

    private fun setRecycler1() {
        model1ArrayList = ArrayList()
        model1ArrayList.add(Model1("Image", R.drawable.images_icon))
        model1ArrayList.add(Model1("Video", R.drawable.video_icon))
        model1ArrayList.add(Model1("Audio", R.drawable.audio_icon))
        model1ArrayList.add(Model1("Document", R.drawable.document_icon))
        recyclerView1.layoutManager = GridLayoutManager(context, 2)
        recyclerView1.adapter = HomearraylistAdapter(model1ArrayList, requireContext())
    }

    private fun runtimepermission(){
        Dexter.withContext(context).withPermissions(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if(p0!!.areAllPermissionsGranted()){
                    setRecycler1()
                    Toast.makeText(requireContext(),"Permission Granted",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),"Permission Denied. Please try again",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1!!.continuePermissionRequest()
            }
        }).check()
    }
}