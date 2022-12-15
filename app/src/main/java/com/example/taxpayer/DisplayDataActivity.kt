package com.example.taxpayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.taxpayer.data.requests.DetailsRequest
import com.example.taxpayer.data.requests.DetailsResponse
import com.example.taxpayer.repositories.UserRepository
import com.example.taxpayer.services.ServiceBuilder
import com.example.taxpayer.services.TraService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        val nida : Int = intent.getIntExtra("NIDA_ID",0)

        //get the details
        val getDetailsCall : TraService = ServiceBuilder.buildService(TraService::class.java)
        getDetailsCall.getTraInfo(DetailsRequest(nida)).enqueue(
            object: Callback<DetailsResponse>{
                override fun onResponse(call: Call<DetailsResponse>, response: Response<DetailsResponse>) {
                    Log.d("DISPLAY_ACTIVITY success",response.body().toString())

                    if(response.body()?.tra_account === null){
                        findViewById<LinearLayout>(R.id.dataLayout).visibility = View.GONE
                        findViewById<TextView>(R.id.tvw_no_record).visibility = View.VISIBLE
                    }

                    val name :String = response.body()?.user?.get("first_name").toString() + response.body()?.user?.get("last_name")
                    val nida : String = response.body()?.user?.get("nida_number").toString()

                    val tin : String = response.body()?.tra_account?.get("tin_number").toString()
                    Log.d("DISPLAY_ACTIVITY TIN",tin)
                    val tin_created : String = response.body()?.tra_account?.get("created_at").toString()
                    val phone_number : String = response.body()?.user?.get("phone_number").toString()
                    Log.d("DISPLAY ACTIVITY FULL response object",response.body().toString())
                    Log.d("DISPLAY ACTIVITY FULL business: ", response.body()?.brela_account.toString())

                    val business_name : String = response.body()?.brela_account?.get("business_name").toString()
                    Log.d("DISPLAY_ACTIVITY business name",business_name)

                    val director_name : String = response.body()?.brela_account?.get("director_name").toString()

                    findViewById<TextView>(R.id.txt_name).setText("Name: ${name}")
                    findViewById<TextView>(R.id.txt_nida).setText("NIDA: ${nida}")
                    findViewById<TextView>(R.id.txt_tin).setText("TIN: ${tin}")
                    findViewById<TextView>(R.id.txt_issueDate).setText("Issue date: ${tin_created}")
                    findViewById<TextView>(R.id.txt_phoneNumber).setText("Phone number: ${phone_number}")

                    findViewById<TextView>(R.id.tvw_business_name).setText("Business name: ${business_name}")
                    findViewById<TextView>(R.id.tvw_director_name).setText("Director's name: ${director_name}")







                }

                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                    Log.d("DISPLAY_ACTIVITY failed",t.message.toString())
                }

            }
        )
        Log.d("DISPLAY_ACTIVITY NIDA: ",nida.toString())
//        val user = userRepository.getUserWithNida(nida)

//        Log.d("DISPLAY_DATA_ACTIVITY",user.toString())
//
//        findViewById<TextView>(R.id.txt_name).setText("Name: ${user.name}")
//        findViewById<TextView>(R.id.txt_nida).setText("NIDA: ${user.nida}")
//        findViewById<TextView>(R.id.txt_tin).setText("TIN: ${user.tin}")
//        findViewById<TextView>(R.id.txt_issueDate).setText("Issue date: ${user.issueDate}")
//        findViewById<TextView>(R.id.txt_physicalLocation).setText("Physical location: ${user.physicalLocation}")
//        findViewById<TextView>(R.id.txt_street).setText("Street: ${user.street}")
//        findViewById<TextView>(R.id.txt_taxOffice).setText("Tax Office: ${user.taxOffice}")
//        findViewById<TextView>(R.id.txt_phoneNumber).setText("Phone number: ${user.phoneNumber}")
    }
}