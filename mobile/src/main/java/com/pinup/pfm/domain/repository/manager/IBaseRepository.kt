package com.pinup.pfm.domain.repository.manager

import android.support.annotation.Nullable

/**
 * Base supported actions on domain objects
 */
interface IBaseRepository<T> {

    /**
     * Returns data with the given primary key

     * @param id The primary key to be searched with
     * *
     * @return The data if found or null
     */
    @Nullable
    fun loadById(id: String): T?

    /**
     * Inserts a new or replace the existing item in the database

     * @param item The item to be inserted or replaced
     */
    fun insertOrUpdate(item: T)

    /**
     * Updates an existing item in the database

     * @param item The item to be updated
     */
    fun update(item: T)

    /**
     * Deletes the item from the database

     * @param item The item to be deleted
     */
    fun delete(item: T)

    /**
     * List all items for this type

     * @return Items for this type
     */
    fun listAllItems(): List<T>

    /**
     * @return List items for this type specified with the ids list
     */
    fun listItems(ids: List<String>): List<T>

    /**
     * Remove the items for this type specified with the ids list
     */
    fun removeItems(ids: List<String>)

    /**
     * Removes all entities for this entity type
     */
    fun removeAll()
}