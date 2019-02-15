package com.mguru.myapplication.ui.activity

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.mguru.myapplication.R
import com.mguru.myapplication.data.database.LocalDB
import com.mguru.myapplication.data.model.Todo
import com.mguru.myapplication.utils.SearchListData
import kotlinx.android.synthetic.main.item_recycler_view.view.*
import java.util.*

class TodoAdapter(private val mTodoList: List<Todo>,
                  private val listItemClickListener: ListItemClickListener) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(), Filterable {

    private var filteredData: List<Todo>? = null
    private val mFilter = ItemFilter()

    init {
        this.filteredData = mTodoList
    }

    private var mListItemClickListener: ListItemClickListener? = null

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        mListItemClickListener = listItemClickListener

        holder.bindItems(filteredData!![position])

        holder.itemView.setOnClickListener {
            mListItemClickListener!!.onItemClick(filteredData!![position])
        }

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return filteredData!!.size
    }

    //this class is holding the list view item
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(todo: Todo) {
            val title = "TITLE: ${todo.title}"
            itemView.titleTextView.text = title
            val id = "ID: ${todo.id}"
            itemView.idTextView.text = id
            val userId = "USER ID: ${todo.userId}"
            itemView.userIdTextView.text = userId
        }

    }

    interface ListItemClickListener {
        fun onItemClick(todo: Todo)
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    private inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {

            val filterString = constraint.toString().toLowerCase()

            val results = Filter.FilterResults()

            //search data by written custom logic
            val list = SearchListData.searchResult(filterString, mTodoList)
            //search data by realm query
            //val list = LocalDB.searchData(filterString)

            results.values = list

            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            filteredData = results.values as List<Todo>
            notifyDataSetChanged()
        }

    }

}