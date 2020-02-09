package com.example.twitter

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var myTweet:String
    lateinit var userName:String
    var listOfTweets=ArrayList<Tweet>()
    lateinit var listV:ListView
    var adapter:TweetAdapter?=null
    lateinit var myPreference: MyPreference
    var liked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        listV=findViewById(R.id.listView)as ListView
        myPreference= MyPreference(this)
        userName=myPreference.getUserName()

            fab.setOnClickListener { view ->
                var builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.new_tweet, null)
                dialogView.myName.text = userName
                builder.setView(dialogView)

                val dialog = builder.create()
                dialog.show()
                dialogView.send.setOnClickListener {
                    myTweet = dialogView.myTweetText.text.toString()
                    if (myTweet.length > 0) {
                        var tweet = Tweet(userName, myTweet)
                        sendTweet(tweet)
                        dialog.cancel()
                        listOfTweets.clear()
                        getTweet()
                    } else {
                        Toast.makeText(this, "No Tweets available", Toast.LENGTH_SHORT).show()
                    }

                }
                dialogView.attachment.setOnClickListener {
                    Toast.makeText(this, "attachment btn", Toast.LENGTH_SHORT).show()
                }

            }
    }

    override fun onStart() {
        super.onStart()
        getTweet()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Profile->{
                var intent =Intent(this,ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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

    fun getTweet(){
        ApiClient.instance.getAllTweet()
            .enqueue(object :Callback<List<Tweet>>{
                override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"failed to Load:Check Your Connection!!!",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                    if(!response.isSuccessful){
                        Toast.makeText(this@MainActivity,"responsed wrongly by getTweet",Toast.LENGTH_SHORT).show()
                    }else{
                        var list_Tweet=response.body()
                        for ( i in 0..list_Tweet!!.size-1){
                            listOfTweets.add(list_Tweet[i])
                        }
                        adapter = TweetAdapter(this@MainActivity, listOfTweets,liked)
                        listV.adapter = adapter
                    }
                }

            })
    }

    class TweetAdapter: BaseAdapter {
        var listOfTweets=ArrayList<Tweet>()
        var context:Context?=null
        var liked:Boolean?=null
        constructor(context:Context, listOfTweets :ArrayList<Tweet>,liked:Boolean):super(){
            this.context=context
            this.listOfTweets=listOfTweets
            this.liked=liked
        }
        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var tweet=listOfTweets[p0]
            var inflater=context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater
            var view=inflater.inflate(R.layout.tweet,null)
            view.userName.text=tweet.username
            view.userTweetText.text=tweet.tweet
            view.likeImage.setOnClickListener {
                if(liked as Boolean) {
                    view.likeImage.setImageResource(R.drawable.heart)
                    liked=false
                }else{
                    view.likeImage.setImageResource(R.drawable.heartred)
                    liked=true
                }
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
        val pid = android.os.Process.myPid()
        android.os.Process.killProcess(pid)
    }

}
