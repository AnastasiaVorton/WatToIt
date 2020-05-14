package com.example.wattoit.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wattoit.domain.entity.Recipe

@Database(
    entities = [Recipe::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun recipeDao(): RecipeDAO
}
