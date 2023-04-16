package com.example.dao

import com.example.enums.Status
import com.example.model.Module
import org.jetbrains.exposed.sql.Op

interface ModuleDAO {
    suspend fun getModulesWithFilters(filter: Map<String, Any>): List<Module>
    suspend fun getWithPagination (moduleList: List<Module>, offset: Long, limit: Int): List<Module>

    suspend fun getCountOfRowsForFilter(whereCl: Op<Boolean>): Long
    suspend fun getWithFilterAndPagination(offset: Long, limit: Int, whereCl: Op<Boolean> ): List<Module>

    suspend fun getFiltering(filter: Map<String, Any>): Op<Boolean>
    suspend fun module(id: Int): Module?
    suspend fun addNewModule(name: String,
                             type: String,
                             createdAt: String,
                             duration: Int,
                             status: Status,
                             description: String): Module
    suspend fun editModule(id: Int,
                           name: String,
                           type: String,
                           createdAt: String,
                           duration: Int,
                           status: Status,
                           description: String): Boolean
    suspend fun deleteModule(id: Int): Boolean

}