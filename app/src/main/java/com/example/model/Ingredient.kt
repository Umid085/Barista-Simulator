package com.example.model

import androidx.compose.ui.graphics.Color

enum class IngredientCategory {
  COFFEE,
  MILK,
  WATER,
  SWEETENER,
  TOPPING,
  SPECIAL
}

enum class IngredientType(
  val id: String,
  val displayName: String,
  val shortName: String,
  val category: IngredientCategory,
  val primaryColor: Color,
  val accentColor: Color,
  val defaultVolumeMl: Int,
  val density: Float, // for layer settling physics (higher = sinks, lower = floats)
  val typicalTempC: Int,
  val description: String,
  val iconEmoji: String
) {
  CHOCOLATE_SYRUP(
    id = "chocolate_syrup",
    displayName = "Chocolate / Mocha Syrup",
    shortName = "Mocha",
    category = IngredientCategory.SWEETENER,
    primaryColor = Color(0xFF381A08),
    accentColor = Color(0xFF4E260E),
    defaultVolumeMl = 20,
    density = 1.35f,
    typicalTempC = 20,
    description = "Rich, decadent cocoa syrup that settles at the base of the cup.",
    iconEmoji = "🍫"
  ),
  CARAMEL_DRIZZLE(
    id = "caramel_drizzle",
    displayName = "Caramel Syrup",
    shortName = "Caramel",
    category = IngredientCategory.SWEETENER,
    primaryColor = Color(0xFFC77826),
    accentColor = Color(0xFFE59846),
    defaultVolumeMl = 15,
    density = 1.30f,
    typicalTempC = 20,
    description = "Golden buttery caramel syrup for sweet toasted depth.",
    iconEmoji = "🍯"
  ),
  VANILLA_SYRUP(
    id = "vanilla_syrup",
    displayName = "Vanilla Syrup",
    shortName = "Vanilla",
    category = IngredientCategory.SWEETENER,
    primaryColor = Color(0xFFE8D399),
    accentColor = Color(0xFFF3E5AB),
    defaultVolumeMl = 15,
    density = 1.25f,
    typicalTempC = 20,
    description = "Sweet Madagascar bourbon vanilla infusion.",
    iconEmoji = "🌼"
  ),
  ICE_CREAM_GELATO(
    id = "gelato_scoop",
    displayName = "Vanilla Gelato Scoop",
    shortName = "Gelato",
    category = IngredientCategory.SPECIAL,
    primaryColor = Color(0xFFFFF7D6),
    accentColor = Color(0xFFFFECC0),
    defaultVolumeMl = 50,
    density = 0.92f,
    typicalTempC = -10,
    description = "Dense, creamy artisan vanilla bean gelato waiting to be 'drowned' in espresso.",
    iconEmoji = "🍨"
  ),
  ICE_CUBES(
    id = "ice_cubes",
    displayName = "Crystal Ice Cubes",
    shortName = "Ice",
    category = IngredientCategory.SPECIAL,
    primaryColor = Color(0xFFBCE6F8),
    accentColor = Color(0xFFE1F5FE),
    defaultVolumeMl = 40,
    density = 0.917f,
    typicalTempC = 0,
    description = "Dense clear ice cubes that chill the beverage and provide refreshing dilution.",
    iconEmoji = "🧊"
  ),
  TONIC_WATER(
    id = "tonic_water",
    displayName = "Chilled Tonic Water",
    shortName = "Tonic",
    category = IngredientCategory.SPECIAL,
    primaryColor = Color(0xFFD7F4F7),
    accentColor = Color(0xFFE8FCFD),
    defaultVolumeMl = 80,
    density = 1.03f,
    typicalTempC = 4,
    description = "Effervescent botanical tonic with bright citrus notes and effervescence.",
    iconEmoji = "🫧"
  ),
  HOT_WATER(
    id = "hot_water",
    displayName = "Filtered Hot Water",
    shortName = "Hot Water",
    category = IngredientCategory.WATER,
    primaryColor = Color(0xFFD6EEF8),
    accentColor = Color(0xFFEAF5FA),
    defaultVolumeMl = 60,
    density = 0.998f,
    typicalTempC = 92,
    description = "Purified hot water at 92°C to open up and elongate espresso notes.",
    iconEmoji = "💧"
  ),
  DRIP_COFFEE(
    id = "drip_coffee",
    displayName = "Batch Drip Brew",
    shortName = "Drip",
    category = IngredientCategory.COFFEE,
    primaryColor = Color(0xFF452B1A),
    accentColor = Color(0xFF5A3822),
    defaultVolumeMl = 90,
    density = 1.01f,
    typicalTempC = 88,
    description = "Slow filter drip brew with clean body and nuanced acidity.",
    iconEmoji = "☕"
  ),
  RISTRETTO(
    id = "ristretto",
    displayName = "Ristretto Shot (1:1)",
    shortName = "Ristretto",
    category = IngredientCategory.COFFEE,
    primaryColor = Color(0xFF24140B),
    accentColor = Color(0xFFC98A48), // Crema
    defaultVolumeMl = 20,
    density = 1.05f,
    typicalTempC = 93,
    description = "Short, highly concentrated espresso extraction with punchy sweet oils and minimal bitterness.",
    iconEmoji = "☕"
  ),
  ESPRESSO(
    id = "espresso",
    displayName = "Espresso Shot (1:2)",
    shortName = "Espresso",
    category = IngredientCategory.COFFEE,
    primaryColor = Color(0xFF331E12),
    accentColor = Color(0xFFD69A55), // Crema
    defaultVolumeMl = 30,
    density = 1.04f,
    typicalTempC = 93,
    description = "Intense 9-bar pressure extraction crowned with aromatic hazelnut crema.",
    iconEmoji = "☕"
  ),
  HALF_AND_HALF(
    id = "half_and_half",
    displayName = "Steamed Breve Cream (Half & Half)",
    shortName = "Breve Cream",
    category = IngredientCategory.MILK,
    primaryColor = Color(0xFFFFF6DF),
    accentColor = Color(0xFFFFFBEF),
    defaultVolumeMl = 60,
    density = 1.02f,
    typicalTempC = 62,
    description = "Equal parts whole milk and rich heavy cream for an ultra-velvety texture.",
    iconEmoji = "🥛"
  ),
  STEAMED_MILK(
    id = "steamed_milk",
    displayName = "Steamed Whole Milk",
    shortName = "Steamed Milk",
    category = IngredientCategory.MILK,
    primaryColor = Color(0xFFFBF7EE),
    accentColor = Color(0xFFFAF4E5),
    defaultVolumeMl = 60,
    density = 1.03f,
    typicalTempC = 62,
    description = "Silky textured milk heated to 62°C, bringing out natural milk sugars.",
    iconEmoji = "🥛"
  ),
  MICROFOAM(
    id = "microfoam",
    displayName = "Velvety Microfoam",
    shortName = "Microfoam",
    category = IngredientCategory.MILK,
    primaryColor = Color(0xFFFFFDF8),
    accentColor = Color(0xFFF9F5EC),
    defaultVolumeMl = 30,
    density = 0.65f,
    typicalTempC = 62,
    description = "Glossy, wet-paint consistency micro-bubbles perfect for intricate latte art.",
    iconEmoji = "🍶"
  ),
  AIRY_FOAM(
    id = "airy_foam",
    displayName = "Thick Airy Milk Foam",
    shortName = "Milk Foam",
    category = IngredientCategory.MILK,
    primaryColor = Color(0xFFFFFFFF),
    accentColor = Color(0xFFF5F3ED),
    defaultVolumeMl = 30,
    density = 0.40f,
    typicalTempC = 65,
    description = "Fluffy, cloud-like foam that sits pillow-soft on traditional cappuccinos.",
    iconEmoji = "☁️"
  ),
  WHIPPED_CREAM(
    id = "whipped_cream",
    displayName = "Vanilla Whipped Cream",
    shortName = "Whip",
    category = IngredientCategory.TOPPING,
    primaryColor = Color(0xFFFFFDF7),
    accentColor = Color(0xFFF8F4EA),
    defaultVolumeMl = 30,
    density = 0.35f,
    typicalTempC = 4,
    description = "Swirled peaks of fresh sweetened whipped cream.",
    iconEmoji = "🧁"
  ),
  COCOA_DUST(
    id = "cocoa_dust",
    displayName = "Dutch Cocoa Dusting",
    shortName = "Cocoa",
    category = IngredientCategory.TOPPING,
    primaryColor = Color(0xFF4A2A1A),
    accentColor = Color(0xFF633A24),
    defaultVolumeMl = 2,
    density = 0.20f,
    typicalTempC = 20,
    description = "Fine unsweetened dark cocoa powder sifted atop foam.",
    iconEmoji = "🍫"
  ),
  CINNAMON_SPICE(
    id = "cinnamon_spice",
    displayName = "Ceylon Cinnamon Dust",
    shortName = "Cinnamon",
    category = IngredientCategory.TOPPING,
    primaryColor = Color(0xFF8B4726),
    accentColor = Color(0xFFA55A35),
    defaultVolumeMl = 2,
    density = 0.20f,
    typicalTempC = 20,
    description = "Warm aromatic ground Ceylon cinnamon bark.",
    iconEmoji = "✨"
  )
}

data class BeverageLayer(
  val type: IngredientType,
  val volumeMl: Int,
  val customNote: String? = null
)
