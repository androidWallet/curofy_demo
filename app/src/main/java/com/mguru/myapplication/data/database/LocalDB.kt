package com.mguru.myapplication.data.database

import com.mguru.myapplication.data.model.Todo
import io.realm.Case
import io.realm.Realm


object LocalDB {

    /**
     * save to do list in local DB
     * @param todoList -> to do list data
     */
    fun saveToDoList(todoList: List<Todo>) {
        val mRealm = Realm.getDefaultInstance()
        mRealm.executeTransaction { realm ->
            try {
                todoList.forEach {
                    val createObj = realm.createObject(Todo::class.java, it.id)
                    createObj.userId = it.userId
                    createObj.title = it.title
                    createObj.completed = it.completed
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } finally {
                mRealm.close()
            }
        }

    }

    /**
     * get to do list in from local DB
     * @return -> array list of to do data
     */
    fun getToDoListFromLocal(): ArrayList<Todo> {
        val mRealm = Realm.getDefaultInstance()
        val list = ArrayList<Todo>()
        try {
            val results = mRealm.where(Todo::class.java).findAll()
            list.addAll(mRealm.copyFromRealm(results))
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        return list
    }

    /**
     * Generate search data
     * @param queryString -> search text which is given input by user
     * @return -> array list of search result
     */
    fun searchData(queryString: String): ArrayList<Todo> {
        val mRealm = Realm.getDefaultInstance()
        val list = ArrayList<Todo>()
        list.clear()
        try {
            val results = mRealm.where(Todo::class.java).contains("title", queryString, Case.INSENSITIVE).findAll()
            list.addAll(mRealm.copyFromRealm(results))
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } finally {
            mRealm.close()
        }
        return list
    }

}