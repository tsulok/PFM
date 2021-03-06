package com.pinup.pfm.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.pinup.pfm.utils.helper.UIHelper

/**
 * Extension for context
 */

/**
 * Enum for supported resource types
 */
enum class ResourceType constructor(val type: kotlin.String) {
    String("string"),
    Drawable("drawable"),
    Array("array");
}

/**
 * Returns an identifier for the given resource type
 * @return Return the id of the resource, or 0 if item is not found
 */
fun Context.getResourceIdentifier(resourceName: String, resourceType: ResourceType): Int {
    return resources.getIdentifier(resourceName, resourceType.type, this.packageName)
}

/**
 * Returns a drawable for the user associated by a name if found, null otherwise
 * @param name The name of the resource
 */
fun Context.getDrawableForName(name: String): Drawable? {
    val resourceId = getResourceIdentifier(name, ResourceType.Drawable)
    if (resourceId == 0) {
        return null
    } else {
        return resources.getDrawable(resourceId, null)
    }
}

/**
 * Checks if all permission is granted
 */
fun Context.isPermissionsGranted(vararg permissions: String): Boolean {
    return permissions.none { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }
}

fun Context.string(identifier: Int): String {
    return this.resources.getString(identifier)
}

fun Context.makeToast(message: String) {
    UIHelper.instance.makeToast(this, message)
}

fun Context.makeToast(stringResourceId: Int) {
    UIHelper.instance.makeToast(this, resources.getString(stringResourceId))
}