package weedro.ingredienter

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("weedro.ingredienter")
            .start()
}

