package com.contacts.adapter

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import com.contacts.R
import com.contacts.databinding.ItemSingleDetailsBinding
import com.contacts.databinding.ItemTxtDetailsBinding
import com.contacts.utills.BaseAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class BasicDetailsInsideAdapter(private var newsList: JsonArray) : BaseAdapter() {
    override fun getListSize(): Int {
        return newsList.size()
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_single_details
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemSingleDetailsBinding) {
            val source: JsonObject = newsList.get(position) as JsonObject
            for (key in source.keySet()) {
                Log.e("TAG", "ItemSingleDetailsBindingItemSingleDetailsBinding: "+source.get(key).toString().replace('"', ' ').trim() )
                if (key.toString().replace('"', ' ').trim().equals("Type")) {
                    Log.e("TAG", "ItemSingleDetailsBindingItemSingleDetailsBinding: "+key.toString().replace('"', ' ').trim() )
                    Log.e("TAG", "ItemSingleDetailsBindingItemSingleDetailsBinding: "+source.get(key).toString().replace('"', ' ').trim() )
                    /*if (source.get(key).toString().replace('"', ' ').trim() == "Phone") {
                        rowBinding.phoneLay.visibility = View.VISIBLE
                    }*/

                } else {
                        rowBinding.data.text = source.get(key).toString().replace('"', ' ').trim()
                    }
                }
            }


    }
}