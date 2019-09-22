package com.example.kariyer.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import com.example.kariyer.R
import com.example.kariyer.view.base.BaseActivity
import com.example.kariyer.view.main.MainActivity
import kotterknife.bindView

class LoginActivity : BaseActivity() {

    private val editTextUserName: EditText by bindView(R.id.activityLogin_editText_userName)
    private val editTextPassword: EditText by bindView(R.id.activityLogin_editText_password)
    private val switch: Switch by bindView(R.id.activityLogin_switch)
    private val loginButton: Button by bindView(R.id.activityLogin_buttonLogin)

    private lateinit var loginPreferences:SharedPreferences
    private lateinit var loginPreferencesEditor:SharedPreferences.Editor
    private var saveLogin:Boolean = false
    private var firstLogin = false

    private var userName:String = ""
    private var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        loginPreferencesEditor = loginPreferences.edit()

        saveLogin = loginPreferences.getBoolean("saveLogin", false)
        firstLogin = loginPreferences.getBoolean("firstLogin", false)

         if(saveLogin ){
             if(!firstLogin){
                 editTextUserName.setText(loginPreferences.getString("username",""))
                 editTextPassword.setText(loginPreferences.getString("password",""))
                 switch.isChecked = true
             }
             else{
                 startActivity<MainActivity>()
             }

        }
        onClickLoginButton()
    }

    private fun onClickLoginButton(){


        loginButton.setOnClickListener {

            userName = editTextUserName.text.toString()
            password = editTextPassword.text.toString()
            val check = LoginCheck.editTextCheck(userName,password)

            if (check != "GO"){
                showToastShort(check)
            }
            else{
                if (switch.isChecked){
                    loginPreferencesEditor.putBoolean("saveLogin", true)
                    loginPreferencesEditor.putBoolean("firstLogin", true)
                    loginPreferencesEditor.putString("username", userName)
                    loginPreferencesEditor.putString("password", password)
                    loginPreferencesEditor.commit();
                } else {
                    loginPreferencesEditor.clear();
                    loginPreferencesEditor.commit();
                }
                startActivity<MainActivity>()

            }


        }
    }

}
