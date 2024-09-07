package com.thiru.recipeapp.di

import com.thiru.recipeapp.data.repository.RecipeRepositoryImpl
import com.thiru.recipeapp.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRecipeListRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository
}