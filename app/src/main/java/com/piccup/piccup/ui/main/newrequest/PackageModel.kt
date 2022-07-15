package com.piccup.piccup.ui.main.newrequest

data class PackageModel(
    val id: String?,
    val name: String?,
    val photo: String?,
    val description: String?,
    val per_month: String?,
    val per_semester: String?,
    var selected: Boolean = false,
)