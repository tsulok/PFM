package com.pinup.pfm.domain.repository.manager.user

import com.pinup.pfm.domain.repository.manager.IBaseRepository
import com.pinup.pfm.model.database.User

/**
 * Category repository interface
 */

interface IUserRepository : IBaseRepository<User> {

    /**
     * @return The desired item associated with the server id or nil
     */
    fun loadByServerId(serverId: String): User?
}