package com.example.wattoit

import android.content.Context
import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.SessionManager
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.data.RestClient
import org.koin.dsl.module

fun createAppModule() = module {
    single { RestClient() }
    single { RecipeViewModel(get()) }
    single { RecipeDatabase.getInstance(get()) }
    single { SessionManager(get())}
}
