package com.example.twitter

import android.content.Context

class MyPreference(context: Context) {
    var PREFERENECE_NAME="AuthPreference"
    var isLogged=""
    var preference=context.getSharedPreferences(PREFERENECE_NAME,Context.MODE_PRIVATE)

    fun getIsLogged():Boolean{
        return preference.getBoolean(isLogged,false)
    }

    fun setIsLogged(boolean: Boolean){
        var editor=preference.edit()
        editor.putBoolean(isLogged,boolean)
        editor.apply()
    }
}