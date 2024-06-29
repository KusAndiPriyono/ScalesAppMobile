package com.bangkit.scalesappmobile.presentatiom.home.component

enum class UserRole {
    ADMIN,
    USER,
    MANAGER;

    companion object {
        fun fromString(value: String): UserRole {
            return when (value.lowercase()) {
                "admin" -> ADMIN
                "user" -> USER
                "manager" -> MANAGER
                else -> throw IllegalArgumentException("Unknown userRole: $value")
            }
        }
    }
}