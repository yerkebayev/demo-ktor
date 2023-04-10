package com.example.dao

import com.example.model.Module
import com.example.model.ModuleLink

interface ModuleLinkDAO {
    suspend fun getModuleLinks(filter: Map<String, Any>, offset: Long, limit: Int): List<ModuleLink>
    suspend fun moduleLink(id: Int): ModuleLink?
    suspend fun addNewModuleLink(parentId: Int,
                                 childId: Int,
                                 linkType: String): ModuleLink?
    suspend fun editModuleLink(id: Int,
                               parentId: Int,
                               childId: Int,
                               linkType: String
    ): Boolean
    suspend fun deleteModuleLink(id: Int): Boolean
}