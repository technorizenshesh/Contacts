package com.contacts.apis

import com.contacts.model.ContactsList
import com.contacts.model.ResponseHome
import com.contacts.model.ResponseToken
import com.contacts.model.TaskList
import com.google.gson.JsonObject
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.http.*
interface ProviderInterface {
    // Create credentials

    @POST("jwt.php")
    fun getToken(@Header("Authorization") credentials: String): Call<ResponseToken>

    @GET("resource.php?")
    fun get_contacts_list(@QueryMap params: Map<String, String>): Call<ArrayList<ContactsList>>
    @GET("resource.php?")
    fun get_contacts_details(@QueryMap params: Map<String, String>): Call<ArrayList<JsonObject>>
    @GET("resource.php?")
    fun getTask(@QueryMap params: Map<String, String>): Call<ArrayList<TaskList>>
/*https://api.mybasiccrm.com/resource.php?access_token=ey&resource_type=contact&id&contact_id&page=2&sort_by&filter*/
}