package com.example.twitter

import android.content.Context

class MyPreference(context: Context) {
    var PREFERENECE_NAME="AuthPreference"
    var isLogged=""
    var username:String?=null
    var preference=context.getSharedPreferences(PREFERENECE_NAME,Context.MODE_PRIVATE)

    fun getIsLogged():Boolean{
        return preference.getBoolean(isLogged,false)
    }

    fun getUserName(): String{
        return preference.getString(username,"admin").toString()
    }

    fun setUserName(name:String){
        var editor=preference.edit()
        editor.putString(username,name)
        editor.apply()
    }

    fun setIsLogged(boolean: Boolean){
        var editor=preference.edit()
        editor.putBoolean(isLogged,boolean)
        editor.apply()
    }
}