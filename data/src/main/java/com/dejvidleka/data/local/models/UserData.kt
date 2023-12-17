package com.dejvidleka.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val uid: String="",
    val name: String="",
    val age: String= "",
    val location: String= "",
    val moviePreferences: List<String> = emptyList()
) : Parcelable