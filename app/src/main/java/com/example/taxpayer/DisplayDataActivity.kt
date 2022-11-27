package com.example.taxpayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.taxpayer.databinding.ActivityDisplayDataBinding
import com.example.taxpayer.repositories.UserRepository
import org.w3c.dom.Text

class DisplayDataActivity : AppCompatActivity() {
    val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        val binding = ActivityDisplayDataBinding.inflate(layoutInflater)
        val nida : String = intent.getStringExtra("EXTRA_NIDA").toString()
        val user = userRepository.getUserWithNida(nida)

        Log.d("DISPLAY_DATA_ACTIVITY",user.toString())

        findViewById<TextView>(R.id.txt_name).setText("Name: ${user.name}")
        findViewById<TextView>(R.id.txt_nida).setText("NIDA: ${user.nida}")
        findViewById<TextView>(R.id.txt_tin).setText("TIN: ${user.tin}")
        findViewById<TextView>(R.id.txt_issueDate).setText("Issue date: ${user.issueDate}")
        findViewById<TextView>(R.id.txt_physicalLocation).setText("Physical location: ${user.physicalLocation}")
        findViewById<TextView>(R.id.txt_street).setText("Street: ${user.street}")
        findViewById<TextView>(R.id.txt_taxOffice).setText("Tax Office: ${user.taxOffice}")
        findViewById<TextView>(R.id.txt_phoneNumber).setText("Phone number: ${user.phoneNumber}")
    }
}