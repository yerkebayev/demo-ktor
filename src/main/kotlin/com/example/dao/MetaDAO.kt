package com.example.dao

import com.example.StudentEachTeacher
import com.example.model.Meta
import com.example.model.Module

interface MetaDAO {
    suspend fun getMetasWithFilters(filter: Map<String, Any>, moduleId: Int): List<Meta>
    suspend fun getWithPagination (metaList: List<Meta>, offset: Long, limit: Int): List<Meta>
    suspend fun studentsEachTeacher(): List<StudentEachTeacher>
    suspend fun howManyTeachersInModule(): Map<String, Int>
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