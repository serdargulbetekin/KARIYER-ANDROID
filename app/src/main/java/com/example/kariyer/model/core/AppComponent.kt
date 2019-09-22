package com.example.kariyer.model.core

import android.content.Context
import com.example.kariyer.apiprocess.RestApi
import com.example.kariyer.constants.URL_WEB
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [AppModule::class, NetModule::class])
@AppScope
interface AppComponent{
    fun getContext(): Context
    fun getApiInterface():RestApi
    fun getRetrofit(): Retrofit

}
interface BaseAppComponentBuilder<out T, out K>{
    fun appComponent(appComponent: AppComponent):T
    fun build():K
}
@Module
class AppModule(private val context: Context){

    @Provides
    @AppScope
    fun provideContext(): Context {
        return  context
    }
}
@Module
class NetModule{
    @Provides
    @AppScope
    internal fun provideNetworkService(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }
    @Provides
    @AppScope
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_WEB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}