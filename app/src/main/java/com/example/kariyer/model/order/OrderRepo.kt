package com.example.kariyer.model.order

import android.os.Parcelable
import android.util.Log
import com.example.kariyer.apiprocess.RestApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import javax.inject.Inject

@Parcelize
data class Order(val date:String ="",
                val month:String="",
                val marketName:String="",
                val orderName:String="",
                val productPrice:Double=0.0,
                val productState:String="",
                val productDetail:ProductDetail): Parcelable
@Parcelize
 data class ProductDetail(val orderDetail:String ="",
                         val summaryPrice:Double=0.0): Parcelable


class OrderRepo @Inject constructor(private val restApi: RestApi
) {

    private var newList = listOf<Order>()

    fun getOrder(): Observable<List<Order>> {
            return restApi.getOrder()
                .map {  responseBody ->
                    newList = parseJSON(responseBody).toList()
                    newList
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun parseJSON(responseBody: ResponseBody): MutableList<Order>{


        val orderList = mutableListOf<Order>()

        try {
            val jsonArray = JSONArray(responseBody.string())

            for (x in 0 until jsonArray.length()) {


                val jsonObjectData = jsonArray.getJSONObject(x)
                val date = jsonObjectData.optString("date", "")
                val month = jsonObjectData.optString("month")
                val marketName = jsonObjectData.optString("marketName")
                val orderName = jsonObjectData.optString("orderName")
                val productPrice = jsonObjectData.optDouble("productPrice")
                val productState = jsonObjectData.optString("productState")
                val productDetailObject = jsonObjectData.optJSONObject("productDetail")
                val orderDetail = productDetailObject!!.optString("orderDetail")
                val summaryPrice = productDetailObject.optDouble("summaryPrice")

                val productDetail = ProductDetail(orderDetail,summaryPrice)

                val symbol = Order(
                    date = date,
                    month = month,
                    marketName = marketName,
                    orderName = orderName,
                    productPrice = productPrice,
                    productState = productState,
                    productDetail = productDetail)
                orderList.add(symbol)

            }
        } catch (e: JSONException) {

        }
        Log.d("OrderList ", orderList.toString())
        return orderList

    }




}