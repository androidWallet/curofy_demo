package com.mguru.myapplication.data.remote

import com.mguru.myapplication.data.model.Todo
import retrofit2.Call
import retrofit2.http.GET


interface WebService {

    @GET("/todos")
    fun getTodoList(): Call<List<Todo>>

}