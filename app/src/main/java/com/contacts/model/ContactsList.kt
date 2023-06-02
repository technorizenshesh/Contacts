package com.contacts.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ContactsList(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("Lines") var lines: ArrayList<JsonObject> = arrayListOf()
) : java.io.Serializable {
    data class Lines(
        @SerializedName("Created date") var Createddate: String? = null,
        @SerializedName("PropertyID") var PropertyID: String? = null,
        @SerializedName("Property Address") var PropertyAddress: String? = null,
        @SerializedName("Açıklama") var Açıklama: String? = null,
        @SerializedName("Bedroom") var Bedroom: String? = null


    ) : java.io.Serializable
}

