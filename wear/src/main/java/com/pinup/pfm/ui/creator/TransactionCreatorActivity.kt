package com.pinup.pfm.ui.creator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.wearable.view.WatchViewStub
import android.widget.Button
import com.pinup.pfm.R


class TransactionCreatorActivity : Activity() {

    private val SPEECH_REQUEST_CODE = 0

    private var createNewButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stub = findViewById(R.id.watch_view_stub) as WatchViewStub
        stub.setOnLayoutInflatedListener { stub ->
            createNewButton = stub.findViewById(R.id.createNewButton) as Button
            createNewButton?.setOnClickListener { displaySpeechRecognizer() }
        }
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.bind(getScreen())
    }

    override fun onStop() {
        getPresenter()?.unbind()
        super.onStop()
    }

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent) {
        if (resultCode == RESULT_OK) {
            val results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results[0]
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getPresenter(): IBasePresenter?
    fun getScreen(): BaseScreen
}
