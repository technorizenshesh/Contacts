package com.contacts.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaskList (
        @SerializedName("date")
        val date: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("line_1")
        val line1: String,
        @SerializedName("line_2")
        val line2: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("text")
        val text: String
    )
