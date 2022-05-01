package weedro.ingredienter.infrastructure

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Value
import io.micronaut.context.event.StartupEvent
import io.micronaut.runtime.event.annotation.EventListener
import jakarta.inject.Singleton
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import weedro.ingredienter.domain.Ingredient
import weedro.ingredienter.service.IngredientService
import java.time.Duration

@KafkaClient
interface KafkaPublisher {

    @Topic("\${app.kafka.topic.ingredient-generate}")
    fun publishIngredientGenerateEvent(ingredient: Ingredient): Mono<Ingredient>

}

@Singleton
class IngredientPublisher(
    private val ingredientService: IngredientService,
    private val kafkaPublisher: KafkaPublisher
) {

    @Value("\${app.generate-ingredient-delay}")
    lateinit var generateIngredientDelay: String

    @EventListener
    fun createIngredient(event: StartupEvent?): Ingredient? =
        generateSequence(0) { it }.toFlux()
//            .delayElements(Duration.parse(generateIngredientDelay))
                .delayElements(Duration.ofSeconds(15))
                .log("generate ingredient")
                .map { ingredientService.generateIngredient() }
                .log("send ingredient")
                .flatMap { kafkaPublisher.publishIngredientGenerateEvent(it) }
                .blockLast()

}

/*
@KafkaClient
interface KafkaPublisher {

    @Topic("\${app.kafka.topic.order-create}")
    fun publishOrderCreateEvent(recipe: Recipe): Mono<Recipe>

}

@Singleton
class OrderPublisher(
    private val recipeRepository: RecipeRepository,
    private val kafkaPublisher: KafkaPublisher
) {

    @Value("\${app.generate-order-delay}")
    lateinit var generateOrderDelay: String

    @EventListener
    fun createOrder(event: StartupEvent?): Recipe? =
        generateSequence(0) { it }.toFlux()
            .delayElements(Duration.parse(generateOrderDelay))
            .log("create random order")
            .map { recipeRepository.random() }
            .log("send random order")
            .flatMap { it }
            .flatMap { kafkaPublisher.publishOrderCreateEvent(it) }
            .blockLast()
}

 */


