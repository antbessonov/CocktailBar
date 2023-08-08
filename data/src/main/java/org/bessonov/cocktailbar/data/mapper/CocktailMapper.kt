package org.bessonov.cocktailbar.data.mapper

import org.bessonov.cocktailbar.data.database.model.CocktailDbModel
import org.bessonov.cocktailbar.domain.model.Cocktail

class CocktailMapper {

    fun mapDbModelListToEntityList(dbModelList: List<CocktailDbModel>): List<Cocktail> {
        return dbModelList
            .map { dbModel -> mapDbModelToEntity(dbModel = dbModel) }
    }

    fun mapEntityToDbModel(entity: Cocktail): CocktailDbModel {
        return CocktailDbModel(
            title = entity.title,
            description = entity.description,
            recipe = entity.recipe
        )
    }

    fun mapDbModelToEntity(dbModel: CocktailDbModel): Cocktail {
        return Cocktail(
            title = dbModel.title,
            description = dbModel.description,
            recipe = dbModel.recipe
        )
    }
}