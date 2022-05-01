package weedro.ingredienter.service

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.inject.Singleton

interface AssetService {

    fun ingredients(): List<String>

    @Singleton
    class Base(private val objectMapper: ObjectMapper) : AssetService {

        override fun ingredients(): List<String> {
            val rawIngredientsText: String =
                this::class.java.classLoader.getResource("ingredients.json")
                        ?.readText()
                    ?: throw IllegalArgumentException("ingredients.json not find")

            return objectMapper.readValue(rawIngredientsText, List::class.java)
                    .filterIsInstance<String>()
        }

    }

}