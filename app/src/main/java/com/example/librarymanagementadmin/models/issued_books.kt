package com.example.librarymanagementadmin.models

data class issued_books(
    val bookName: String? = null,
    val authorName: String? = null,
    val branch: String? = null,
    val issuerName: String? = null,
    val rollNo: String? = null,
    var startDate: Map<String, Any>? = null,
    var endDate: Map<String, Any>? = null
)


