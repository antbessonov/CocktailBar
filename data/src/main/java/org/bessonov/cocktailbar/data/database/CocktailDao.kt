package org.bessonov.cocktailbar.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import org.bessonov.cocktailbar.data.database.model.CocktailDbModel
import org.bessonov.cocktailbar.domain.model.Cocktail

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cocktailDbModel: CocktailDbModel)

    @Query("SELECT * FROM cocktails WHERE title == :title LIMIT 1")
    fun get(title: String): Flow<CocktailDbModel>

    @Update
    suspend fun update(cocktail: Cocktail)

    @Query("DELETE FROM cocktails WHERE title == :title")
    suspend fun delete(title: String)

    @Query("SELECT * FROM cocktails")
    fun getList(): Flow<List<CocktailDbModel>>
}