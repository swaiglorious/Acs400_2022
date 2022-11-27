package com.example.taxpayer.services

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BasicAuthInterceptor(username: String, password: String): Interceptor {
    private var credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()
        return chain.proceed(request)
    }
}
object ServiceBuilder {

    val logger : HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(logger)
            .build()


        val retrofit: Retrofit = retrofit2.Retrofit.Builder()
            .baseUrl("https://apiotp.beem.africa/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        fun <T> buildService(service: Class<T>): T {
            return retrofit.create(service)
        }


}
//class ServiceBuilder {
//    companion object{
//        val URL : String = "https://apiotp.beem.africa/v1"
//        val builder : Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
//            .addConverterFactory(GsonConverterFactory.create())
//        val retrofit : Retrofit = builder.build()
//    }
//}