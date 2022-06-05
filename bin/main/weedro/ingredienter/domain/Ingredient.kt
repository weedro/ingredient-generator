package weedro.ingredienter.domain

import io.micronaut.core.annotation.Introspected

@Introspected
data class Ingredient(var name: String, var quantity: Int) {

    // empty constructor for jackson
    constructor() : this("", 0)

}
