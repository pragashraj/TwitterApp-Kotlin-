package com.example.twitter

class Tweet {
        var image:String?=null
        var uname:String?=null
        var tweet:String?=null
        var attachment:String?=null

    constructor(image: String?, uname: String?, tweet: String?, attachment: String?) {
        this.image = image
        this.uname = uname
        this.tweet = tweet
        this.attachment = attachment
    }
}