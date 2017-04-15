package com.pinup.pfm.ui.creator

import com.pinup.pfm.common.domain.model.SpeechTransaction
import com.pinup.pfm.common.ui.core.BaseScreen

/**
 * Possible actions on transaction creator
 */
interface TransactionCreatorScreen: BaseScreen {

    // Create an intent that can start the Speech Recognizer activity
    fun showSpeechRecongitionUI()

    // Voice is undetected
    fun undetectedVoice()

    /**
     * UI should show parsed data
     */
    fun showParsedData(lastParsedSpeechText: SpeechTransaction)
}