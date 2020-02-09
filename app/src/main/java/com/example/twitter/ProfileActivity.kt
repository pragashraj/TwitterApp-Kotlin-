package com.example.twitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    lateinit var nametxt:TextView
    lateinit var emailtxt:TextView
    lateinit var goBackBtn:ImageView
    var myPreference:MyPreference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        nametxt=findViewById(R.id.NametxtValue)as TextView
        emailtxt=findViewById(R.id.EmailtxtValue)as TextView
        goBackBtn=findViewById(R.id.homeBtn)as ImageView
        myPreference= MyPreference(this)
        nametxt.text= myPreference!!.getUserName()
        emailtxt.text=myPreference!!.getUserName()+"@gmail.com"

        goBackBtn.setOnClickListener {
            var intent =Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
