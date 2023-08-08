package org.bessonov.cocktailbar.di.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bessonov.cocktailbar.data.database.AppDatabase
import org.bessonov.cocktailbar.data.database.CocktailDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .createFromAsset("initial_cocktail_bar")
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailDao(
        appDatabase: AppDatabase
    ): CocktailDao {
        return appDatabase.cocktailDao()
    }

    private const val DB_NAME = "cocktail_bar.db"
}