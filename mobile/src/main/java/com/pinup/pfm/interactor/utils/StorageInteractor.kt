package com.pinup.pfm.interactor.utils

import android.content.Context
import com.pinup.pfm.PFMApplication
import com.sromku.simple.storage.InternalStorage
import com.sromku.simple.storage.SimpleStorage
import com.sromku.simple.storage.Storage
import java.io.File
import javax.inject.Inject

/**
 * Interactor for handling storage functionalities
 */
class StorageInteractor {

    @Inject lateinit var context: Context

    val forceUseInternalStorage: Boolean = true

    constructor() {
        PFMApplication.injector.inject(this)
    }

    /**
     * Returns the default available storage
     * @param shouldInternalStorage If set true then internal storage must be used. Otherwise external storage can be used if found. False by default
     * @return the default available storage
     */
    private fun getAvailableStorage(shouldInternalStorage: Boolean = false): Storage {
        return if (SimpleStorage.isExternalStorageWritable() && !shouldInternalStorage) SimpleStorage.getExternalStorage() else SimpleStorage.getInternalStorage(context)
    }

    fun createDirectory(dirName: String) {
        getAvailableStorage(forceUseInternalStorage).createDirectory(dirName)
    }

    fun createFile(name: String, fileType: FileType = FileType.Image) {
        val storage = getAvailableStorage(forceUseInternalStorage)

        if (!storage.isDirectoryExists(fileType.dirName)) {
            createDirectory(fileType.dirName)
        }

        storage.createFile(fileType.dirName, name, "")
    }

    fun getFile(name: String, fileType: FileType = FileType.Image): File? {
        val storage = getAvailableStorage(forceUseInternalStorage)
        return storage.getFile(fileType.dirName, name)
    }
}

enum class FileType(val dirName: String) {
    Image("Images")
}