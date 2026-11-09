package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CoffeeDatabase
import com.example.data.CoffeeQuizRepository
import com.example.model.BeverageLayer
import com.example.model.CupType
import com.example.model.IngredientType
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Barista Simulator", appName)
  }

  @Test
  fun `coffee database has drinks with valid receipts and layers`() {
    val drinks = CoffeeDatabase.drinks
    assertTrue("Should have multiple coffee drinks", drinks.size >= 12)

    val cappuccino = drinks.find { it.id == "cappuccino" }
    assertNotNull(cappuccino)
    assertEquals("Cappuccino", cappuccino!!.name)
    assertTrue("Cappuccino should have layers", cappuccino.layers.isNotEmpty())
    assertNotNull("Cappuccino should have receipt", cappuccino.receipt)
    assertTrue("Receipt should have items", cappuccino.receipt.items.isNotEmpty())
  }

  @Test
  fun `drink evaluation matches cappuccino accurately`() {
    // 1/3 espresso (60ml), 1/3 steamed milk (60ml), 1/3 foam (60ml)
    val userLayers = listOf(
      BeverageLayer(IngredientType.ESPRESSO, 60),
      BeverageLayer(IngredientType.STEAMED_MILK, 60),
      BeverageLayer(IngredientType.AIRY_FOAM, 60)
    )

    val evaluation = CoffeeDatabase.evaluateCreatedDrink(userLayers, CupType.CERAMIC_CUP)
    assertEquals("cappuccino", evaluation.matchedDrink?.id)
    assertTrue("Score should be high for matching ingredients", evaluation.accuracyScore >= 80)
  }

  @Test
  fun `quiz questions are loaded`() {
    val questions = CoffeeQuizRepository.questions
    assertTrue("Should have questions", questions.isNotEmpty())
    questions.forEach { q ->
      assertTrue("Question should have at least 2 options", q.options.size >= 2)
      assertTrue("Correct index should be within bounds", q.correctIndex in q.options.indices)
    }
  }
}
