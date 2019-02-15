package com.mguru.myapplication.data.remote

import com.mguru.myapplication.helper.AppConstant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun create(): WebService {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.BASE_URL)
                .build().create(WebService::class.java)
    }

}