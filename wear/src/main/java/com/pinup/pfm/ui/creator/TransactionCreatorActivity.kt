package com.pinup.pfm.ui.creator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.wearable.view.WatchViewStub
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import com.pinup.pfm.R
import com.pinup.pfm.common.communication.TransactionCommunicator
import com.pinup.pfm.common.converter.ByteConverter
import com.pinup.pfm.common.domain.model.SpeechTransaction
import com.pinup.pfm.common.ui.core.BaseScreen
import com.pinup.pfm.common.ui.core.IBasePresenter


class TransactionCreatorActivity : Activity(), TransactionCreatorScreen,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private val presenter: TransactionCreatorPresenter by lazy { TransactionCreatorPresenter() }

    private val SPEECH_REQUEST_CODE = 0

    private lateinit var createNewButton: Button
    private lateinit var saveButton: Button
    private lateinit var informationTextView: TextView
    private lateinit var logoImageView: ImageView

    private var connectedNode: Node? = null // the connected device to send the message to
    private lateinit var googleApiClient: GoogleApiClient
    private val resolvingError = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stub = findViewById(R.id.watch_view_stub) as WatchViewStub
        stub.setOnLayoutInflatedListener { stub ->
            createNewButton = stub.findViewById(R.id.createNewButton) as Button
            saveButton = stub.findViewById(R.id.saveBtn) as Button
            informationTextView = stub.findViewById(R.id.informationTxt) as TextView
            logoImageView = stub.findViewById(R.id.logo) as ImageView
            initListeners()
        }
        initGoogleServices()
    }

    private fun initGoogleServices() {
        //Connect the GoogleApiClient
        googleApiClient = GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
    }

    private fun initListeners() {
        createNewButton.setOnClickListener { presenter.createClicked() }
        saveButton.setOnClickListener { presenter.saveIndicated() }
    }

    override fun onStart() {
        super.onStart()
        getPresenter()?.bind(getScreen())
        if (!resolvingError) {
            googleApiClient.connect()
        }
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
        clearUIToInitialState()
    }

    private fun clearUIToInitialState() {
        informationTextView.visibility = View.GONE
        saveButton.visibility = View.GONE
        logoImageView.visibility = View.VISIBLE
    }

    override fun showParsedData(lastParsedSpeechText: SpeechTransaction) {
        informationTextView.text = lastParsedSpeechText.toString()
        informationTextView.visibility = View.VISIBLE
        saveButton.visibility = View.VISIBLE
        logoImageView.visibility = View.GONE
    }

    fun getPresenter(): IBasePresenter? = presenter
    fun getScreen(): BaseScreen = this

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e("Teszt", "Google api connection failed" + p0.errorMessage)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e("Teszt", "Google api connection suspended")
    }

    override fun onConnected(p0: Bundle?) {
        resolveNodes()
    }

    private fun resolveNodes() {
        Wearable.NodeApi.getConnectedNodes(googleApiClient)
                .setResultCallback { nodes -> nodes.nodes.firstOrNull()?.let { connectedNode = it } }
    }

    private fun sendData(item: SpeechTransaction) {

        if (!googleApiClient.isConnected) {
            Log.e("Teszt", "Google api is not connected")
            return
        }

        connectedNode?.let { node ->
            Wearable.MessageApi.sendMessage(
                    googleApiClient,
                    node.id,
                    TransactionCommunicator.TRANSACTION_KEY,
                    ByteConverter.convertToBytes(item)).setResultCallback { sendMessageResult ->
                if (!sendMessageResult.status.isSuccess) {
                    Log.e("Teszt", "Failed to send message with status code: " + sendMessageResult.status.statusCode)
                } else {
                    clearUIToInitialState()
                }
            }
        }
    }

    override fun sendToDevice(it: SpeechTransaction) {
        sendData(it)
    }
}
