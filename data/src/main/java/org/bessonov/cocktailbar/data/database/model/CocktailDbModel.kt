package org.bessonov.cocktailbar.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailDbModel(
    @PrimaryKey
    val title: String,
    val ingredientList: String,
    val description: String?,
    val recipe: String?
)
