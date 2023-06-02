package com.contacts.adapter

import androidx.databinding.ViewDataBinding
import com.contacts.R
import com.contacts.databinding.ItemTaskListBinding
import com.contacts.model.ContactsList
import com.contacts.model.TaskList
import com.contacts.utills.BaseAdapter

class BasicDetailsTaskAdapter(private var newsList: ArrayList<TaskList>) : BaseAdapter() {
    override fun getListSize(): Int {
        return newsList.size
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_task_list
    }
    fun notifyAdapter(newsList: ArrayList<TaskList>) {
        this@BasicDetailsTaskAdapter.newsList = newsList
        notifyDataSetChanged()
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemTaskListBinding) {
            val source = newsList.get(position)
            rowBinding.name1.text = source.line1
            rowBinding.type1.text = source.line2
            rowBinding.year.text = source.date.replace(" ", "\n") + "\n" + source.text


        }


    }
}