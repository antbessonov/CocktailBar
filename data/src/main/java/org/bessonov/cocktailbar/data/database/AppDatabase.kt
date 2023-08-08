package org.bessonov.cocktailbar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.bessonov.cocktailbar.data.database.model.CocktailDbModel

@Database(
    entities = [CocktailDbModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao
}