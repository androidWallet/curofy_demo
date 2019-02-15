package com.mguru.myapplication.ui.activity

import com.mguru.myapplication.data.remote.RetrofitFactory
import com.mguru.myapplication.data.database.LocalDB
import com.mguru.myapplication.data.model.Todo
import retrofit2.Call
import retrofit2.Callback

class MainPresenterImpl(mainView: MainView) : MainPresenter {
    private var mMainView: MainView? = null

    init {
        mMainView = mainView
    }

    private var mListTodo: List<Todo>? = null

    override fun todoList() {
        //get data from local db
        mListTodo = LocalDB.getToDoListFromLocal()

        //check data exist or not in local db
        if (mListTodo!!.isEmpty()) {

            //check internet connection
            val networkStatus = mMainView!!.isNetworkConnected()
            if (!networkStatus) {
                mMainView!!.handleError(100)
                return
            }

            //show loading dialog
            mMainView!!.showLoading()

            //make network call for getting list of data
            val service = RetrofitFactory.create()
            val call = service.getTodoList()

            call.enqueue(object : Callback<List<Todo>> {
                override fun onResponse(call: Call<List<Todo>>, response: retrofit2.Response<List<Todo>>?) {

                    //hide loading dialog
                    mMainView!!.hideLoading()

                    //check response or not
                    if (response != null) {
                        //get body of response
                        val todo = response.body()
                        //save data in local db
                        LocalDB.saveToDoList(todo!!)
                        //set data in view
                        mMainView!!.bindTodoList(todo)
                    } else {
                        //handle exception
                        mMainView!!.handleError(101)
                    }
                }

                override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                    //hide loading dialog
                    mMainView!!.hideLoading()
                    //handle exception
                    mMainView!!.handleError(0)

                    t.printStackTrace()
                }

            })

        } else {
            //set data in view
            mMainView!!.bindTodoList(mListTodo!!)
        }
    }

    override fun detachView() {
        mListTodo = null
        mMainView = null
    }
}