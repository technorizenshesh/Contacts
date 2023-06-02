package com.contacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseToken (
    @Expose
    @SerializedName("access_token")
    val accessToken: String,
    @Expose
    @SerializedName("expires_in")
    var expiresIn: Int,
    @Expose
    @SerializedName("token_type")
    val tokenType: String,
    @Expose
    @SerializedName("scope")
    var scope: Int
)

