package weedro.ingredienter.service

import jakarta.inject.Singleton
import weedro.ingredienter.domain.Ingredient
import kotlin.random.Random

interface IngredientService {

    fun generateIngredient(): Ingredient

    @Singleton
    class Base(private val assetService: AssetService) : IngredientService {

        private val random = Random(123098)

        override fun generateIngredient(): Ingredient {
            val ingredients = assetService.ingredients()

            val name = ingredients.random(random)
            val quantity = random.nextInt(10)

            return Ingredient(name, quantity)
        }

    }

}
