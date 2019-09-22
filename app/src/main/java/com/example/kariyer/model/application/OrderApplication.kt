package com.example.kariyer.model.application

import android.app.Activity
import android.app.Application
import com.example.kariyer.model.core.AppComponent
import com.example.kariyer.model.core.AppModule
import com.example.kariyer.model.core.DaggerAppComponent
import com.example.kariyer.model.core.NetModule

class OrderApplication: Application(){
    private var appComponent:AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()



    }

    companion object{
        fun get(activity: Activity):OrderApplication{
            return  activity.application as OrderApplication
        }
    }


    fun getAppComponent():AppComponent{
        return appComponent!!
    }

}