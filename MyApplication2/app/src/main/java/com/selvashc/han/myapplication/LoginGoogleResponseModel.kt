package com.selvashc.han.myapplication

import com.google.gson.annotations.SerializedName

data class LoginGoogleResponseModel(
    @SerializedName("access_token")
    var accessToken: String
)