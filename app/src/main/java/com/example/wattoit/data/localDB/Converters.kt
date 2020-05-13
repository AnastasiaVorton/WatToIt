package com.example.wattoit.data.localDB

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun fromStringToArraylist(value: String): ArrayList<String> {
        val mapType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromArraylistToString(list: ArrayList<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}