package org.bessonov.cocktailbar.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.bessonov.cocktailbar.data.database.CocktailDao
import org.bessonov.cocktailbar.data.mapper.CocktailMapper
import org.bessonov.cocktailbar.domain.model.Cocktail
import org.bessonov.cocktailbar.domain.repository.CocktailRepository

class CocktailRepositoryImpl(
    private val cocktailDao: CocktailDao,
    private val cocktailMapper: CocktailMapper
) : CocktailRepository {

    override suspend fun add(cocktail: Cocktail) {
        cocktailDao.insert(cocktailDbModel = cocktailMapper.mapEntityToDbModel(entity = cocktail))
    }

    override fun get(title: String): Flow<Cocktail> {
        return cocktailDao.get(title = title)
            .map { dbModel -> cocktailMapper.mapDbModelToEntity(dbModel = dbModel) }
    }

    override suspend fun update(cocktail: Cocktail) {
        cocktailDao.update(cocktailDbModel = cocktailMapper.mapEntityToDbModel(entity = cocktail))
    }

    override suspend fun delete(title: String) {
        cocktailDao.delete(title = title)
    }

    override fun getList(): Flow<List<Cocktail>> {
        return cocktailDao.getList()
            .map { dbModelList ->
                cocktailMapper.mapDbModelListToEntityList(dbModelList = dbModelList)
            }
    }
}