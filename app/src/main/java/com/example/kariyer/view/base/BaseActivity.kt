package com.example.kariyer.view.base

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kariyer.view.login.LoginActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToastShort(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun showToastLong(message:String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    fun showLogD(firstMessage:String, secondMessage:String){
        Log.d(firstMessage, secondMessage)
    }
    fun showLogE(firstMessage: String, secondMessage: String){
        Log.e(firstMessage,secondMessage)
    }
    inline fun <reified T : Context> Context.startActivity() {
        startActivity(Intent(this, T::class.java))
    }
     fun alertDialog(loginPreferencesEditor:SharedPreferences.Editor,loginPreferences:SharedPreferences){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Çıkış yapmak üzeresiniz...")
        alertDialog.setMessage("Çıkış yapmak istiyor musunuz?")


        alertDialog.setPositiveButton("İstiyorum") { _, _ ->

            loginPreferencesEditor.putBoolean("saveLogin",loginPreferences.getBoolean("saveLogin",false))
            loginPreferencesEditor.putBoolean("firstLogin",false)
            loginPreferencesEditor.putString("userName",loginPreferences.getString("userName",""))
            loginPreferencesEditor.putString("password",loginPreferences.getString("password",""))
            //loginPreferencesEditor.clear();
            loginPreferencesEditor.commit()
          startActivity<LoginActivity>()
        }
        alertDialog.setNegativeButton("İptal Et") { _, _-> showToastShort("İptal Edildi") }
        alertDialog.create().show()

    }
}
