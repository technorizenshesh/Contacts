package com.contacts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.contacts.R
import com.contacts.databinding.ItemContactBinding
import com.contacts.model.ContactsList
import com.contacts.model.DynamicData
import com.contacts.utills.OnContactsListItemClickListener
import com.contacts.utills.SharedPref
import com.google.gson.JsonObject


class AdapterContactsList(
    val mContext: Context,
    var transList: ArrayList<ContactsList>?, private val listener: OnContactsListItemClickListener
) : RecyclerView.Adapter<AdapterContactsList.TransViewHolder>() {

    lateinit var sharedPref: SharedPref
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransViewHolder {
        val binding: ItemContactBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext), R.layout.item_contact, parent, false
        )
        sharedPref = SharedPref(mContext)
        return TransViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TransViewHolder, position: Int) {
        val data: ContactsList = transList?.get(position)!!
        var show = false
        if (position == 0) {
            holder.binding.first.setBackgroundColor(Color.WHITE)
        }
        holder.binding.name.text = data.name
        holder.binding.type.text = data.type
        holder.binding.root.setOnClickListener {
            listener.onItemClick(data, position)
        }
        try {
            var dynamicData: ArrayList<DynamicData> = ArrayList()
            val keySet = data.lines[0].keySet()
            val keyList = ArrayList(keySet)
            for (key in keyList) {
                dynamicData.add(DynamicData(key, data.lines[0].get(key).toString()))
            }
            holder.binding.date.text = (dynamicData[0].data).replace('"', ' ', true).trim()
            holder.binding.date1.text = (dynamicData[0].title).trim() + ":"
            dynamicData.removeAt(0)
            val newsAdapter = NewsAdapter(dynamicData)
            holder.binding.keyList.adapter = newsAdapter
        } catch (e: Exception) {
            e.printStackTrace()
        }


//        try {
//
//             if (data.lines[0].has("Date")){
//
//                 val source = data.lines[0].get("Date").toString()
//                 if (source.contains('"')) holder.binding.date.text =
//                     (source.replace('"', ' ', true)).trim()
//                 else holder.binding.date.text = source
//             }else {
//
//                 val source = data.lines[0].get("Created date").toString()
//                 if (source.contains('"')) holder.binding.date.text =
//                     (source.replace('"', ' ', true)).trim()
//                 else holder.binding.date.text = source
//             }
//        } catch (e: Exception) {
//
//        }
        holder.binding.detailsBtn.setOnClickListener {
           // if (!holder.binding.detailsLay.isVisible) {
                holder.binding.detailsBtn.visibility = View.GONE
                holder.binding.detailsLay.visibility = View.VISIBLE
               // holder.binding.detailsBtn.setImageDrawable(mContext.getDrawable(R.drawable.up_side))
         //   } else {

              //  holder.binding.detailsBtn.setImageDrawable(mContext.getDrawable(R.drawable.down_side))

          //  }
        }
        holder.binding.detailsBtnClose.setOnClickListener {
            holder.binding.detailsBtn.visibility = View.VISIBLE
            holder.binding.detailsLay.visibility = View.GONE
        }
    }

    private fun getvalues(keyList: ArrayList<String>, JsonObject: JsonObject): ArrayList<String> {
        var list: ArrayList<String> = ArrayList()
        for (key in keyList) {
            if (key.contains("Date", true)) {
            } else {
                list.add(JsonObject.get(key).toString())
            }
        }
        return list
    }


    override fun getItemCount(): Int {
        if (transList == null) return 0
        return transList?.size!!
    }

    fun notifyAdapter(newsList: ArrayList<ContactsList>) {
        transList = newsList
        notifyDataSetChanged()
    }

    class TransViewHolder(var binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root)

}