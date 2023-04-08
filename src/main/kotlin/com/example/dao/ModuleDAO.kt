package com.example.dao

import com.example.enums.Status
import com.example.model.Module

interface ModuleDAO {
    suspend fun allModules(): List<Module>
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