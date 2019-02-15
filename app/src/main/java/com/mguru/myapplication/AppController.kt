package com.mguru.myapplication

import android.app.Application
import com.mguru.myapplication.helper.AppConstant.DATABASE_NAME
import com.mguru.myapplication.helper.AppConstant.DATABASE_VERSION
import io.realm.Realm
import io.realm.RealmConfiguration
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        //init realm database which cover all the object
        Realm.init(this)
        val config = RealmConfiguration.Builder().schemaVersion(DATABASE_VERSION)
                .name(DATABASE_NAME)
                .build()
        Realm.setDefaultConfiguration(config)

        //Apply Custom default fonts
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

    }

}