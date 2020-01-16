package com.example.twitter

class NewUser {
    lateinit var mail:String
    lateinit var password:String
    lateinit var username:String

    constructor(mail: String, password: String,username:String) {
        this.mail = mail
        this.password = password
        this.username=username
    }

}