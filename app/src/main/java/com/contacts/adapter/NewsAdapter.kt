package com.contacts.adapter

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.contacts.R
import com.contacts.databinding.ItemContactBinding
import com.contacts.databinding.ItemTxtBinding
import com.contacts.model.ContactsList
import com.contacts.model.DynamicData
import com.contacts.utills.BaseAdapter
import com.contacts.utills.showImage

class NewsAdapter(private var newsList: ArrayList<DynamicData>?) : BaseAdapter() {
    override fun getListSize(): Int {
        if (newsList == null) return 0
        return newsList?.size!!
    }
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_txt
    }
    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemTxtBinding) {
            val source = newsList?.get(position)
            rowBinding.date3.text = source?.title+" :"
            rowBinding.date.text = source?.data?.replace('"',' ')
if(position==getListSize()-1){
   rowBinding.view.visibility=View.GONE
}
        }
    }
}