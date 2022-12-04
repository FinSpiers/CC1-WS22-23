package com.example.myweather.feature_weather.data.data_source.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import com.example.myweather.feature_weather.data.data_source.network.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(context : Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

        // TODO: check if correct
        val networkInfo = connectivityManager.activeNetwork
        return networkInfo != null && connectivityManager.isDefaultNetworkActive

    }
}