package com.example.twitter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import retrofit2.Call
import retrofit2.Callback


class SignUpActivity : AppCompatActivity() {

    lateinit var email_editText:EditText
    lateinit var password_editText:EditText
    lateinit var email:String
    lateinit var password:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email_editText=findViewById(R.id.emailEt)as EditText
        password_editText=findViewById(R.id.passwordEt)as EditText
        // Toast.makeText(this,"btn clicked",Toast.LENGTH_SHORT).show()
    }

    fun onSignUpBtnClick(v: View)
    {
        email=email_editText.text.toString()
        password=password_editText.text.toString()
        ApiClient.instance.signUp(email,password,splitEmail(email))
            .enqueue(object:Callback<Response>{
                override fun onFailure(call: Call<Response>, t: Throwable) {

                }

                override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@SignUpActivity,"Sign Up is Not Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@SignUpActivity,"Sign Up is  Success",Toast.LENGTH_SHORT).show()
                        email_editText.setText("")
                        password_editText.setText("")
                        startProfileActivity()
                    }
                }

            })

            Toast.makeText(this@SignUpActivity,"Sign Up is  Success",Toast.LENGTH_SHORT).show()
            email_editText.setText("")
            password_editText.setText("")
            startProfileActivity()
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
