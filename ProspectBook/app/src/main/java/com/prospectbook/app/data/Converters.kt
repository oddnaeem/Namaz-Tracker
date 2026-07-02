package com.prospectbook.app.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStatus(status: Status): String = status.name

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)
}
