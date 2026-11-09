package com.example.model

enum class CupType(
  val id: String,
  val displayName: String,
  val capacityMl: Int,
  val description: String,
  val iconEmoji: String
) {
  DEMITASSE(
    id = "demitasse",
    displayName = "Demitasse Cup",
    capacityMl = 70,
    description = "Thick-walled porcelain to preserve crema & temperature.",
    iconEmoji = "☕"
  ),
  GIBRALTAR(
    id = "gibraltar",
    displayName = "Gibraltar Glass",
    capacityMl = 140,
    description = "Faceted 4.5oz glass, the iconic vessel for Cortado.",
    iconEmoji = "🥃"
  ),
  CERAMIC_CUP(
    id = "ceramic_cup",
    displayName = "Ceramic Bowl Cup",
    capacityMl = 190,
    description = "Classic curved bowl for seamless milk integration & latte art.",
    iconEmoji = "🥣"
  ),
  TALL_GLASS(
    id = "tall_glass",
    displayName = "Tall Latte Glass",
    capacityMl = 260,
    description = "Clear vertical glass showcasing distinct gradient layers.",
    iconEmoji = "🥛"
  ),
  ICED_TUMBLER(
    id = "iced_tumbler",
    displayName = "Iced Glass Tumbler",
    capacityMl = 340,
    description = "High-capacity ribbed glass packed with crystal clear ice.",
    iconEmoji = "🧊"
  )
}

enum class CoffeeCategory(val displayName: String, val iconEmoji: String) {
  ALL("All Drinks", "☕"),
  CLASSIC_ESPRESSO("Espresso Bar", "⚡"),
  MILK_AND_FOAM("Milk & Foam", "🥛"),
  SWEET_SPECIALTY("Specialty & Sweet", "🍫"),
  ICED_REFRESHING("Chilled & Iced", "🧊")
}

data class BrewSpecs(
  val beanDoseGrams: Float,
  val liquidYieldGrams: Float,
  val extractionTimeSec: Int,
  val waterTempC: Int,
  val milkTempC: Int?,
  val grindSize: String,
  val caffeineMg: Int,
  val caloriesKcal: Int,
  val tastingNotes: List<String>
)

data class ReceiptItem(
  val label: String,
  val quantity: String,
  val percentage: Int,
  val temperatureDesc: String
)

data class BaristaReceipt(
  val receiptNumber: String,
  val storeHeader: String = "✦ THE ARTISAN BEAN ✦",
  val storeSubHeader: String = "Specialty Espresso & Brew Lab",
  val orderTitle: String,
  val timestamp: String,
  val items: List<ReceiptItem>,
  val brewRatio: String,
  val prepSteps: List<String>,
  val baristaProTip: String,
  val barcode: String
)

data class CoffeeDrink(
  val id: String,
  val name: String,
  val nativeName: String,
  val origin: String,
  val category: CoffeeCategory,
  val tagLine: String,
  val description: String,
  val recommendedCup: CupType,
  val recommendedTotalMl: Int,
  val layers: List<BeverageLayer>,
  val specs: BrewSpecs,
  val receipt: BaristaReceipt
)
