package com.contacts.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ContactsDetails(
    var lines: JsonObject
) : java.io.Serializable {

}

