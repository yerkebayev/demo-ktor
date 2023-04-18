package com.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class StudentEachTeacher(
    val teacherId: Int,
    val teacherName: String,
    val numberOfStudents: Int
) {
    companion object {
        inline fun fromJson(json: String): StudentEachTeacher {
            return Json.decodeFromString(json)
        }

        inline fun toJson(page: List<StudentEachTeacher>): String {
            return Json.encodeToString(page)
        }
    }

}
