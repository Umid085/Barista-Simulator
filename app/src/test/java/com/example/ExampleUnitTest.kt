package com.example

import com.example.data.CoffeeDatabase
import com.example.data.CoffeeQuizRepository
import com.example.model.BeverageLayer
import com.example.model.CupType
import com.example.model.IngredientType
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun coffeeDatabase_hasAllDrinksWithReceipts() {
    val drinks = CoffeeDatabase.drinks
    assertTrue(drinks.size >= 12)

    val flatWhite = drinks.find { it.id == "flat_white" }
    assertNotNull(flatWhite)
    assertEquals("Flat White", flatWhite!!.name)
    assertTrue(flatWhite.receipt.items.isNotEmpty())
    assertTrue(flatWhite.layers.isNotEmpty())
  }

  @Test
  fun drinkEvaluation_accuratelyScoresMatches() {
    val cappuccinoLayers = listOf(
      BeverageLayer(IngredientType.ESPRESSO, 60),
      BeverageLayer(IngredientType.STEAMED_MILK, 60),
      BeverageLayer(IngredientType.AIRY_FOAM, 60)
    )
    val eval = CoffeeDatabase.evaluateCreatedDrink(cappuccinoLayers, CupType.CERAMIC_CUP)
    assertEquals("cappuccino", eval.matchedDrink?.id)
    assertTrue(eval.accuracyScore >= 80)
  }

  @Test
  fun quizRepository_hasValidQuestions() {
    val questions = CoffeeQuizRepository.questions
    assertTrue(questions.isNotEmpty())
    questions.forEach { q ->
      assertTrue(q.options.size >= 2)
      assertTrue(q.correctIndex in q.options.indices)
    }
  }
}
