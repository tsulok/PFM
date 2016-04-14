package com.pinup.pfm.extensions

import android.content.Context
import android.graphics.drawable.Drawable

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