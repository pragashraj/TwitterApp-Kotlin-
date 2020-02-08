package com.example.twitter

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_tweet.view.*
import kotlinx.android.synthetic.main.tweet.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var myTweet:String
    lateinit var userName:String
    var listOfTweets=ArrayList<Tweet>()
    lateinit var listV:ListView
    var adapter:TweetAdapter?=null
    lateinit var myPreference: MyPreference
    var byteArray:ByteArray?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        listV=findViewById(R.id.listView)as ListView
        myPreference= MyPreference(this)
        userName=myPreference.getUserName()

        try {
            listOfTweets.add(Tweet(userName, "First1"))
            listOfTweets.add(Tweet("kamal", "First1"))
            listOfTweets.add(Tweet(userName, "First1"))
            listOfTweets.add(Tweet("kamal", "First1"))
            adapter = TweetAdapter(this, listOfTweets)
            listV.adapter = adapter
        }catch (e:Exception){
            Toast.makeText(this,"excep",Toast.LENGTH_SHORT).show()
        }


        fab.setOnClickListener { view ->
            myDetails()
            var builder= AlertDialog.Builder(this)
            val dialogView=layoutInflater.inflate(R.layout.new_tweet,null)
            dialogView.myName.text=userName
            dialogView.myImage.setImageURI(Uri.parse(byteArray.toString()))
            builder.setView(dialogView)

            val dialog=builder.create()
            dialog.show()
            dialogView.send.setOnClickListener {
                myTweet=dialogView.myTweetText.text.toString()
                if(myTweet.length>0)
                {
                    var tweet=Tweet(userName,myTweet)
                    sendTweet(tweet)
                    dialog.cancel()
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
        try {
            ApiClient.instance.getProfilePic("amal")
                .enqueue(object : Callback<com.example.twitter.Response> {
                    override fun onFailure(call: Call<com.example.twitter.Response>, t: Throwable) {
                        Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<com.example.twitter.Response>, response: Response<com.example.twitter.Response>) {
                        if (!response.isSuccessful) {
                            Toast.makeText(this@MainActivity, response.code(), Toast.LENGTH_SHORT).show()
                        } else {
                            byteArray = response.body()!!.byteArray
                        }
                    }

                })
        }catch (e:Exception){
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun sendTweet(tweet: Tweet){

        ApiClient.instance.sendTweet(tweet)
            .enqueue(object :Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@MainActivity, response.code(), Toast.LENGTH_SHORT).show()
                    }else{

                    }
                }

            })

    }


    class TweetAdapter: BaseAdapter {
        var listOfTweets=ArrayList<Tweet>()
        var context:Context?=null
        constructor(context:Context, listOfTweets :ArrayList<Tweet>):super(){
            this.context=context
            this.listOfTweets=listOfTweets
        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var tweet=listOfTweets[p0]
            var inflater=context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
            var view=inflater.inflate(R.layout.tweet,null)
            view.userName.text=tweet.username
            view.userTweetText.text=tweet.tweet
            view.likeImage.setOnClickListener {
                Toast.makeText(context,"liked",Toast.LENGTH_SHORT).show()
            }
            return  view
        }

        override fun getItem(p0: Int): Any {
            return listOfTweets[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
            return listOfTweets.size
        }

    }

}
