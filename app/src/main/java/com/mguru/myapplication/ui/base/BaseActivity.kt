package com.mguru.myapplication.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mguru.myapplication.utils.CommonUtils
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity : AppCompatActivity(), MvpView {

    private var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun showLoading() {
        mDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        if (mDialog != null && mDialog!!.isShowing) {
            mDialog!!.cancel()
        }
    }

    override fun isNetworkConnected(): Boolean {
        return CommonUtils.isNetworkConnected(applicationContext)
    }

    abstract fun initialSetup()

}
