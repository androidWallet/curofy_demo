package com.mguru.myapplication.ui.base

interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun isNetworkConnected():Boolean

}