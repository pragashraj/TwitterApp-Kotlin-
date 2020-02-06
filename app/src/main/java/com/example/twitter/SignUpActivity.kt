package com.example.twitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    lateinit var email_editText:EditText
    lateinit var password_editText:EditText
    lateinit var email:String
    lateinit var password:String
    lateinit var myPreference: MyPreference
    var ISLOGGED:Boolean?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email_editText=findViewById(R.id.emailEt)as EditText
        password_editText=findViewById(R.id.passwordEt)as EditText
        myPreference= MyPreference(this)
        ISLOGGED=myPreference.getIsLogged()

        if(ISLOGGED as Boolean){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun onSignUpBtnClick(v: View)
    {
        email=email_editText.text.toString()
        password=password_editText.text.toString()

        if(email.length>0 && password.length>0) {
            var user=NewUser(email,password,splitEmail(email))
            ApiClient.instance.Regeister(user)
                .enqueue(object:Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@SignUpActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(this@SignUpActivity, "Sign Up is  Success", Toast.LENGTH_SHORT).show()
                        email_editText.setText("")
                        password_editText.setText("")
                        myPreference.setIsLogged(true)
                        myPreference.setUserName(splitEmail(email))
                        startProfileActivity()
                    }

                })


        }
    }

    fun splitEmail(emailString: String):String
    {
        var mail=emailString.split("@")
        return mail[0]
    }

    fun startProfileActivity()
    {
        var intent=Intent(this,ProfilePicActivity::class.java)
        intent.putExtra("username",splitEmail(email))
        startActivity(intent)
    }



}
