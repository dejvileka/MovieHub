package com.dejvidleka.data.local.database

import androidx.room.TypeConverter

object GenreIdListTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    @JvmStatic
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}
