package com.mguru.myapplication.ui.activity

import com.mguru.myapplication.data.model.Todo
import com.mguru.myapplication.ui.base.MvpView

interface MainView : MvpView {

    fun bindTodoList(todoList: List<Todo>)

    fun handleError(errorCode: Int)

}