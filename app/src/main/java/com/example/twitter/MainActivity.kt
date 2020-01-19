package com.example.twitter

import android.content.Context
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        listV=findViewById(R.id.listView)as ListView

       /* var bundle=intent.extras
        userName=bundle!!.getString("name").toString()

        */

        listOfTweets.add(Tweet("","user1","First Tweet",""))
        listOfTweets.add(Tweet("","user2","Second Tweet",""))
        adapter=TweetAdapter(this,listOfTweets)
        listV.adapter=adapter

        userName="raj"
        fab.setOnClickListener { view ->
            var builder= AlertDialog.Builder(this)
            val dialogView=layoutInflater.inflate(R.layout.new_tweet,null)
            builder.setView(dialogView)

            val dialog=builder.create()
            dialog.show()
            dialogView.send.setOnClickListener {
                Toast.makeText(this,"send btn",Toast.LENGTH_SHORT).show()
                //myTweet=dialogView.myTweetText.text.toString()
                if(1>0)
                {
                    sendMyTweet("tweet try")
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
        ApiClient.instance.saveTweet(userName,"",tweetText,"")
            .enqueue(object:Callback<com.example.twitter.Response>{
                override fun onFailure(call: Call<com.example.twitter.Response>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<com.example.twitter.Response>, response: Response<com.example.twitter.Response>) {

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
            view.userName.text=tweet.uname
            view.userTweetText.text=tweet.tweet
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
