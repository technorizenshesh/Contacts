package com.contacts

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.contacts.utills.CrashReportingTree
import com.contacts.utills.SharedPref
import timber.log.Timber

class appController: Application() {
    var manager: SharedPref? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree())
        manager = SharedPref(applicationContext)
        context = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //to disable dark mode
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        fun showToast(mContext: Context?, msg: String) { Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show() }
        fun get(): appController? { return get() }

    }
}