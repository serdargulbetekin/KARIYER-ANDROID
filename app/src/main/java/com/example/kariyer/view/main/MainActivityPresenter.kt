package com.example.kariyer.view.main

import android.util.Log
import com.example.kariyer.apiprocess.RestApi
import com.example.kariyer.model.core.ActivityScope
import com.example.kariyer.model.core.AppComponent
import com.example.kariyer.model.core.BaseAppComponentBuilder
import com.example.kariyer.model.order.Order
import com.example.kariyer.model.order.OrderRepo
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class MainActivityPresenter @Inject constructor(
    private val orderRepo: OrderRepo,
    private val mainActivityViewable: MainActivityViewable
) {


    fun getOrder() {
        orderRepo.getOrder()
            .subscribe(object : Observer<List<Order>> {
                override fun onComplete() {
                    Log.d("OnComplete: ", "Completed")
                }
                override fun onError(e: Throwable) {

                    Log.d("OnError: ", e.message.toString())

                }
                override fun onNext(orderList: List<Order>) {
                    mainActivityViewable.showOrder(orderList)

                }
                override fun onSubscribe(d: Disposable) {
                    Log.d("OnSubscirbe: ", d.toString())

                }
            })
    }
}

interface MainActivityViewable {
    fun showOrder(orderList: List<Order>)
    fun showResponseFailure(t: Throwable)
}

@Module
class MainActivityModule {

    @Provides
    fun providesOrderRepo(restApi: RestApi):OrderRepo {
        return OrderRepo(restApi)
    }

}

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainActivityModule::class])
interface MainActivityPresenterComponent{

    fun get():MainActivityPresenter

    @Component.Builder
    interface BuilderApp:BaseAppComponentBuilder<BuilderApp,MainActivityPresenterComponent>{
        @BindsInstance
        fun viewable(viewable: MainActivityViewable):BuilderApp
    }

}