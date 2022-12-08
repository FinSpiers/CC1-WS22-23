package com.example.myweather.feature_weather.domain.util

import android.content.Context
import com.example.myweather.R

object WindDegreeConverter {
    fun convertToDirection(degree: Int, context: Context?): String {
        if (context != null) {
            return when (degree) {
                in 26..64 -> {
                    context.applicationContext.resources.getString(R.string.ne)
                }
                in 65..115 -> {
                    context.applicationContext.resources.getString(R.string.e)
                }
                in 116..154 -> {
                    context.applicationContext.resources.getString(R.string.se)
                }
                in 155..205 -> {
                    context.applicationContext.resources.getString(R.string.s)
                }
                in 206..244 -> {
                    context.applicationContext.resources.getString(R.string.sw)
                }
                in 245..295 -> {
                    context.applicationContext.resources.getString(R.string.w)
                }
                in 296..334 -> {
                    context.applicationContext.resources.getString(R.string.nw)
                }
                else -> context.applicationContext.resources.getString(R.string.n)
            }
        }
        return "F"
    }
}