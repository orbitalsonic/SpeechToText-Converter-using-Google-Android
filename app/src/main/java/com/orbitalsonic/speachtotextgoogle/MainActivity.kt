package com.orbitalsonic.speachtotextgoogle

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ReportFragment.Companion.reportFragment

class MainActivity : AppCompatActivity() {

    private val micVoiceActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.apply {
                    getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let { resultList ->
                        if (resultList.isNotEmpty()) {
                            try {
                                setResult(resultList[0])
                            } catch (ex: Exception) {
                                Log.d("SpeechToTextTAG", "micVoiceActivity: ${ex.message}")
                            }
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_speak).setOnClickListener {
            onMicClick()
        }
    }

    private fun onMicClick() {
        try {
            val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            mIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            mIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE, "en"
            )

            micVoiceActivity.launch(mIntent)
        } catch (ex: Exception) {
            Log.d("SpeechToTextTAG", "onUser1MicClick: ${ex.message}")
        }
    }

    private fun setResult(text:String){
        findViewById<TextView>(R.id.tv_result).text = text
    }
}