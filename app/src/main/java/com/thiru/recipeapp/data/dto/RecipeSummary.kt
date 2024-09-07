package com.thiru.recipeapp.data.dto

data class RecipeSummary(
    val id: Int,
    val summary: String,
    val title: String,
    val imageUrl: String?,
) {

    fun toRecipeSummary(imageUrl: String?): RecipeSummary {
        return copy(id = id, summary = summary, imageUrl = imageUrl, title = title)
    }
}