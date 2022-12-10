package com.example.taxpayer.services

import com.example.taxpayer.data.requests.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

public interface TraService {

    @POST("verify-nida")
    fun verifyNida(@Body nida_number : NidaRequestVerification) : Call<NidaRequestVerificationResponse>

    @POST("verify-phone")
    fun verifyPhone(@Body data : NidaVerifyPhoneRequest) : Call<NidaVerifyPhoneResponse>

    @POST("my-info")
    fun getTraInfo(@Body id: DetailsRequest) : Call<DetailsResponse>
}