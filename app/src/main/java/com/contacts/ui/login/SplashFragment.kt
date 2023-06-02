package com.contacts.ui.login

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.contacts.R
import com.contacts.apis.ApiClient
import com.contacts.apis.Constant
import com.contacts.apis.ProviderInterface
import com.contacts.databinding.FragmentSplashBinding
import com.contacts.model.ResponseToken
import com.contacts.utills.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*


class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    lateinit var sharedPref: SharedPref
    lateinit var apiInterface: ProviderInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        sharedPref = SharedPref(requireContext())
        apiInterface = ApiClient.getClient()!!.create(ProviderInterface::class.java)
        return binding.root
    }

    override fun onResume() {
        sharedPref = SharedPref(requireContext())
       // setLocale(sharedPref.getStringValue(Constant.LANGUAGE))
        getToken()
        handlerMethod()
        super.onResume()
    }

    /*fun setLocale(lang: String?) {
        if (lang == "") {

            val myLocale = Locale("en")
            val res = resources
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, res.displayMetrics)
        } else {

            val myLocale = Locale(lang)
            val res = resources
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, res.displayMetrics)
        }

    }*/

    private fun handlerMethod() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (sharedPref.getBooleanValue(Constant.IS_LOGIN)) {
                Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_homeFragment)

            } else {
               // Navigation.findNavController(binding.root).navigate(R.id.action_splash_to_login_type)
                Navigation.findNavController(binding.root).navigate(R.id.action_splashFragment_to_homeFragment)

            }
        }, 3000)

    }
    private fun getToken() {
        apiInterface.getToken("Basic TW9iaWxlVGVzdDE6TW9iaWxlUGFzczE=")
            .enqueue(object : Callback<ResponseToken?> {
            override fun onResponse(call: Call<ResponseToken?>, response: Response<ResponseToken?>
            ) {
                try {
                    Timber.tag(ContentValues.TAG).e("success: %s",response.body().toString() )
                    sharedPref.setStringValue(Constant.AUTH_TOKEN,response.body()?.accessToken.toString())
                    Timber.tag(ContentValues.TAG).e("AUTH_TOKEN:---- %s",sharedPref.getStringValue(Constant.AUTH_TOKEN) )

                } catch (e: Exception) {
                } }
            override fun onFailure(call: Call<ResponseToken?>, t: Throwable) {
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
            }

        })

    }


}