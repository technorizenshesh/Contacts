package com.contacts.adapter

import androidx.databinding.ViewDataBinding
import com.contacts.R
import com.contacts.databinding.ItemContactBinding
import com.contacts.databinding.ItemTxtBinding
import com.contacts.databinding.ItemTxtTwoBinding
import com.contacts.model.ContactsList
import com.contacts.utills.BaseAdapter
import com.contacts.utills.showImage

class NewsAdapter2(private var newsList: ArrayList<String>?) : BaseAdapter() {
    override fun getListSize(): Int {
        if (newsList == null) return 0
        return newsList?.size!!
    }
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_txt_two
    }
    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemTxtTwoBinding) {
            val source = newsList?.get(position)
            if (source != null) {

                if (source.contains('"')) rowBinding.date3.text=(source.replace('"',' ',true)).trim()
                  else  rowBinding.date3.text = source
            }
        }
    }
}