package com.mguru.myapplication.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Todo : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int? = 0

    @SerializedName("userId")
    @Expose
    open var userId: Int? = 0

    @SerializedName("title")
    @Expose
    open var title: String? = null

    @SerializedName("completed")
    @Expose
    open var completed: Boolean? = false

}