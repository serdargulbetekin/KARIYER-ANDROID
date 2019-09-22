package com.example.kariyer.view.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.kariyer.R
import com.example.kariyer.model.application.OrderApplication
import com.example.kariyer.model.order.Order
import com.example.kariyer.view.base.BaseActivity
import kotterknife.bindView

class MainActivity : BaseActivity(), MainActivityViewable {


    private val recyclerView: RecyclerView by bindView(R.id.data_rv)

    private lateinit var loginPreferences: SharedPreferences
    private lateinit var loginPreferencesEditor: SharedPreferences.Editor
    private var backButtonCount: Int = 0

    private val viewManager: LinearLayoutManager = LinearLayoutManager(this)
    private val toolbar: Toolbar by bindView(R.id.toolbar_main)

    private val activityMain_buttonLogOut: Button by bindView(R.id.activityMain_buttonLogOut)



    private val mainActivityAdapter by lazy {
        MainActivityAdapter({ item -> onClick(item) })
    }
    private val mainActivityPresenter: MainActivityPresenter by lazy {
        DaggerMainActivityPresenterComponent.builder()
            .viewable(this)
            .appComponent(OrderApplication.get(this).getAppComponent())
            .build()
            .get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        loginPreferencesEditor = loginPreferences.edit()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initUI()
        callOrder()
        logOutClick()
    }
    private fun initUI() {
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }
    }
    private fun callOrder() {
        mainActivityPresenter.getOrder()
        recyclerView.adapter = mainActivityAdapter
    }
    private fun onClick(item: Order) {

    }

    override fun showOrder(orderList: List<Order>) {

        mainActivityAdapter.updateList(orderList)
    }

    override fun showResponseFailure(t: Throwable) {

    }
    private fun logOutClick(){
        activityMain_buttonLogOut.setOnClickListener {
            alertDialog(loginPreferencesEditor,loginPreferences)
        }
    }

    override fun onBackPressed() {
        if (backButtonCount >= 1) {
            val intent = Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            backButtonCount = 0
        } else {
            showToastShort("Uygulamadan çıkmak için tekrar basınız.")
            backButtonCount++
        }
    }
}
