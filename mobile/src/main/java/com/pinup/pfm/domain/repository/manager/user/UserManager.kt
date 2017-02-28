package com.pinup.pfm.domain.repository.manager.user

import com.pinup.pfm.domain.repository.manager.BaseDaoManager
import com.pinup.pfm.model.database.*
import javax.inject.Inject

/**
 * Dao manager for categories
 */

class UserDaoManager @Inject constructor(val daoSession: DaoSession)
    : BaseDaoManager<UserDao, User>(daoSession.userDao), IUserRepository {

    override fun loadByServerId(serverId: String): User? {
        return createQuery()
                .where(UserDao.Properties.ServerId.eq(serverId))
                .unique()
    }
}