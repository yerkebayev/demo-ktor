package com.example.dao

import com.example.model.Meta
import com.example.model.Module

interface DAOFacade {
    suspend fun allModules(): List<Module>
    suspend fun module(id: Int): Module?
    suspend fun addNewModule(name: String,
                             type: String,
                             createdAt: String,
                             duration: Int,
                             status: String,
                             description: String): Module?
    suspend fun editModule(id: Int,
                           name: String,
                           type: String,
                           createdAt: String,
                           duration: Int,
                           status: String,
                           description: String): Boolean
    suspend fun deleteModule(id: Int): Boolean

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