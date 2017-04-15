package com.pinup.pfm.common.domain.model

import java.io.Serializable

/**
 * Speech transaction item
 */
class SpeechTransaction(var amount: Double = 0.0,
                        var name: String = "") : Serializable {

    constructor() : this(0.0, "")

    companion object {
        private const val serialVersionUID: Long = 1L
    }

    override fun toString(): String {
        return "$amount\n$name"
    }
}