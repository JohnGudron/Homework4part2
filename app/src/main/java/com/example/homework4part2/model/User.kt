package com.example.homework4part2.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class User(
    val id: Long,
    val name: String,
    val surname: String,
    val number: String,
    val avatarUrl: String
) : Parcelable