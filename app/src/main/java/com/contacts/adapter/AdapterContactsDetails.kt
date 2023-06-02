package com.contacts.adapter

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.contacts.R
import com.contacts.apis.ApiClient
import com.contacts.apis.Constant
import com.contacts.apis.ProviderInterface
import com.contacts.databinding.ItemContactDetailsBinding
import com.contacts.model.TaskList
import com.contacts.utills.BaseAdapter
import com.contacts.utills.OnContactsListItemClickListener
import com.contacts.utills.SharedPref
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AdapterContactsDetails(
    private var newsList: ArrayList<JsonObject>?,
    private var context: Context,
    private val listener: OnContactsListItemClickListener
) : BaseAdapter() {
    lateinit var sharedPref: SharedPref
    private var mPageCount: Int = 1
    var arrayList = ArrayList<TaskList>()

    override fun getListSize(): Int {
        if (newsList == null) return 0
        return newsList?.size!!
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_contact_details
    }

    override fun onBindTo(rowBinding: ViewDataBinding, position: Int) {
        if (rowBinding is ItemContactDetailsBinding) {
            sharedPref = SharedPref(mContext)
            val source = newsList?.get(position)

            Log.e("TAG", "onBindTo: " + source.toString())
            if (source?.has("thumbnail") == true) {
                rowBinding.topMainLay.visibility = View.VISIBLE
                rowBinding.name.text = source.get("name").toString().replace('"', ' ').trim()
                rowBinding.type.text = source.get("type").toString().replace('"', ' ').trim()
                Glide.with(context)
                    .load(source.get("thumbnail").toString().replace('"', ' ').trim())
                    .into(rowBinding.managerImage)
                rowBinding.detailsBtnTop.setOnClickListener {
                    if (rowBinding.linesRecycle.isVisible) {
                        rowBinding.linesRecycle.visibility = View.GONE
                        rowBinding.detailsBtnTop.setImageDrawable(context.getDrawable(R.mipmap.btn_image_closed))
                    } else {
                        rowBinding.linesRecycle.visibility = View.VISIBLE
                        rowBinding.detailsBtnTop.setImageDrawable(context.getDrawable(R.mipmap.btn_image))
                    }

                }
                if (source.has("Lines")) {
                    try {
                        val keySet = source.getAsJsonArray("Lines")
                        val newsAdapter = BasicDetailsAdapter(keySet)
                        rowBinding.linesRecycle.adapter = newsAdapter
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }

            } else if (source?.has("tab") == true &&
                source.get("tab").toString().replace('"', ' ').trim() == "Details"
            ) {
                rowBinding.topMainLay.visibility = View.GONE
                rowBinding.tabName.text = source.get("tab").toString().replace('"', ' ').trim()
                Log.e("TAG", "onBindTo: notthumb")
                rowBinding.detailsTab.visibility = View.VISIBLE
                if (source.has("Lines")) {
                    try {
                        val keySet = source.getAsJsonArray("Lines")
                        val newsAdapter = BasicDetailsAdapter(keySet)
                        rowBinding.detailsRecycle.adapter = newsAdapter
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("TAG", "ExceptionException: " + e.message)
                    }

                }
                rowBinding.detailsBtnDetail.setOnClickListener {
                    if (rowBinding.detailsRecycle.isVisible) {
                        rowBinding.detailsRecycle.visibility = View.GONE
                        rowBinding.detailsBtnTop.setImageDrawable(context.getDrawable(R.drawable.down_arrow_details))
                    } else {
                        rowBinding.detailsRecycle.visibility = View.VISIBLE
                        rowBinding.detailsBtnTop.setImageDrawable(context.getDrawable(R.drawable.down_arrow_details))
                    }

                }

            } else if (source?.has("tab") == true &&
                source.get("tab").toString().replace('"', ' ').trim() == "Histories"
            ) {
                rowBinding.tabHistory.visibility = View.VISIBLE
                rowBinding.tabNameHistory.text =
                    source.get("tab").toString().replace('"', ' ').trim()
                rowBinding.hrtryRecycle.showShimmerAdapter()
                getTaskList(mContext, rowBinding)


            }

        }
    }

    private fun getTaskList(mContext: Context, rowBinding: ItemContactDetailsBinding) {
        val id = sharedPref.getStringValue("id").toString()
        val auth_token = sharedPref.getStringValue(Constant.AUTH_TOKEN).toString()
        val map = HashMap<String, String>()
        map["id"] = ""
        map["filter"] = ""
        map["contact_id"] = "1"
        map["sort_by"] = ""
        map["page"] = "1"
        map["resource_type"] = "task"
        map["access_token"] = auth_token
        Log.e("Login user Request = %s", map.toString())
        var apiInterface = ApiClient.getClient()!!.create(ProviderInterface::class.java)
        apiInterface.getTask(map).enqueue(object :
            Callback<ArrayList<TaskList>?> {
            override fun onResponse(
                call: Call<ArrayList<TaskList>?>,
                response: Response<ArrayList<TaskList>?>
            ) {
                try {
                    Log.e("TAG", "onResponse:------------- " + response.body().toString())

                    //     newsList = response.body()!!
                    if (arrayList == null || arrayList.size == 0) arrayList = response.body()!!
                    else arrayList.addAll(response.body()!!)
                    // newsAdapter.notifyAdapter(newsList)
                    arrayList = response.body()!!
                    Log.e("TAG", "showShimmerAdaptershowShimmerAdapter: " + arrayList.toString())
                    val newsAdapter = BasicDetailsTaskAdapter(arrayList)
                    rowBinding.hrtryRecycle.adapter = newsAdapter
                    rowBinding.hrtryRecycle.setBackgroundColor(Color.WHITE)
                    rowBinding.hrtryRecycle.hideShimmerAdapter()
                    rowBinding.layy.setOnScrollChangeListener(
                        NestedScrollView.OnScrollChangeListener
                        { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                                mPageCount++
                                getTaskList(mContext, rowBinding)
                            }
                        })


                } catch (e: Exception) {
                    Timber.tag("Exception").e("Exception = %s", e.message)
                }
            }

            override fun onFailure(call: Call<ArrayList<TaskList>?>, t: Throwable) {
                call.cancel()
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
            }
        })

    }
}
