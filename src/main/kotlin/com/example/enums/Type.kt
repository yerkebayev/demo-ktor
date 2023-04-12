package com.example.enums

import java.util.*

enum class Type {
    PROGRAM,
    MODULE
}


fun toType(type: String): Type? {
    return when (type.uppercase(Locale.getDefault())) {
        "PROGRAM" -> Type.PROGRAM
        "MODULE" -> Type.MODULE
        else -> null
    }
}

fun fromType(type: Type?): String {
    return when (type) {
        Type.PROGRAM -> "program"
        Type.MODULE -> "module"
        null -> "null"
    }
}
