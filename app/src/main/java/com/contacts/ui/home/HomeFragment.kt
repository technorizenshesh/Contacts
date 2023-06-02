package com.contacts.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.contacts.R
import com.contacts.adapter.AdapterContactsList
import com.contacts.apis.ApiClient
import com.contacts.apis.Constant
import com.contacts.apis.ProviderInterface
import com.contacts.databinding.FragmentHomeBinding
import com.contacts.model.ContactsList
import com.contacts.utills.DataManager
import com.contacts.utills.OnContactsListItemClickListener
import com.contacts.utills.SharedPref
import com.contacts.utills.errorSnack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class HomeFragment : Fragment(), OnContactsListItemClickListener {
    lateinit var binding: FragmentHomeBinding
    lateinit var sharedPref: SharedPref
    lateinit var apiInterface: ProviderInterface
    private var newsList: ArrayList<ContactsList>? = null
    private lateinit var newsAdapter: AdapterContactsList
    private var mPageCount: Int = 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        sharedPref = SharedPref(requireContext())
        apiInterface = ApiClient.getClient()!!.create(ProviderInterface::class.java)
        getHomeList()
        setAdapter()
        return binding.root
    }

    private fun setAdapter() {
        newsAdapter = AdapterContactsList(requireActivity(), newsList, this@HomeFragment)
        binding.managerList.layoutManager = LinearLayoutManager(activity)
        binding.layy.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener
        { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                mPageCount++
                getHomeList()
            }
        })
        binding.managerList.adapter = newsAdapter
    }

    private fun getHomeList() {
        DataManager.instance.showProgressMessage(requireActivity(), "Loading...")
        val userid = sharedPref.getStringValue(Constant.USER_ID).toString()
        val auth_token = sharedPref.getStringValue(Constant.AUTH_TOKEN).toString()
        val map = HashMap<String, String>()
        map["id"] = ""
        map["filter"] = ""
        map["contact_id"] = ""
        map["sort_by"] = ""
        map["page"] = mPageCount.toString()
        map["resource_type"] = "contact"
        map["access_token"] = auth_token
        Log.e("Login user Request = %s", map.toString())
        apiInterface.get_contacts_list(map).enqueue(object : Callback<ArrayList<ContactsList>?> {
            override fun onResponse(
                call: Call<ArrayList<ContactsList>?>,
                response: Response<ArrayList<ContactsList>?>
            ) {
                try {
                    Log.e("TAG", "onResponse:------------- "+response.body().toString() )

                    if (newsList == null || newsList!!.size == 0) newsList = response.body()!!
                    else newsList?.addAll(response.body()!!)
                    newsAdapter.notifyAdapter(newsList!!)
                    if (newsList == null || newsList?.size == 0)
                        binding.noData.visibility = View.VISIBLE
                    else binding.noData.visibility = View.GONE
                    DataManager.instance.hideProgressMessage()
                } catch (e: Exception) {
                    Timber.tag("Exception").e("Exception = %s", e.message)
                    DataManager.instance.hideProgressMessage()
                    binding.root.errorSnack("No More Data Available ")
                }
            }
            override fun onFailure(call: Call<ArrayList<ContactsList>?>, t: Throwable) {
                call.cancel()
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
                binding.root.errorSnack(t.message.toString())
                binding.noData.visibility = View.VISIBLE
                DataManager.instance.hideProgressMessage()
            }
        })
    }

    override fun onItemClick(model: ContactsList, int: Int) {
         var bundle = Bundle()
         bundle.putSerializable("id",model.id)
        Navigation.findNavController(binding.root)
            .navigate(R.id.action_homeFragment_to_contactDetailsFragment,bundle)
    }


}
