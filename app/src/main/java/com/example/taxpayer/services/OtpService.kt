package com.example.taxpayer.services

import com.example.taxpayer.data.*
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.*

public interface OtpService {

    @POST("request")
    fun getOtp(
        @Header("Authorization") authorization:String,
        @Body requestModel: RequestModel) : Call<HashMap<String, HashMap<String, Any>>>

    @POST("verify")
    fun verifyOtp(
        @Header("Authorization") authorization: String,
        @Body verifyModel : VerifyOtpRequestModel) : Call<HashMap<String, HashMap<String, Any>>>
}