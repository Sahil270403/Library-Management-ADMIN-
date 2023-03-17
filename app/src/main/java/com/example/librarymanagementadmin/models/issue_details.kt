package com.example.librarymanagementadmin.models

data class issue_details(
    val bookName: String? = null,
    val authorName: String? = null,
    val branch: String? = null,
    val userName: String? = null,
    val rollNo: String? = null,
    val sbranch: String? = null,
    var date: Map<String, Any>? = null
)

