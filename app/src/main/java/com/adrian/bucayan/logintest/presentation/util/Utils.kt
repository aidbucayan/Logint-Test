package com.adrian.bucayan.logintest.presentation.util

import android.content.Context
import android.text.Editable
import java.util.regex.Pattern
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context) {

    fun isEmailValid(email: Editable): Boolean {
        val expression = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

   fun isPasswordValid(password: Editable): Boolean {
       val expression = "^(?=.{8,}\$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*\$"
       val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
       val matcher = pattern.matcher(password)
       return matcher.matches()
   }

    fun isUserNameValid(username: String): Boolean {
        return username.length > 2
    }

}
