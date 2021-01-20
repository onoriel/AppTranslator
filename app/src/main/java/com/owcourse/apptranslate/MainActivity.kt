package com.owcourse.apptranslate

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        initListener()
        getLanguages()
    }

    private fun initListener() {
        btnDetectLanguage.setOnClickListener{
            val text = etDescription.text.toString()
            if(text.isNotEmpty()){
                getTextLanguage(text)
            }
        }
    }

    private fun getTextLanguage(text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = retrofitService.getTextLanguage(text)
            if(result.isSuccessful){
                checkResult(result.body())
            }else{
                showError()
            }
        }
    }

    private fun checkResult(detectionResponse: DetectionResponse?) {
        if(detectionResponse != null && !detectionResponse.data.detections.isNullOrEmpty()){
            val correctLanguage = detectionResponse.data.detections.filter { it.isReliable }
            if(correctLanguage.isNotEmpty()){
                var languageName = languages.find { it.code == correctLanguage.first().language }
                if(languageName != null) {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "The language detected ${languageName.name}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
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
            Toast.makeText(this, "Request Success!!", Toast.LENGTH_LONG).show()
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
