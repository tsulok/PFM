package com.pinup.pfm.interactor.social.facebook.model

import org.json.JSONException
import org.json.JSONObject


/**
 * Facebook login cancelled
 */
class FacebookLoginCancelledException : IllegalStateException()

/**
 * Facebook token missing exception
 */

class FacebookAccessTokenMissingException : IllegalArgumentException()

/**
 * Wrapper for facebook login data
 */
data class FacebookDataWrapper(val email: String, val name: String, val token: String, val id: String)

/**
 * UserDTO data from facebook graph api
 */
class FacebookGraphUser @Throws(JSONException::class)
constructor(jsonObject: JSONObject) {

    val id: String = jsonObject.getString("id")
    var firstName: String? = null
        private set
    var lastName: String? = null
        private set
    var email: String? = null
        private set
    var gender: String? = null
        private set
    var birthday: String? = null
        private set
    var location: String? = null
        private set

    init {
        if (jsonObject.has("first_name")) {
            firstName = jsonObject.getString("first_name")
        }
        if (jsonObject.has("last_name")) {
            lastName = jsonObject.getString("last_name")
        }
        if (jsonObject.has("email")) {
            email = jsonObject.getString("email")
        }
        if (jsonObject.has("gender")) {
            gender = jsonObject.getString("gender")
        }
        if (jsonObject.has("birthday")) {
            birthday = jsonObject.getString("birthday")
        }
        if (jsonObject.has("location")) {
            location = jsonObject.getJSONObject("location").getString("name")
        }
    }
}