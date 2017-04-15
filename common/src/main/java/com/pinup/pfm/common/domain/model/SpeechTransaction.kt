package com.pinup.pfm.common.domain.model

/**
 * Speech transaction item
 */
class SpeechTransaction(var amount: Double = 0.0,
                        var name: String = "") {

    override fun toString(): String {
        return "$amount\n$name"
    }
}