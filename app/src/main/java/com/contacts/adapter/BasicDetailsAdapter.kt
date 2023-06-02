package com.contacts.adapter

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.contacts.R
import com.contacts.databinding.ItemTxtDetailsBinding
import com.contacts.utills.BaseAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class BasicDetailsAdapter(private var newsList: JsonArray) : BaseAdapter() {
    override fun getListSize(): Int {
        return newsList.size()
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_txt_details
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemTxtDetailsBinding) {
            val source: JsonObject = newsList.get(position) as JsonObject
            for (key in source.keySet()) {
                if (key.toString().replace('"', ' ').trim().equals("Type")) {
                    if (source.get(key).toString().replace('"', ' ').trim() == "Phone") {
                        rowBinding.phoneLay.visibility = View.VISIBLE
                    }

                } else {
                    if (source.get(key).isJsonArray) {
                        Log.e("TAG", "isJsonArrayisJsonArrayisJsonArray: "+key.toString().replace('"', ' ').trim() )
                        if (key.toString().replace('"', ' ').trim() == "Source") {
                            rowBinding.simpalLay.visibility = View.GONE
                            rowBinding.innerRecycle.visibility = View.VISIBLE
                            var array = source.getAsJsonArray(key)
                            rowBinding.date3.text = key.toString().replace('"', ' ').trim()
                            val newsAdapter = BasicDetailsInsideAdapter(array)
                            rowBinding.innerRecycle.layoutManager = LinearLayoutManager(mContext)
                            rowBinding.innerRecycle.adapter = newsAdapter

                            for (keys in array) {
                            }

                            Log.e("TAG", "arrayarrayarrayarrayarray: " + array.toString())

                        }else if (source.get("Type").toString().replace('"', ' ').trim() == "Image") {
                            if (source.get(key).isJsonArray) {
                                rowBinding.simpalLay.visibility= View.GONE
                                rowBinding.innerRecycle.visibility= View.VISIBLE
                                var array = source.getAsJsonArray(key)
                                rowBinding.date3.text = "Image"
                                val newsAdapter = BasicDetailsInsideImageAdapter(array)
                                rowBinding.innerRecycle.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
                                rowBinding.innerRecycle.adapter = newsAdapter
                                Log.e("TAG", "BasicDetailsInsideImageAdapter: "+array.toString())

                            }
                        } else if (source.get("Type").toString().replace('"', ' ').trim() == "Document") {
                            if (source.get(key).isJsonArray) {
                                rowBinding.simpalLay.visibility= View.GONE
                                rowBinding.innerRecycle.visibility= View.VISIBLE
                                var array = source.getAsJsonArray(key)
                                rowBinding.date3.text = "Document"
                                val newsAdapter = BasicDetailsInsideDocAdapter(array)
                                rowBinding.innerRecycle.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
                                rowBinding.innerRecycle.adapter = newsAdapter
                                Log.e("TAG", "BasicDetailsInsideImageAdapter: "+array.toString())

                            }
                        }


                        //   if (key.toString().)


                    } else {
                        rowBinding.date3.text = key.toString().replace('"', ' ').trim()
                        rowBinding.data.text = source.get(key).toString().replace('"', ' ').trim()
                    }
                }
            }
            if (position == getListSize() - 1) {
                rowBinding.view.visibility = View.GONE
            }
        }
    }
}