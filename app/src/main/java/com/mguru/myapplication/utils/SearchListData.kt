package com.mguru.myapplication.utils

import com.mguru.myapplication.data.model.Todo
import java.util.*

object SearchListData {

    /**
     * Generate search data
     * @param searchQuery -> search text which is given input by user
     * @param actualList -> it is a original data
     * @return -> array list of search result
     */
    fun searchResult(searchQuery: String, actualList: List<Todo>): ArrayList<Todo> {
        val newList = ArrayList<Todo>()

        actualList.forEach {

            searchQuery.toLowerCase()

            val tempTitle = it.title!!.toLowerCase()
            val tempId = it.id
            val tempUserId = it.userId

            if (tempTitle.contains(searchQuery) ||
                    tempId.toString().contains(searchQuery) ||
                    tempUserId.toString().contains(searchQuery))
                newList.add(it)
        }

        return newList
    }
}