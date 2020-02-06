package com.example.twitter

class NewUser {
    lateinit var email:String
    lateinit var password:String
    lateinit var username:String

    constructor(email: String, password: String,username:String) {
        this.email = email
        this.password = password
        this.username=username
    }

}