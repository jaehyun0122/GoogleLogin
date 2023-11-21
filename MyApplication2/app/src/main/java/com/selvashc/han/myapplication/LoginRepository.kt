package com.selvashc.han.myapplication

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {

    private val getAccessTokenBaseUrl = "https://www.googleapis.com"
    private lateinit var mContext: Context

    constructor(context: Context){
        this.mContext = context
    }


    fun getAccessToken(authCode: String) {
        LoginService.loginRetrofit(getAccessTokenBaseUrl).getAccessToken(
            request = LoginGoogleRequestModel(
                grant_type = "authorization_code",
                client_id = mContext.getString(R.string.client_id),
                client_secret = mContext.getString(R.string.client_secret),
                code = authCode
            )
        ).enqueue(object : Callback<LoginGoogleResponseModel>{
            override fun onResponse(call: Call<LoginGoogleResponseModel>, response: Response<LoginGoogleResponseModel>) {
                val accessToken = response.body()?.accessToken.orEmpty()

                Log.d("jay", "access Token $accessToken")

            }

            override fun onFailure(call: Call<LoginGoogleResponseModel>, t: Throwable) {
                Log.d("jay", "accessToken fail")
            }
        })

    }


    companion object {
        const val TAG = "LoginRepository"
    }

}
