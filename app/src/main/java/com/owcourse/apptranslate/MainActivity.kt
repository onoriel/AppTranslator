package com.owcourse.apptranslate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.owcourse.apptranslate.API.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var btnDetectLanguage: Button
    private lateinit var etDescription: EditText

    var languages = emptyList<Language>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getLanguages()
    }

    private fun getLanguages() {
        CoroutineScope(Dispatchers.IO).launch {
            val languagesRes = retrofitService.getLanguages()
            if(languagesRes.isSuccessful){
                languages = languagesRes.body() ?: emptyList()
                showSuccess()
            }else{
                showError()
            }
        }


    }

    private fun showSuccess() {
        runOnUiThread {
            Toast.makeText(this, "Request Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showError() {
        runOnUiThread {
            Toast.makeText(this, "Error requesting data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        btnDetectLanguage = findViewById(R.id.btnDetectLanguage)
        etDescription = findViewById(R.id.etDescription)
    }
}
