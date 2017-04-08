package com.pinup.pfm.domain.repository.manager

import com.pinup.pfm.model.database.DaoMaster
import javax.inject.Inject

/**
 * Common repository actions
 */
class RepositoryManager
@Inject constructor(private val daoMaster: DaoMaster): IRepositoryManager {

    override fun clearDatabase() {
        DaoMaster.dropAllTables(daoMaster.getDatabase(), true)
        DaoMaster.createAllTables(daoMaster.getDatabase(), true)
    }
}

interface IRepositoryManager {

    /**
     * Removes all data from database by recreating the scheme
     */
    fun clearDatabase()
}