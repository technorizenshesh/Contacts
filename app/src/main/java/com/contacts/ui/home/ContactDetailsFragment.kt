package com.contacts.ui.home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.contacts.R
import com.contacts.adapter.AdapterContactsDetails
import com.contacts.adapter.AdapterContactsList
import com.contacts.apis.ApiClient
import com.contacts.apis.Constant
import com.contacts.apis.ProviderInterface
import com.contacts.databinding.FragmentContactDetailsBinding
import com.contacts.model.ContactsList
import com.contacts.model.ResponseHome
import com.contacts.utills.DataManager
import com.contacts.utills.OnContactsListItemClickListener
import com.contacts.utills.SharedPref
import com.contacts.utills.errorSnack
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException


class ContactDetailsFragment : Fragment(), OnContactsListItemClickListener {
    lateinit var binding: FragmentContactDetailsBinding
    lateinit var sharedPref: SharedPref
    lateinit var apiInterface: ProviderInterface
    private var newsList: ArrayList<JsonObject>? = null
    private lateinit var newsAdapter: AdapterContactsDetails
    private var mPageCount: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_details, container,
            false
        )
        sharedPref = SharedPref(requireContext())
        apiInterface = ApiClient.getClient()!!.create(ProviderInterface::class.java)

         if (arguments!=null){
             val id = requireArguments().getString("id")
             sharedPref.setStringValue("id",id.toString());
             setAdapter()
             getDetailsdata(id.toString())
             Log.e("TAG", "onCreateView:ididididid---- "+id )
         }

        return binding.root

    }


    private fun setAdapter() {

    }


    private fun getDetailsdata(id:String ) {
        binding.managerList.showShimmerAdapter()
      //  DataManager.instance.showProgressMessage(requireActivity(), "Loading...")
        val userid = sharedPref.getStringValue(Constant.USER_ID).toString()
        val auth_token = sharedPref.getStringValue(Constant.AUTH_TOKEN).toString()
        val map = HashMap<String, String>()
        map["id"] = id
        map["filter"] = ""
        map["contact_id"] = ""
        map["sort_by"] = ""
        map["page"] = mPageCount.toString()
        map["resource_type"] = "contact"
        map["access_token"] = auth_token
        Log.e("Login user Request = %s", map.toString())
        apiInterface.get_contacts_details(map).enqueue(object : Callback<ArrayList<JsonObject>?> {
            override fun onResponse(
                call: Call<ArrayList<JsonObject>?>,
                response: Response<ArrayList<JsonObject>?>
            ) {
                try {
                    Log.e("TAG", "onResponse:------------- "+response.body().toString() )

                    newsList = response.body()!!
                    newsAdapter = AdapterContactsDetails(newsList,requireActivity(),
                        this@ContactDetailsFragment)
                    binding.managerList.layoutManager = LinearLayoutManager(activity)
                    binding.managerList.adapter = newsAdapter
                     binding.noData.visibility = View.GONE
                    binding.managerList.hideShimmerAdapter()

                   // DataManager.instance.hideProgressMessage()
                } catch (e: Exception) {
                    Timber.tag("Exception").e("Exception = %s", e.message)
                   // DataManager.instance.hideProgressMessage()
                    binding.managerList.hideShimmerAdapter()

                    binding.root.errorSnack("No More Data Available ")
                }
            }
            override fun onFailure(call: Call<ArrayList<JsonObject>?>, t: Throwable) {
                call.cancel()
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
                binding.root.errorSnack(t.message.toString())
                binding.noData.visibility = View.VISIBLE
                binding.managerList.hideShimmerAdapter()

                //  DataManager.instance.hideProgressMessage()
            }
        })
    }



    override fun onItemClick(model: ContactsList, int: Int) {
    }


}