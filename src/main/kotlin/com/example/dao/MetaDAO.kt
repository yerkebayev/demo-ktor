package com.example.dao

import com.example.model.Meta

interface MetaDAO {
    suspend fun getMetas(filter: Map<String, Any>, id: Int, offset: Long, limit: Int): List<Meta>
    suspend fun meta(id: Int): Meta?
    suspend fun addNewMeta(moduleId: Int,
                           metaKey: String,
                           metaValue: String
    ): Meta?
    suspend fun editMeta(id: Int,
                         moduleId: Int,
                         metaKey: String,
                         metaValue: String
    ): Boolean
    suspend fun deleteMeta(id: Int): Boolean
}