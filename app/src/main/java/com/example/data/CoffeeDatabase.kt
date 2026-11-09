package com.example.data

import com.example.model.*

object CoffeeDatabase {

  val drinks: List<CoffeeDrink> = listOf(
    // 1. ESPRESSO
    CoffeeDrink(
      id = "espresso",
      name = "Espresso",
      nativeName = "Caffè Espresso",
      origin = "Milan, Italy (1906)",
      category = CoffeeCategory.CLASSIC_ESPRESSO,
      tagLine = "The pure heart & soul of specialty coffee",
      description = "Extracted under 9 bars of pressure. A concentrated shot with three distinct zones: the dark heart at the base, the warm body in the middle, and the dense golden-hazelnut crema on top.",
      recommendedCup = CupType.DEMITASSE,
      recommendedTotalMl = 30,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 30, "Single shot pulled at 9 bars (18g coffee in, 36g liquid out)")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Fine (250 µm)",
        caffeineMg = 64,
        caloriesKcal = 3,
        tastingNotes = listOf("Dark Chocolate", "Roasted Hazelnut", "Black Cherry")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0101",
        orderTitle = "Single Origin Solo Espresso",
        timestamp = "08:15 AM - Table 01",
        items = listOf(
          ReceiptItem("Espresso Shot (9 Bar)", "30 ml (1.0 oz)", 100, "93°C extraction")
        ),
        brewRatio = "1:2 Ratio (18g ground in / 36g espresso out in 27s)",
        prepSteps = listOf(
          "Purge grouphead & wipe dry portafilter basket.",
          "Dose 18g fine-ground specialty coffee & distribute evenly (WDT).",
          "Tamp level with 15kg firm pressure.",
          "Lock into group head & extract immediately to 36g yield (27s)."
        ),
        baristaProTip = "Stir with a demitasse spoon before sipping to blend the acidic crema with the dense sweet body at the bottom.",
        barcode = "||| || |||| | |||||| | |||"
      )
    ),

    // 2. RISTRETTO
    CoffeeDrink(
      id = "ristretto",
      name = "Ristretto",
      nativeName = "Caffè Ristretto",
      origin = "Italy",
      category = CoffeeCategory.CLASSIC_ESPRESSO,
      tagLine = "Restricted extraction: sweeter, syrupy, less bitter",
      description = "A 'restricted' shot using the same coffee dose but cut off at half the liquid volume. Only the fastest-extracting sugars and aromatic oils are captured, leaving behind bitter tannins.",
      recommendedCup = CupType.DEMITASSE,
      recommendedTotalMl = 20,
      layers = listOf(
        BeverageLayer(IngredientType.RISTRETTO, 20, "Short extraction stopping right after the first blonde stream")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 20.0f,
        extractionTimeSec = 18,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Very Fine (230 µm)",
        caffeineMg = 50,
        caloriesKcal = 2,
        tastingNotes = listOf("Brown Sugar", "Candied Plum", "Syrupy Cocoa")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0102",
        orderTitle = "Double Ristretto Short Shot",
        timestamp = "08:24 AM - Counter Bar",
        items = listOf(
          ReceiptItem("Ristretto Extraction", "20 ml (0.7 oz)", 100, "93°C short cut")
        ),
        brewRatio = "1:1 Ratio (18g coffee dose / 20g liquid yield in 18s)",
        prepSteps = listOf(
          "Grind slightly finer than standard espresso.",
          "Dose 18g and level carefully.",
          "Start extraction and stop when flow turns pale amber (approx. 18 seconds)."
        ),
        baristaProTip = "Ristrettos are the secret to modern Australian flat whites—they pack intense sweetness without drying bitterness.",
        barcode = "|| ||| | ||| |||| | ||||"
      )
    ),

    // 3. ESPRESSO MACCHIATO
    CoffeeDrink(
      id = "macchiato",
      name = "Espresso Macchiato",
      nativeName = "Caffè Macchiato",
      origin = "Italy",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "'Marked' or 'stained' with a cloud of milk foam",
      description = "A bold shot of espresso 'stained' (macchiato) with a small spoonful of velvety milk foam. Cuts the espresso's sharp edge while preserving its raw power.",
      recommendedCup = CupType.DEMITASSE,
      recommendedTotalMl = 45,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 30, "Base double or single espresso shot"),
        BeverageLayer(IngredientType.AIRY_FOAM, 15, "Dollop of warm milk froth on top")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 65,
        grindSize = "Fine (250 µm)",
        caffeineMg = 64,
        caloriesKcal = 12,
        tastingNotes = listOf("Intense Espresso", "Caramelized Crema", "Sweet Milk Foam")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0103",
        orderTitle = "Traditional Macchiato Caldo",
        timestamp = "08:42 AM - Dine In",
        items = listOf(
          ReceiptItem("Espresso Shot Base", "30 ml (1.0 oz)", 67, "93°C"),
          ReceiptItem("Velvety Milk Foam Staining", "15 ml (0.5 oz)", 33, "65°C froth")
        ),
        brewRatio = "2:1 Espresso to Foam volume",
        prepSteps = listOf(
          "Pull a single or double espresso directly into a warm demitasse.",
          "Steam a small pitcher of whole milk to 65°C with rich microfoam.",
          "Use a bar spoon to gently scoop 1-2 dollops of dense foam onto the crema center."
        ),
        baristaProTip = "Don't confuse Italian Caffè Macchiato with Caramel Macchiato dessert drinks. Traditional macchiato is 80% pure espresso with just a foam mark!",
        barcode = "| |||| | || ||| |||| || |"
      )
    ),

    // 4. CORTADO
    CoffeeDrink(
      id = "cortado",
      name = "Cortado",
      nativeName = "Café Cortado",
      origin = "Basque Country / Madrid, Spain",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "Equal parts 1:1 espresso and steamed milk",
      description = "'Cortar' means to cut. Equal parts espresso and lightly steamed milk (no heavy froth). The warm milk cuts the acidity of the coffee while celebrating its origin notes in a small Gibraltar glass.",
      recommendedCup = CupType.GIBRALTAR,
      recommendedTotalMl = 120,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of espresso (1:2 ratio)"),
        BeverageLayer(IngredientType.STEAMED_MILK, 60, "Silky warm steamed milk (minimal foam)")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 60,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 42,
        tastingNotes = listOf("Toffee", "Smooth Cocoa", "Roasted Walnut")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0104",
        orderTitle = "Artisan Gibraltar Cortado",
        timestamp = "09:05 AM - Table 04",
        items = listOf(
          ReceiptItem("Double Espresso (2x)", "60 ml (2.0 oz)", 50, "93°C double shot"),
          ReceiptItem("Warm Steamed Whole Milk", "60 ml (2.0 oz)", 50, "60°C textured milk")
        ),
        brewRatio = "1:1 Exact Equal Ratio (Coffee to Milk)",
        prepSteps = listOf(
          "Pull a double espresso shot directly into a tempered 4.5oz Gibraltar glass.",
          "Steam whole milk gently with little aeration, heating to 60°C.",
          "Pour equal volume of warm milk into the espresso with a flat, thin surface film."
        ),
        baristaProTip = "Serve cooler (60°C) than a latte so it can be enjoyed immediately in two or three decadent sips.",
        barcode = "||| || | |||| || | |||| |"
      )
    ),

    // 5. FLAT WHITE
    CoffeeDrink(
      id = "flat_white",
      name = "Flat White",
      nativeName = "Flat White",
      origin = "Sydney & Melbourne, Australia / Wellington, NZ (1980s)",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "Double ristretto with glossy, integrated microfoam",
      description = "Australia and New Zealand's gift to the world. A double shot (often double ristretto) blended with microfoam—milk steamed so finely that bubbles are invisible, creating a glossy 'wet paint' texture from top to bottom.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 160,
      layers = listOf(
        BeverageLayer(IngredientType.RISTRETTO, 40, "Double ristretto base (intense sweetness)"),
        BeverageLayer(IngredientType.STEAMED_MILK, 90, "Velvety steamed whole milk"),
        BeverageLayer(IngredientType.MICROFOAM, 30, "Ultra-thin glossy microfoam cap with latte art")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 19.0f,
        liquidYieldGrams = 38.0f,
        extractionTimeSec = 24,
        waterTempC = 93,
        milkTempC = 62,
        grindSize = "Fine (240 µm)",
        caffeineMg = 130,
        caloriesKcal = 85,
        tastingNotes = listOf("Creamy Caramel", "Sweet Molasses", "Smooth Malt")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0105",
        orderTitle = "Melbourne Style Flat White",
        timestamp = "09:20 AM - Bar Stool 02",
        items = listOf(
          ReceiptItem("Double Ristretto / Espresso", "40 ml (1.4 oz)", 25, "93°C extraction"),
          ReceiptItem("Steamed Silk Milk", "90 ml (3.0 oz)", 56, "62°C gentle steam"),
          ReceiptItem("Microfoam Cap (Late Art)", "30 ml (1.0 oz)", 19, "Micro-bubbles")
        ),
        brewRatio = "1:3 Coffee to Microfoam Ratio",
        prepSteps = listOf(
          "Extract double ristretto into a 160ml ceramic tulip cup.",
          "Steam milk with vortex roll to break down all air bubbles into sub-millimeter microfoam.",
          "Swirl pitcher until milk looks like glossy white paint.",
          "Pour smoothly from height to incorporate, dropping tip close to lay a rosetta or tulip pattern."
        ),
        baristaProTip = "A true Flat White has a flat, glossy top (hence the name), distinct from the dome of froth on a cappuccino.",
        barcode = "|| |||| | ||| || |||| | |"
      )
    ),

    // 6. CAPPUCCINO
    CoffeeDrink(
      id = "cappuccino",
      name = "Cappuccino",
      nativeName = "Cappuccino Tradizionale",
      origin = "Vienna & Northern Italy (1930s)",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "The classic rule of thirds: 1/3 espresso, 1/3 milk, 1/3 foam",
      description = "Named after the brown robes of Capuchin monks. A harmonious tripartite balance of rich espresso, sweet steamed milk, and a luxurious dome of airy foam dusted with Dutch cocoa.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 180,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of espresso base"),
        BeverageLayer(IngredientType.STEAMED_MILK, 60, "Hot textured milk"),
        BeverageLayer(IngredientType.AIRY_FOAM, 58, "Thick pillow of velvety foam"),
        BeverageLayer(IngredientType.COCOA_DUST, 2, "A dusting of Dutch cocoa powder")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 28,
        waterTempC = 93,
        milkTempC = 65,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 95,
        tastingNotes = listOf("Melted Marshmallow", "Dark Chocolate", "Hazelnut Praline")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0106",
        orderTitle = "Traditional 1:1:1 Cappuccino",
        timestamp = "09:45 AM - Dine In",
        items = listOf(
          ReceiptItem("Double Espresso Base", "60 ml (2.0 oz)", 33, "93°C extraction"),
          ReceiptItem("Steamed Whole Milk", "60 ml (2.0 oz)", 33, "65°C"),
          ReceiptItem("Dense Aerated Foam", "58 ml (1.9 oz)", 33, "65°C thick foam"),
          ReceiptItem("Dutch Cocoa Powder", "2 g (pinch)", 1, "Top dusting")
        ),
        brewRatio = "1:1:1 Equal Thirds Rule (Espresso : Milk : Foam)",
        prepSteps = listOf(
          "Pull double espresso into a preheated 180ml rounded ceramic cup.",
          "Aerate milk for 5-6 seconds to introduce ample air, creating rich foam depth.",
          "Pour milk into center, allowing foam to billow up and form a white dome ringed by brown crema.",
          "Dust lightly with dark unsweetened cocoa powder."
        ),
        baristaProTip = "When you tap the side of a proper cappuccino cup, the foam dome should gently wobble like a delicate soufflé.",
        barcode = "|||| | || |||| | || |||||"
      )
    ),

    // 7. CAFFÈ LATTE
    CoffeeDrink(
      id = "latte",
      name = "Caffè Latte",
      nativeName = "Caffè Latte",
      origin = "Italy / popularized worldwide in America",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "Espresso enveloped in plenty of sweet steamed milk",
      description = "The gentlest and most comforting coffee drink. A single or double espresso drowned in 3x its volume of steamed milk, crowned with a thin 1cm layer of microfoam for latte art.",
      recommendedCup = CupType.TALL_GLASS,
      recommendedTotalMl = 240,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of rich espresso"),
        BeverageLayer(IngredientType.STEAMED_MILK, 150, "Abundant steamed whole milk"),
        BeverageLayer(IngredientType.MICROFOAM, 30, "Thin silky microfoam cap (1cm)")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 65,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 150,
        tastingNotes = listOf("Vanilla Cream", "Honey Biscuit", "Soft Mocha")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0107",
        orderTitle = "Grand Caffè Latte",
        timestamp = "10:10 AM - Takeout",
        items = listOf(
          ReceiptItem("Double Espresso Shot", "60 ml (2.0 oz)", 25, "93°C"),
          ReceiptItem("Steamed Whole Milk", "150 ml (5.0 oz)", 63, "65°C silky"),
          ReceiptItem("Microfoam Surface Cap", "30 ml (1.0 oz)", 12, "1cm foam layer")
        ),
        brewRatio = "1:4 Ratio (Espresso to Steamed Milk)",
        prepSteps = listOf(
          "Pull double espresso into base of a tall glass or 8oz cup.",
          "Steam whole milk to 65°C, aerating for just 2-3 seconds for minimal foam.",
          "Pour steadily from height to mix thoroughly with the espresso.",
          "Finish with a swan, tulip, or rosetta pour on top."
        ),
        baristaProTip = "In Italy, ordering just a 'latte' will literally get you a glass of plain cold milk! Always specify 'Caffè Latte'.",
        barcode = "||| | ||| |||| | ||| || |"
      )
    ),

    // 8. CAFFÈ MOCHA
    CoffeeDrink(
      id = "mocha",
      name = "Caffè Mocha",
      nativeName = "Mocaccino",
      origin = "Turin, Italy / Seattle, USA",
      category = CoffeeCategory.SWEET_SPECIALTY,
      tagLine = "Espresso meets bittersweet chocolate and whipped cream",
      description = "Descended from the 18th-century Turin delicacy 'Bicerin'. Bittersweet chocolate syrup melted into hot espresso, blended with steamed milk, and crowned with fresh whipped cream.",
      recommendedCup = CupType.TALL_GLASS,
      recommendedTotalMl = 250,
      layers = listOf(
        BeverageLayer(IngredientType.CHOCOLATE_SYRUP, 30, "Gourmet dark cocoa mocha syrup at base"),
        BeverageLayer(IngredientType.ESPRESSO, 60, "Hot double espresso shot dissolving syrup"),
        BeverageLayer(IngredientType.STEAMED_MILK, 120, "Steamed warm whole milk"),
        BeverageLayer(IngredientType.WHIPPED_CREAM, 38, "Swirled peak of vanilla whipped cream"),
        BeverageLayer(IngredientType.COCOA_DUST, 2, "Shaved chocolate or cocoa dusting")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 65,
        grindSize = "Fine (250 µm)",
        caffeineMg = 135,
        caloriesKcal = 290,
        tastingNotes = listOf("Dark Fudge", "Roasted Cocoa Nibs", "Creamy Truffle")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0108",
        orderTitle = "Belgian Chocolate Caffè Mocha",
        timestamp = "10:35 AM - Table 08",
        items = listOf(
          ReceiptItem("Artisan Dark Cocoa Syrup", "30 ml (1.0 oz)", 12, "Base layer"),
          ReceiptItem("Double Espresso Shot", "60 ml (2.0 oz)", 24, "93°C hot pull"),
          ReceiptItem("Steamed Whole Milk", "120 ml (4.0 oz)", 48, "65°C textured"),
          ReceiptItem("Cold Vanilla Whipped Cream", "38 ml (1.3 oz)", 15, "Swirl top"),
          ReceiptItem("Dark Cocoa Dusting", "2 g", 1, "Finishing spice")
        ),
        brewRatio = "1:2:4 (Syrup : Espresso : Steamed Milk)",
        prepSteps = listOf(
          "Pump 30ml rich chocolate sauce into bottom of the glass.",
          "Extract double espresso directly onto the chocolate and stir vigorously until emulsified.",
          "Pour steamed milk into chocolate-espresso mixture.",
          "Garnish with a spiraled rosette of fresh whipped cream and cocoa dust."
        ),
        baristaProTip = "Using dark cocoa (70%+) instead of sugary syrup keeps the coffee flavor forward without turning it cloying.",
        barcode = "|| |||| || | ||| |||| | |"
      )
    ),

    // 9. AMERICANO
    CoffeeDrink(
      id = "americano",
      name = "Caffè Americano",
      nativeName = "Caffè Americano",
      origin = "Italy (WWII era)",
      category = CoffeeCategory.CLASSIC_ESPRESSO,
      tagLine = "Espresso diluted with hot water to mimic drip coffee",
      description = "Legend holds that American soldiers stationed in Italy during World War II found traditional espresso too strong, so Italian baristas added hot water to resemble filter coffee from home.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 180,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of rich espresso"),
        BeverageLayer(IngredientType.HOT_WATER, 120, "Filtered hot water at 92°C")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 5,
        tastingNotes = listOf("Toasted Grain", "Subtle Lemon", "Dark Plum")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0109",
        orderTitle = "Double Shot Caffè Americano",
        timestamp = "11:00 AM - Counter",
        items = listOf(
          ReceiptItem("Double Espresso Shot", "60 ml (2.0 oz)", 33, "93°C extraction"),
          ReceiptItem("Purified Hot Water", "120 ml (4.0 oz)", 67, "92°C dilution")
        ),
        brewRatio = "1:2 Espresso to Water ratio",
        prepSteps = listOf(
          "Pull a double espresso shot into a cup.",
          "Add 120ml hot water from the kettle or espresso tap.",
          "The crema breaks apart and diffuses into the liquid, giving a clean, lighter profile."
        ),
        baristaProTip = "Americano adds hot water TO espresso. If you do it in reverse (espresso poured into water), you make a Long Black, which preserves the crema!",
        barcode = "||| |||| | ||| | ||||| |"
      )
    ),

    // 10. LONG BLACK
    CoffeeDrink(
      id = "long_black",
      name = "Long Black",
      nativeName = "Long Black",
      origin = "Australia & New Zealand",
      category = CoffeeCategory.CLASSIC_ESPRESSO,
      tagLine = "Hot water first, espresso on top to preserve crema",
      description = "The Australasian counterpart to the Americano. Hot water is placed in the cup first, and a double espresso (or double ristretto) is pulled directly on top, preserving a thick blanket of crema and intense aromatics.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 160,
      layers = listOf(
        BeverageLayer(IngredientType.HOT_WATER, 100, "Hot water at base (85°C)"),
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double espresso floated gently on top, keeping crema intact")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 19.0f,
        liquidYieldGrams = 38.0f,
        extractionTimeSec = 26,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Fine (240 µm)",
        caffeineMg = 130,
        caloriesKcal = 5,
        tastingNotes = listOf("Intense Crema", "Sweet Cedar", "Dark Cocoa")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0110",
        orderTitle = "Crema-Preserved Long Black",
        timestamp = "11:15 AM - Table 03",
        items = listOf(
          ReceiptItem("Hot Water Base", "100 ml (3.3 oz)", 62, "85°C foundation"),
          ReceiptItem("Double Espresso Float", "60 ml (2.0 oz)", 38, "93°C crema intact")
        ),
        brewRatio = "2:3 Espresso to Hot Water",
        prepSteps = listOf(
          "Fill preheated ceramic cup with 100ml hot water first.",
          "Position cup under the double spout of portafilter.",
          "Extract double espresso directly onto surface of the water so crema stays undisturbed."
        ),
        baristaProTip = "Because the crema isn't drowned by pouring water, your first sip delivers the full burst of coffee oils and aroma.",
        barcode = "|| ||| |||| | || |||| | |"
      )
    ),

    // 11. BREVE
    CoffeeDrink(
      id = "breve",
      name = "Caffè Breve",
      nativeName = "Breve / Café Breve",
      origin = "United States",
      category = CoffeeCategory.MILK_AND_FOAM,
      tagLine = "Espresso with steamed half-and-half cream",
      description = "The richest milk-based coffee in existence. Swaps standard milk for steamed half-and-half (equal parts whole milk and light cream), yielding an extraordinarily luscious, dessert-like texture.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 180,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double espresso shot"),
        BeverageLayer(IngredientType.HALF_AND_HALF, 90, "Steamed warm half-and-half cream"),
        BeverageLayer(IngredientType.AIRY_FOAM, 30, "Creamy dense froth cap")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 60,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 240,
        tastingNotes = listOf("Heavy Cream", "Custard", "Butterscotch")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0111",
        orderTitle = "Rich Breve Cappuccino",
        timestamp = "11:40 AM - Counter",
        items = listOf(
          ReceiptItem("Double Espresso Shot", "60 ml (2.0 oz)", 33, "93°C extraction"),
          ReceiptItem("Steamed Half & Half", "90 ml (3.0 oz)", 50, "60°C thick cream"),
          ReceiptItem("Breve Microfoam", "30 ml (1.0 oz)", 17, "Dense crown")
        ),
        brewRatio = "1:2 Espresso to Steamed Cream",
        prepSteps = listOf(
          "Extract double espresso into a 6oz cup.",
          "Steam half-and-half gently—it thickens and expands faster than regular milk due to high fat.",
          "Pour smoothly into the coffee, creating a decadently thick beverage."
        ),
        baristaProTip = "Steam half-and-half to only 60°C maximum to avoid curdling or heavy cooked butter aromas.",
        barcode = "|||| | ||| || | |||| || |"
      )
    ),

    // 12. AFFOGATO
    CoffeeDrink(
      id = "affogato",
      name = "Affogato al Caffè",
      nativeName = "Affogato al Caffè",
      origin = "Italy",
      category = CoffeeCategory.SWEET_SPECIALTY,
      tagLine = "Vanilla gelato drowned in hot espresso",
      description = "'Affogato' means 'drowned'. A dessert and beverage in one: a generous scoop of artisanal vanilla bean gelato drowned in a freshly pulled shot of piping hot espresso. The hot-cold contrast is legendary.",
      recommendedCup = CupType.GIBRALTAR,
      recommendedTotalMl = 90,
      layers = listOf(
        BeverageLayer(IngredientType.ICE_CREAM_GELATO, 50, "Chilled dense scoop of vanilla bean gelato"),
        BeverageLayer(IngredientType.ESPRESSO, 40, "Piping hot double espresso poured directly over gelato")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 26,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Fine (250 µm)",
        caffeineMg = 85,
        caloriesKcal = 165,
        tastingNotes = listOf("Hot Espresso / Cold Gelato", "Vanilla Bean", "Melted Caramel")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0112",
        orderTitle = "Classico Affogato al Caffè",
        timestamp = "01:20 PM - Dessert Bar",
        items = listOf(
          ReceiptItem("Vanilla Bean Gelato", "50 ml (1 scoop)", 55, "-10°C chilled scoop"),
          ReceiptItem("Hot Double Espresso Shot", "40 ml (1.3 oz)", 45, "93°C hot drown")
        ),
        brewRatio = "1 Scoop Gelato to 1 Double Espresso",
        prepSteps = listOf(
          "Place a round scoop of firm artisan vanilla gelato into a chilled glass.",
          "Pull a hot double shot of espresso into a small pouring pitcher.",
          "Immediately pour the hot espresso over the crown of the gelato and serve with a dessert spoon."
        ),
        baristaProTip = "Chill the glass in the freezer beforehand so the gelato doesn't melt from the bottom before the hot espresso is poured.",
        barcode = "||| || | |||| | ||| |||||"
      )
    ),

    // 13. ESPRESSO TONIC
    CoffeeDrink(
      id = "espresso_tonic",
      name = "Espresso Tonic",
      nativeName = "Kaffetonic",
      origin = "Helsingborg, Sweden (2007)",
      category = CoffeeCategory.ICED_REFRESHING,
      tagLine = "Sparkling citrus tonic topped with floating espresso",
      description = "Invented by Swedish specialty baristas. Chilled effervescent tonic water over crystal ice cubes, crowned by an espresso shot poured gently over a spoon to float on top. Sparkling, crisp, and thirst-quenching.",
      recommendedCup = CupType.ICED_TUMBLER,
      recommendedTotalMl = 260,
      layers = listOf(
        BeverageLayer(IngredientType.ICE_CUBES, 60, "Clear dense cocktail ice cubes"),
        BeverageLayer(IngredientType.TONIC_WATER, 140, "Crisp botanical tonic water"),
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of bright espresso floated on top")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 26,
        waterTempC = 93,
        milkTempC = null,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 55,
        tastingNotes = listOf("Citrus Zest", "Sparkling Quinine", "Bright Stone Fruit")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0113",
        orderTitle = "Nordic Sparkling Espresso Tonic",
        timestamp = "02:15 PM - Patio",
        items = listOf(
          ReceiptItem("Artisan Clear Ice", "60 ml equiv.", 23, "0°C chilling"),
          ReceiptItem("Botanical Tonic Water", "140 ml (4.7 oz)", 54, "4°C bubbly base"),
          ReceiptItem("Floated Double Espresso", "60 ml (2.0 oz)", 23, "Fresh pull float")
        ),
        brewRatio = "1:2.5 Espresso to Tonic Water",
        prepSteps = listOf(
          "Fill ribbed iced tumbler with clear ice cubes.",
          "Slowly pour 140ml chilled tonic down the inside of the glass to maintain carbonation.",
          "Pull a double espresso into a mini beaker.",
          "Gently pour espresso over an inverted bar spoon to float neatly on top without fizzing over."
        ),
        baristaProTip = "Naturally processed Ethiopian coffees shine best here—their floral blueberry notes mingle gorgeously with the tonic.",
        barcode = "|| |||| | ||| |||| | ||||"
      )
    ),

    // 14. ICED LATTE
    CoffeeDrink(
      id = "iced_latte",
      name = "Iced Caffè Latte",
      nativeName = "Iced Latte",
      origin = "Global specialty cafes",
      category = CoffeeCategory.ICED_REFRESHING,
      tagLine = "Crisp cold milk over ice with espresso marbling",
      description = "Refreshing and smooth. Ice cubes, chilled whole milk, and a double espresso poured over, creating beautiful marbled streaks of espresso cascading through the cold milk.",
      recommendedCup = CupType.ICED_TUMBLER,
      recommendedTotalMl = 280,
      layers = listOf(
        BeverageLayer(IngredientType.ICE_CUBES, 70, "Crystal ice cubes"),
        BeverageLayer(IngredientType.STEAMED_MILK, 150, "Cold organic whole milk"),
        BeverageLayer(IngredientType.ESPRESSO, 60, "Hot double espresso poured over, slowly cascading")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 4,
        grindSize = "Fine (250 µm)",
        caffeineMg = 128,
        caloriesKcal = 120,
        tastingNotes = listOf("Sweet Cream", "Cold Cocoa", "Roasted Praline")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0114",
        orderTitle = "Marbled Iced Caffè Latte",
        timestamp = "02:40 PM - To Go",
        items = listOf(
          ReceiptItem("Crystal Clear Ice", "70 ml equiv.", 25, "0°C cubes"),
          ReceiptItem("Cold Whole Milk", "150 ml (5.0 oz)", 54, "4°C chilled"),
          ReceiptItem("Double Espresso Pour", "60 ml (2.0 oz)", 21, "Fresh pull cascade")
        ),
        brewRatio = "1:2.5 Espresso to Cold Milk",
        prepSteps = listOf(
          "Pack glass 3/4 full with solid ice cubes.",
          "Pour cold milk over the ice.",
          "Pull double espresso and immediately pour gently over the ice to achieve dramatic marbled banding."
        ),
        baristaProTip = "Serve unstirred so the guest can admire the visual gradient before taking their first swirl.",
        barcode = "||| | |||| | || | |||| ||"
      )
    ),

    // 15. VIENNA COFFEE
    CoffeeDrink(
      id = "vienna",
      name = "Vienna Coffee",
      nativeName = "Einspänner",
      origin = "Vienna, Austria (19th century)",
      category = CoffeeCategory.SWEET_SPECIALTY,
      tagLine = "Double espresso under a thick cap of cold whipped cream",
      description = "Historically consumed by Viennese carriage drivers (Einspänner) who needed the thick whipped cream layer to keep the hot coffee insulated while holding the horses' reins in the cold.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 140,
      layers = listOf(
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double shot of bold espresso"),
        BeverageLayer(IngredientType.HOT_WATER, 30, "Splash of hot water to lengthen"),
        BeverageLayer(IngredientType.WHIPPED_CREAM, 48, "Chilled, thick whipped cream crown"),
        BeverageLayer(IngredientType.COCOA_DUST, 2, "Cocoa dusting or chocolate shavings")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 18.0f,
        liquidYieldGrams = 36.0f,
        extractionTimeSec = 27,
        waterTempC = 93,
        milkTempC = 4,
        grindSize = "Fine (250 µm)",
        caffeineMg = 130,
        caloriesKcal = 160,
        tastingNotes = listOf("Hot Coffee through Cold Cream", "Bittersweet Cacao", "Vanilla")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0115",
        orderTitle = "Viennese Einspänner Coffee",
        timestamp = "03:10 PM - Saloon",
        items = listOf(
          ReceiptItem("Double Espresso Base", "60 ml (2.0 oz)", 43, "93°C extraction"),
          ReceiptItem("Hot Water Dash", "30 ml (1.0 oz)", 21, "92°C lengthener"),
          ReceiptItem("Cold Whipped Cream Cap", "48 ml (1.6 oz)", 34, "4°C thick cream"),
          ReceiptItem("Chocolate Shavings", "2 g", 2, "Garnish")
        ),
        brewRatio = "2:1 Coffee to Whipped Cream",
        prepSteps = listOf(
          "Pull double espresso into a glass mug and add 30ml hot water.",
          "Whip cold heavy cream with a touch of powdered sugar until soft peaks form.",
          "Pipe or spoon cream liberally over top to form a tall protective layer.",
          "Garnish with dark cocoa dust."
        ),
        baristaProTip = "Do NOT stir! Drink the piping hot espresso directly through the icy cold, sweet cream blanket.",
        barcode = "|||| || | ||| |||| | || |"
      )
    ),

    // 16. RED EYE
    CoffeeDrink(
      id = "red_eye",
      name = "Red Eye",
      nativeName = "Red Eye / Shot in the Dark",
      origin = "North America",
      category = CoffeeCategory.CLASSIC_ESPRESSO,
      tagLine = "Fresh filter drip coffee boosted with an espresso shot",
      description = "Named for the overnight 'red-eye' flights where heavy caffeine is non-negotiable. A mug of fresh batch drip coffee topped with an espresso shot to skyrocket caffeine and punchiness.",
      recommendedCup = CupType.CERAMIC_CUP,
      recommendedTotalMl = 200,
      layers = listOf(
        BeverageLayer(IngredientType.DRIP_COFFEE, 140, "Fresh batch brewed filter drip coffee"),
        BeverageLayer(IngredientType.ESPRESSO, 60, "Double espresso shot pulled straight in")
      ),
      specs = BrewSpecs(
        beanDoseGrams = 28.0f,
        liquidYieldGrams = 200.0f,
        extractionTimeSec = 30,
        waterTempC = 92,
        milkTempC = null,
        grindSize = "Medium + Fine",
        caffeineMg = 220,
        caloriesKcal = 5,
        tastingNotes = listOf("Maximum Caffeine", "Smoky Oak", "Heavy Body")
      ),
      receipt = BaristaReceipt(
        receiptNumber = "TKT-0116",
        orderTitle = "High-Voltage Red Eye Brew",
        timestamp = "04:00 PM - To Go",
        items = listOf(
          ReceiptItem("Fresh Drip Filter Coffee", "140 ml (4.7 oz)", 70, "88°C batch brew"),
          ReceiptItem("Double Espresso Boost", "60 ml (2.0 oz)", 30, "93°C turbo pull")
        ),
        brewRatio = "Double Espresso dropped into 5oz Drip Brew",
        prepSteps = listOf(
          "Fill mug 70% with piping hot fresh filter drip coffee.",
          "Pull a double espresso into shot glass.",
          "Pour shot directly into the drip brew and serve immediately."
        ),
        baristaProTip = "Also known as a 'Black Eye' when made with 2 shots, or a 'Dead Eye' when made with 3 shots!",
        barcode = "|| |||| | || |||| ||| | |"
      )
    )
  )

  fun findDrinkById(id: String): CoffeeDrink? = drinks.find { it.id == id }

  fun evaluateCreatedDrink(layers: List<BeverageLayer>, cupType: CupType): DrinkEvaluation {
    if (layers.isEmpty()) {
      return DrinkEvaluation(
        matchedDrink = null,
        accuracyScore = 0,
        feedback = "The cup is empty! Choose a cup and start pulling shots or steaming milk.",
        identifiedTitle = "Empty Cup"
      )
    }

    val totalVolume = layers.sumOf { it.volumeMl }
    val ingredientVolumes = mutableMapOf<IngredientType, Int>()
    for (layer in layers) {
      ingredientVolumes[layer.type] = (ingredientVolumes[layer.type] ?: 0) + layer.volumeMl
    }

    var bestMatch: CoffeeDrink? = null
    var highestScore = 0

    for (targetDrink in drinks) {
      val targetTotal = targetDrink.recommendedTotalMl
      val targetVolumes = mutableMapOf<IngredientType, Int>()
      for (l in targetDrink.layers) {
        targetVolumes[l.type] = (targetVolumes[l.type] ?: 0) + l.volumeMl
      }

      // Check ingredient overlap
      var score = 100
      val allTypes = (ingredientVolumes.keys + targetVolumes.keys).toSet()

      for (type in allTypes) {
        val userPct = ((ingredientVolumes[type] ?: 0).toFloat() / totalVolume.coerceAtLeast(1)) * 100f
        val targetPct = ((targetVolumes[type] ?: 0).toFloat() / targetTotal.coerceAtLeast(1)) * 100f
        val diff = Math.abs(userPct - targetPct)
        score -= (diff * 0.7f).toInt()
      }

      // Volume penalty if way off
      val volDiffRatio = Math.abs(totalVolume - targetTotal).toFloat() / targetTotal.toFloat()
      if (volDiffRatio > 0.3f) {
        score -= ((volDiffRatio - 0.3f) * 40f).toInt()
      }

      score = score.coerceIn(0, 100)
      if (score > highestScore) {
        highestScore = score
        bestMatch = targetDrink
      }
    }

    val feedback = when {
      highestScore >= 90 -> "Outstanding Barista Craft! An authentic, textbook ${bestMatch?.name}."
      highestScore >= 75 -> "Great job! Very close to a classic ${bestMatch?.name}. Slight proportion adjustments will make it perfect."
      highestScore >= 50 -> "Decent brew! Has qualities of a ${bestMatch?.name}, but ratios or missing ingredients differ."
      else -> "Creative concoction! Your custom drink has bold character, closest to a ${bestMatch?.name ?: "House Blend"}."
    }

    return DrinkEvaluation(
      matchedDrink = bestMatch,
      accuracyScore = highestScore,
      feedback = feedback,
      identifiedTitle = if (highestScore >= 80) (bestMatch?.name ?: "Custom Brew") else "Artisan Blend (closest: ${bestMatch?.name})"
    )
  }
}

data class DrinkEvaluation(
  val matchedDrink: CoffeeDrink?,
  val accuracyScore: Int,
  val feedback: String,
  val identifiedTitle: String
)
