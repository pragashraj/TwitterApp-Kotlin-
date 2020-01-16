package com.example.twitter

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_tweet.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var myTweet:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            var builder= AlertDialog.Builder(this)
            val dialogView=layoutInflater.inflate(R.layout.new_tweet,null)
            builder.setView(dialogView)

            val dialog=builder.create()
            dialog.show()
            dialogView.send.setOnClickListener {
                Toast.makeText(this,"send btn",Toast.LENGTH_SHORT).show()
                myTweet=dialogView.myTweetText.text.toString()
                if(myTweet.length>0)
                {
                    sendMyTweet(myTweet)
                }else{
                    Toast.makeText(this,"No Tweets available",Toast.LENGTH_SHORT).show()
                }

            }
            dialogView.attachment.setOnClickListener {
                Toast.makeText(this,"attachment btn",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun myDetails()
    {
        ApiClient.instance.getMyDetails()
            .enqueue(object : Callback<NewUser>{
                override fun onFailure(call: Call<NewUser>, t: Throwable) {

                }

                override fun onResponse(call: Call<NewUser>, response: Response<NewUser>) {

                }

            })
    }


    fun sendMyTweet(tweetText:String)
    {

    }
}
