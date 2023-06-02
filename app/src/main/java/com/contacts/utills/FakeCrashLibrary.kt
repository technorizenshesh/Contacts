package com.contacts.utills

import android.util.Log
import timber.log.Timber

class FakeCrashLibrary private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
       fun log(priority: Int, tag: String, message: String) {
           Timber.tag("TAG").e("log: %s", priority)
           Timber.tag("TAG").e("log: %s", tag)
           Timber.tag("TAG").e("log: %s", message)
       }

        fun logWarning(t: Throwable) {
            Timber.tag("TAG").e("log: %s",t.message )

        }

        fun logError(t: Throwable) {
            Timber.tag("TAG").e("log: %s",t.message )

        }
    }
}