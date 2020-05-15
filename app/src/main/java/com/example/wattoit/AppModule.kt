package com.example.wattoit

import com.example.wattoit.data.RecipeViewModel
import com.example.wattoit.data.localDB.RecipeDatabase
import com.example.wattoit.login.data.RestClient
import org.koin.dsl.module

fun createAppModule() = module {
    single { RestClient() }
    single { RecipeViewModel(get()) }
    single {RecipeDatabase.getInstance(get())}
}
