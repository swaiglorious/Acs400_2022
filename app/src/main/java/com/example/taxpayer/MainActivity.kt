package com.example.taxpayer

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.taxpayer.data.RequestModel
import com.example.taxpayer.data.ResponseModel
import com.example.taxpayer.data.User
import com.example.taxpayer.data.VerifyOtpRequestModel
import com.example.taxpayer.repositories.UserRepository
import com.example.taxpayer.services.OtpService
import com.example.taxpayer.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

class MainActivity : AppCompatActivity() {
    private lateinit var pinId: String
    val userRepository = UserRepository()
    lateinit var codeEditText: EditText
    val username : String = "b1d2218977b5d109"
    val password : String = "OTFmMWViOGQ4MDQ2YmRhN2U3YzVlZDlmZmU0NjE3MTEwYWMxZWY5MjI1YWEzYmY5NTQ3ZGFlZjRmNDllMzE0Yg=="
    lateinit var myIntent: Intent
    var submitCode : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val nidaEditText : EditText = findViewById(R.id.edt_nida)
        codeEditText = findViewById(R.id.edt_code)
        codeEditText.visibility = View.GONE

        btnSubmit.setOnClickListener(){
            if(submitCode){
                val code = codeEditText.text.toString()
                verifyOTP(code)
            }else{
                val nida = nidaEditText.text.toString()
                myIntent.putExtra("EXTRA_NIDA",nida)
                verifyNida(nida)
            }
        }

        initializeUserRepoData()
        myIntent = Intent(this,DisplayDataActivity::class.java)




    }

    private fun initializeUserRepoData() {
        userRepository.addUser(User(1, "Baraka Elias", "127-168-650","12345678901234567890","27/11/2022","Dar es Salaam", "TEGETA", "Wema Street","255624327900"))
        userRepository.addUser(User(2, "Glorius Swai", "127-458-650","12345678901234567891","28/11/2022","Dar es Salaam", "MWENGE", "Sam Nujoma Street","254797795440"))
        userRepository.addUser(User(3, "Haidery Shango", "137-168-990","12345678901234567892","1/12/2022","Dar es Salaam", "KIGAMBONI", "Nawi Street","255624327900"))
        userRepository.addUser(User(4, "Baraka Elias", "127-168-650","12345678901234567893","27/11/2022","Dar es Salaam", "TEGETA", "Wema Street","255624327900"))
    }

    private fun verifyNida(nid:String) {
        val phoneNumber = userRepository.getUserPhoneWithNida(nid)

       val getOtpCall : OtpService = ServiceBuilder.buildService(OtpService::class.java)
        val req = RequestModel(phoneNumber)

        val auth = "Basic "+ Base64.getEncoder().encodeToString(("${username}:${password}").toByteArray())

        getOtpCall.getOtp(auth,req).enqueue(
            object: Callback<HashMap<String, HashMap<String,Any >>>{
                override fun onResponse(
                    call: Call<HashMap<String, HashMap<String, Any>>>,
                    response: Response<HashMap<String, HashMap<String, Any>>>
                ) {
                    Log.d("MAIN_ACTIVITY ON RESPONSE","IT WORKS")

                    val data = response.body()?.get("data")
                    pinId = data?.get("pinId").toString()
                    codeEditText.visibility = View.VISIBLE
                    submitCode = true
                }

                override fun onFailure(
                    call: Call<HashMap<String, HashMap<String, Any>>>,
                    t: Throwable
                ) {
                    Log.d("MAIN_ACTIVITY ON FAILURE", t.message.toString())
                }
            }
        )

    }

    private fun verifyOTP(cod:String){
        val verifyOtpCall : OtpService = ServiceBuilder.buildService(OtpService::class.java)
        val req = VerifyOtpRequestModel(pinId,cod)

        val auth = "Basic "+ Base64.getEncoder().encodeToString(("${username}:${password}").toByteArray())

        verifyOtpCall.verifyOtp(auth, req).enqueue(
            object: Callback<HashMap<String, HashMap<String, Any>>>{
                override fun onResponse(
                    call: Call<HashMap<String, HashMap<String, Any>>>,
                    response: Response<HashMap<String, HashMap<String, Any>>>
                ) {
                    Log.d("MAIN ACTIVITY VERIFY OTP ON RESPONSE","IT WORKS")
                    startActivity(myIntent)
                }

                override fun onFailure(
                    call: Call<HashMap<String, HashMap<String, Any>>>,
                    t: Throwable
                ) {
                    Log.d("MAIN ACTIVITY VERIFY OTP ON FAILURE","no work")
                }
            }
        )

    }
}
