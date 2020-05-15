package com.example.wattoit.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wattoit.domain.entity.Recipe

@Dao
interface RecipeDAO {
    @Query("SELECT * FROM Recipe")
    suspend fun getSaved(): List<Recipe>


    @Query("DELETE FROM Recipe")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long
}
