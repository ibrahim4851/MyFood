package com.example.myfood.room

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "recipe_db"
        ).build()

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeDao: FoodDao): FoodRepository =
        FoodRepositoryImpl(dao = recipeDao)

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDatabase: FoodDatabase) = recipeDatabase.dao


    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    }

}