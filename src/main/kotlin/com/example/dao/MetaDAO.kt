package com.example.dao

import com.example.model.Meta

interface MetaDAO {
    suspend fun allMetas(id: Int): List<Meta>
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