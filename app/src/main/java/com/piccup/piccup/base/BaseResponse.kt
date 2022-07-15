package com.piccup.piccup.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @field:SerializedName("status")
    var status: Boolean? = null,

    @SerializedName("message")
    @Expose
    val message: String = "",

    @SerializedName("info")
    @Expose
    var data: T? = null,

    ) {
    val isSuccessResponse: Boolean
        get() {

            return if (status != null) {
                status!!
            } else {
                false
            }
        }

    val messageResponse: String
        get() {

            return if (message.isNotEmpty()) {
                message
            }  else {
                "Error"
            }
        }

    data class EmptyData(val message: String = "")


}

