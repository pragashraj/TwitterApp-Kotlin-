package com.example.twitter

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_profile_pic.*
import okhttp3.Callback
import okhttp3.MediaType
import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody
import retrofit2.Call


class ProfilePicActivity : AppCompatActivity() {

    var GALLERY_REQUEST_CODE=1
    var CAMERA_REQUEST_CODE=2
    var GALLERY_CODE=3
    var CAMERA_CODE=4
    lateinit var username:String
    private val selectOptions = arrayOf<CharSequence>("Select photo", "Capture a photo", "Cancel")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_pic)
        var bundle=intent.extras
        username= bundle!!.getString("username").toString()
    }

    fun onChooseBtnClick(v:View)
    {
        permissionCheck()
    }

    fun onNextBtnClick(v:View)
    {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Alert!!!")
        builder.setMessage("Are you sure about your Profile Picture?")
        builder.setPositiveButton("Yes",{dialogInterface, i ->
            var intent=Intent(this,MainActivity::class.java)
            intent.putExtra("name",username)
            startActivity(intent)
        })
        builder.setNegativeButton("No",{dialogInterface, i ->
            dialogInterface.cancel()
        })
        builder.show()
    }

    fun openDialog()
    {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setItems(selectOptions,
            DialogInterface.OnClickListener { dialog, position ->
                when (position) {
                    0 -> loadGallery()
                    1 -> loadcamera()
                    2 -> dialog.dismiss()
                    else -> dialog.dismiss()
                }
            })
        val dialog1 = alertDialog.create()
        dialog1.show()
    }

    fun permissionCheck()
    {
        if(Build.VERSION.SDK_INT>23)
        {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),GALLERY_REQUEST_CODE)
            }
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),CAMERA_REQUEST_CODE)
            }
        }
        openDialog()
    }

    fun loadGallery()
    {
        var intent=Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent,GALLERY_CODE)

    }

    fun loadcamera()
    {
        Toast.makeText(this,"camera",Toast.LENGTH_SHORT).show();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GALLERY_CODE && resultCode== Activity.RESULT_OK && data!=null)
        {
            val imagedata=data.data
            val filepath= arrayOf(MediaStore.Images.Media.DATA)
            val cursor=contentResolver.query(imagedata!!,filepath,null,null,null)
            cursor!!.moveToFirst()
            val index=cursor.getColumnIndex(filepath[0])
            val path=cursor.getString(index)
            cursor.close()
            profile_image.setImageBitmap(BitmapFactory.decodeFile(path))
            val destinationFile = File(path)
            upload(destinationFile)
        }
    }

    fun upload(file: File)
    {
        val filepart = MultipartBody.Part.createFormData("image", file.name, RequestBody.create(MediaType.parse("image/*"), file))
        ApiClient.instance.sendImage(filepart,username)
            .enqueue(object:retrofit2.Callback<Response>{
                override fun onFailure(call: Call<Response>, t: Throwable) {

                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if(!response.isSuccessful)
                    {
                        Toast.makeText(this@ProfilePicActivity,"Not Success",Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }


}
