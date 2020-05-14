package com.example.wattoit.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Recipe(
    val label: String,
    @PrimaryKey
    val url: String,
    val image: String,
    val dietLabels: ArrayList<String>,
    val healthLabels: ArrayList<String>,
    val ingredientLines: ArrayList<String>,
    val calories: Double
) : Serializable
