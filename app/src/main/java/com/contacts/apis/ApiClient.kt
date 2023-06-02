package com.contacts.apis
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
class ApiClient {
    companion object {
        var BASE_URL: String = "https://api.mybasiccrm.com/"
        val cacheSize = (50 * 1024 * 1024).toLong()
        private var retrofit: Retrofit? = null
        private val httpClient = OkHttpClient.Builder()
       val interceptor = HttpLoggingInterceptor()
        fun getClient(): Retrofit? {
             interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = httpClient
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    }
}