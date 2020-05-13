package com.example.wattoit.data


import com.example.wattoit.domain.entity.Recipe
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("success")
    val successCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String
)

data class RegistrationResponse(
    @SerializedName("success")
    val successCode: Int
)

data class RecipeSearchResponse(
    @SerializedName("success")
    val successCode: Int,

    @SerializedName("data")
    val recipes: List<RecipeResponse>
)

data class RecipeResponse(
    @SerializedName("recipe")
    val recipe:Recipe
): Serializable

