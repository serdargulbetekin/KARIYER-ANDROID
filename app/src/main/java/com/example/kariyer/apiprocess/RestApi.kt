package com.example.kariyer.apiprocess

import androidx.annotation.Keep
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface RestApi {
    @Keep
    @GET(" ")
    fun getOrder(): Observable<ResponseBody>
}

