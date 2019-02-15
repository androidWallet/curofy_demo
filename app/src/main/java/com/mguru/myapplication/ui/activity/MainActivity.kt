package com.mguru.myapplication.ui.activity

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.mguru.myapplication.R
import com.mguru.myapplication.data.model.Todo
import com.mguru.myapplication.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), MainView, TodoAdapter.ListItemClickListener {

    private var mMainPresenter: MainPresenter? = null
    private var mTodoAdapter: TodoAdapter? = null
    private var mTodoList = ArrayList<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //initial setup
        initialSetup()

        //make network call to get list of data
        mMainPresenter!!.todoList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)

        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.enter_search_text)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                mTodoAdapter!!.filter.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })

        return true
    }

    //Bind the recyclerVew
    override fun bindTodoList(todoList: List<Todo>) {
        mTodoList.addAll(todoList)
        mTodoAdapter!!.notifyDataSetChanged()
    }

    override fun handleError(errorCode: Int) {
        when (errorCode) {
            100 -> Toast.makeText(applicationContext, resources.getString(R.string.network_connection_error), Toast.LENGTH_SHORT).show()
            101 -> Toast.makeText(applicationContext, resources.getString(R.string.no_data_available), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(applicationContext, resources.getString(R.string.try_again), Toast.LENGTH_SHORT).show()
        }

    }

    override fun initialSetup() {
        //create a vertical Layout Manager
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1)

        //initiate main presenter
        mMainPresenter = MainPresenterImpl(this)

        //set adapter to recycler view
        mTodoAdapter = TodoAdapter(mTodoList, this@MainActivity)
        recyclerView.adapter = mTodoAdapter

    }

    override fun onItemClick(todo: Todo) {
        Toast.makeText(this, todo.title, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        mMainPresenter!!.detachView()
        mMainPresenter = null
        super.onDestroy()
    }

}
