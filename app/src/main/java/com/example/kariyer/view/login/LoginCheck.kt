package com.example.kariyer.view.login

import android.text.TextUtils

class LoginCheck {

    companion object {
        fun editTextCheck(userName: String, password: String): String {
            return if (TextUtils.isEmpty(userName)) {
                "UserName not provided"
            } else if (TextUtils.isEmpty(password)) {
                "Password not provided"
            } else {
                if (password == "2019ADev" && userName == "kariyer") {
                    "GO"
                } else {
                    "WRONG USERNAME OR PASSWORD. TRY AGAIN"
                }
            }

        }
    }
}