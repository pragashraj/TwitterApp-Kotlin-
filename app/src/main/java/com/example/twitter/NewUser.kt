package com.example.twitter

class NewUser {
    lateinit var mail:String
    lateinit var password:String

    constructor(mail: String, password: String) {
        this.mail = mail
        this.password = password
    }

}