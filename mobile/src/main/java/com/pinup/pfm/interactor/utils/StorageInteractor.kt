package com.pinup.pfm.interactor.utils

import android.content.Context
import com.pinup.pfm.di.qualifiers.ApplicationContext
import com.sromku.simple.storage.SimpleStorage
import com.sromku.simple.storage.Storage
import java.io.File
import javax.inject.Inject

interface IStorageInteractor {
    /**
     * Creates a temporary file in the cache
     */
    fun createTemporaryFile(): File

    fun createDirectory(dirName: String)
    fun createFile(name: String, fileType: FileType = FileType.Image)
    fun getFile(name: String, fileType: FileType = FileType.Image): File?
    fun getFileWithPath(path: String): File?
    /**
     * Deletes a file
     * @param name
     * @param fileType
     */
    fun deleteFile(name: String, fileType: FileType = FileType.Image)

    /**
     * Move file to an other destination
     * @return The moved file's reference
     */
    fun moveFile(originalFile: File, name: String, fileType: FileType = FileType.Image): File?
}

/**
 * Interactor for handling storage functionalities
 */
class StorageInteractor @Inject constructor(@ApplicationContext val context: Context) : IStorageInteractor {

    val forceUseInternalStorage: Boolean = false

    /**
     * Creates a temporary file in the cache
     */
    override fun createTemporaryFile(): File {
        return File.createTempFile("pfm-image", ".jpg", context.cacheDir)
    }

    /**
     * Returns the default available storage
     * @param shouldInternalStorage If set true then internal storage must be used. Otherwise external storage can be used if found. False by default
     * @return the default available storage
     */
    private fun getAvailableStorage(shouldInternalStorage: Boolean): Storage {
        return if (SimpleStorage.isExternalStorageWritable() && !shouldInternalStorage) SimpleStorage.getExternalStorage() else SimpleStorage.getInternalStorage(context)
    }

    override fun createDirectory(dirName: String) {
        getAvailableStorage(forceUseInternalStorage).createDirectory(dirName)
    }

    override fun createFile(name: String, fileType: FileType) {
        val storage = getAvailableStorage(forceUseInternalStorage)

        if (!storage.isDirectoryExists(fileType.dirName)) {
            createDirectory(fileType.dirName)
        }

        storage.createFile(fileType.dirName, name, "")
    }

    override fun getFile(name: String, fileType: FileType): File? {
        val storage = getAvailableStorage(forceUseInternalStorage)
        return storage.getFile(fileType.dirName, name)
    }

    override fun getFileWithPath(path: String): File? {
        val storage = getAvailableStorage(forceUseInternalStorage)
        return storage.getFile(path)
    }

    /**
     * Deletes a file
     * @param name
     * @param fileType
     */
    override fun deleteFile(name: String, fileType: FileType) {
        val storage = getAvailableStorage(forceUseInternalStorage)
        storage.deleteFile(fileType.dirName, name)
    }

    /**
     * Move file to an other destination
     * @return The moved file's reference
     */
    override fun moveFile(originalFile: File, name: String, fileType: FileType): File? {
        val storage = getAvailableStorage(forceUseInternalStorage)
        storage.move(originalFile, fileType.dirName, name)
        return storage.getFile(fileType.dirName, name)
    }
}

enum class FileType(val dirName: String) {
    Image("Images")
}