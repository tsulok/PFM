package com.pinup.pfm.ui.creator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.wearable.view.WatchViewStub
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.pinup.pfm.R
import com.pinup.pfm.common.domain.model.SpeechTransaction
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.common.ui.core.IBasePresenter


class TransactionCreatorActivity : Activity(), TransactionCreatorScreen {

    private val presenter: TransactionCreatorPresenter by lazy { TransactionCreatorPresenter() }

    private val SPEECH_REQUEST_CODE = 0

    private lateinit var createNewButton: Button
    private lateinit var saveButton: Button
    private lateinit var informationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stub = findViewById(R.id.watch_view_stub) as WatchViewStub
        stub.setOnLayoutInflatedListener { stub ->
            createNewButton = stub.findViewById(R.id.createNewButton) as Button
            saveButton = stub.findViewById(R.id.saveBtn) as Button
            informationTextView = stub.findViewById(R.id.informationTxt) as TextView
            initListeners()
        }
    }

    private fun initListeners() {
        createNewButton.setOnClickListener { presenter.createClicked() }
        saveButton.setOnClickListener { presenter.saveIndicated() }
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.bind(getScreen())
    }

    override fun onStop() {
        getPresenter()?.unbind()
        super.onStop()
    }

    override fun showSpeechRecongitionUI() {
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
        getPresenter()?.bind(getScreen())
        if (resultCode == RESULT_OK) {
            val results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS)
            presenter.parseVoiceData(results)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun undetectedVoice() {
        Toast.makeText(this, "Unable to detect voice", Toast.LENGTH_SHORT).show()
        informationTextView.visibility = View.GONE
        saveButton.visibility = View.GONE
    }

    override fun showParsedData(lastParsedSpeechText: SpeechTransaction) {
        informationTextView.text = lastParsedSpeechText.toString()
        informationTextView.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
    }

    fun getPresenter(): IBasePresenter? = presenter
    fun getScreen(): BaseScreen = this
}
