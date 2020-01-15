package com.example.twitter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    lateinit var email_editText:EditText
    lateinit var password_editText:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email_editText=findViewById(R.id.emailEt)as EditText
        password_editText=findViewById(R.id.passwordEt)as EditText
        // Toast.makeText(this,"btn clicked",Toast.LENGTH_SHORT).show();
    }

    fun onSignUpBtnClick(v: View)
    {
        var email=email_editText.text.toString()
        var password=password_editText.text.toString()
        var newUser=NewUser(email,password)

    }
}
