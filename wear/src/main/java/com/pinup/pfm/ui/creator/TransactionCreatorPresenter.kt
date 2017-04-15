package com.pinup.pfm.ui.creator

import com.pinup.pfm.common.domain.model.SpeechTransaction
import com.pinup.pfm.common.ui.core.BasePresenter
import java.util.ArrayList

/**
 * Presenter for creator
 */
class TransactionCreatorPresenter: BasePresenter<TransactionCreatorScreen>() {

    var lastParsedSpeechText: SpeechTransaction? = null

    // Create button clicked
    fun createClicked() {
        screen?.showSpeechRecongitionUI()
    }

    // Voice data should be parsed here
    fun parseVoiceData(results: ArrayList<String>?) {

        if (results == null || results.isEmpty()) {
            screen?.undetectedVoice()
            return
        }

        val splittedSpeech = results.first().split(" ")
        lastParsedSpeechText = SpeechTransaction()
        if (splittedSpeech.isNotEmpty()) {
            splittedSpeech[0].toDoubleOrNull()?.let {
                lastParsedSpeechText?.amount = it
            }
        }

        if (splittedSpeech.size > 1) {
            lastParsedSpeechText?.name = splittedSpeech
                    .subList(1, splittedSpeech.size)
                    .reduce { acc, s -> "$acc $s" }
        }

        if (lastParsedSpeechText != null) {
            screen?.showParsedData(lastParsedSpeechText!!)
        }
    }

    fun saveIndicated() {
        lastParsedSpeechText?.let {
            screen?.sendToDevice(it)
        }
    }
}