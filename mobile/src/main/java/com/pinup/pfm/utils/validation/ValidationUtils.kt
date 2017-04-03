package com.pinup.pfm.utils.validation

import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Utility wrapper for validation purposes
 */

class ValidationUtils
@Inject constructor() {
    private object ValidationUtilConstants {
        internal val EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-+]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        internal val MIN_PSWD_LENGTH = 4
    }

    val emailPattern = Pattern.compile(ValidationUtilConstants.EMAIL_REGEX)

    fun isMailValid(email: String): Boolean {
        val matcher = emailPattern.matcher(email)
        return matcher.matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= ValidationUtilConstants.MIN_PSWD_LENGTH
    }
}