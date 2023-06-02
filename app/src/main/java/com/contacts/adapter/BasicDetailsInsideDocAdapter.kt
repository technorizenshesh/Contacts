package com.contacts.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.contacts.R
import com.contacts.databinding.ItemSingleDetailsBinding
import com.contacts.databinding.ItemSingleDetailsDocBinding
import com.contacts.databinding.ItemSingleDetailsImageBinding
import com.contacts.databinding.ItemTxtDetailsBinding
import com.contacts.utills.BaseAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class BasicDetailsInsideDocAdapter(private var newsList: JsonArray) : BaseAdapter() {
    override fun getListSize(): Int {
        return newsList.size()
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_single_details_doc
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemSingleDetailsDocBinding) {
            val source: JsonObject = newsList.get(position) as JsonObject
            for (key in source.keySet()) {
                Log.e("TAG", "ItemSingleDetailsImageBindingItemSingleDetailsImageBinding: "+source.get(key).toString().replace('"', ' ').trim() )
                      //  rowBinding.data.text = source.get(key).toString().replace('"', ' ').trim()
               /* with(Glide.with(mContext)) {
                    load(source.get(key).toString().replace('"', ' ').trim()).into(rowBinding.image)
                }*/

            rowBinding.image.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(source.get(key).toString().replace('"', ' ').trim()))
                mContext.startActivity(browserIntent)
            }
            }
        }



    }
}