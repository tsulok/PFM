package com.pinup.pfm.common.converter

import java.io.*


/**
 * Converter helper
 */

class ByteConverter {
    companion object {
        @Throws(IOException::class)
        @JvmStatic fun convertToBytes(`object`: Any): ByteArray {
            ByteArrayOutputStream().use({ bos ->
                ObjectOutputStream(bos).use({ out ->
                    out.writeObject(`object`)
                    return bos.toByteArray()
                })
            })
        }

        @Throws(IOException::class, ClassNotFoundException::class)
        @JvmStatic fun convertFromBytes(bytes: ByteArray): Any {
            ByteArrayInputStream(bytes).use({ bis ->
                ObjectInputStream(bis).use({ `in` ->
                    return `in`.readObject()
                })
            })
        }
    }
}