package com.adrian.bucayan.logintest.presentation.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {

    fun isEmailValid(email: String): Boolean {
        val expression = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    fun isPasswordValid(password: String?): Boolean {
        val expression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        val pattern: Pattern = Pattern.compile(expression)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isUserNameValid(username: String): Boolean {
        return username.length > 2
    }

}
