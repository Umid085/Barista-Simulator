package com.example.data

data class QuizQuestion(
  val id: Int,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String,
  val relatedDrinkId: String? = null
)

object CoffeeQuizRepository {
  val questions: List<QuizQuestion> = listOf(
    QuizQuestion(
      id = 1,
      question = "What makes a Flat White distinctly different from a Caffè Latte?",
      options = listOf(
        "It uses chocolate powder and skim milk",
        "It uses double ristretto with seamless microfoam rather than thick froth",
        "It is always served cold over ice",
        "It has whipped cream instead of milk"
      ),
      correctIndex = 1,
      explanation = "A Flat White originated in Australia/NZ and features double espresso/ristretto blended with silky microfoam ('wet paint' texture) throughout, giving a punchier coffee taste than a milky latte.",
      relatedDrinkId = "flat_white"
    ),
    QuizQuestion(
      id = 2,
      question = "What is the key pouring difference between an Americano and a Long Black?",
      options = listOf(
        "Americano uses cold milk; Long Black uses hot cream",
        "Long Black puts hot water in first, pulling espresso on top to preserve crema",
        "Americano uses three shots; Long Black uses only one shot",
        "Long Black contains brown sugar and whiskey"
      ),
      correctIndex = 1,
      explanation = "In an Americano, hot water is poured over the espresso, breaking the crema. In a Long Black, hot water goes into the cup first, and espresso is pulled directly on top, keeping the aromatic golden crema intact!",
      relatedDrinkId = "long_black"
    ),
    QuizQuestion(
      id = 3,
      question = "What does the traditional 1:1:1 rule of a classic Cappuccino refer to?",
      options = listOf(
        "1 part espresso, 1 part sugar, 1 part chocolate",
        "1 scoop ice cream, 1 shot coffee, 1 glass milk",
        "Equal thirds: 1/3 espresso, 1/3 steamed milk, 1/3 aerated milk foam",
        "1 bean, 1 second extraction, 1 bar pressure"
      ),
      correctIndex = 2,
      explanation = "A classic Italian cappuccino is crafted with equal thirds: 1/3 espresso base, 1/3 steamed milk, and 1/3 thick, pillowy milk foam.",
      relatedDrinkId = "cappuccino"
    ),
    QuizQuestion(
      id = 4,
      question = "What is an Affogato al Caffè made of?",
      options = listOf(
        "Espresso blended with banana and peanut butter",
        "A scoop of vanilla gelato drowned in a shot of hot espresso",
        "Cold brew mixed with sparkling lemonade",
        "Filter coffee with condensed milk"
      ),
      correctIndex = 1,
      explanation = "'Affogato' in Italian means 'drowned'. It is a luscious combination of artisan vanilla bean gelato drowned in a freshly pulled hot shot of espresso.",
      relatedDrinkId = "affogato"
    ),
    QuizQuestion(
      id = 5,
      question = "What is the exact ratio and definition of a Spanish Cortado?",
      options = listOf(
        "1:1 equal parts espresso and lightly steamed milk to 'cut' acidity",
        "1 part coffee to 5 parts boiling water",
        "Double espresso with sweetened condensed milk",
        "Espresso shaken with orange peel"
      ),
      correctIndex = 0,
      explanation = "From the Spanish verb 'cortar' (to cut), a Cortado is equal parts (1:1) espresso and warm steamed milk with little-to-no foam, usually served in a 4.5oz Gibraltar glass.",
      relatedDrinkId = "cortado"
    ),
    QuizQuestion(
      id = 6,
      question = "What makes a Caffè Breve richer and higher in calories than a regular latte?",
      options = listOf(
        "It uses steamed half-and-half (cream & milk) instead of plain milk",
        "It has two tablespoons of melted butter",
        "It is brewed with sugar cane syrup in the portafilter",
        "It is topped with marshmallow fluff"
      ),
      correctIndex = 0,
      explanation = "A Breve substitutes regular whole milk with steamed half-and-half (equal parts milk and heavy cream), giving it a velvety, custard-like texture and rich dessert profile.",
      relatedDrinkId = "breve"
    ),
    QuizQuestion(
      id = 7,
      question = "What does 'Macchiato' literally mean in Italian?",
      options = listOf(
        "Small & Strong",
        "Marked or Stained (spotted with milk foam)",
        "Sweet morning coffee",
        "Whipped mountain"
      ),
      correctIndex = 1,
      explanation = "'Macchiato' means 'stained' or 'marked'. An Espresso Macchiato is an espresso stained with a dollop of milk foam; a Latte Macchiato is hot milk stained with a shot of espresso.",
      relatedDrinkId = "macchiato"
    ),
    QuizQuestion(
      id = 8,
      question = "Why does a Ristretto shot taste sweeter and less bitter than a standard espresso?",
      options = listOf(
        "Baristas secretly add sugar to the portafilter",
        "It is cut off at a 1:1 ratio, capturing only early sweet sugars before bitter tannins extract",
        "It is extracted with cold milk instead of hot water",
        "The beans are soaked in honey before roasting"
      ),
      correctIndex = 1,
      explanation = "Coffee compounds extract in sequence: first sour acids, then rich sweet sugars, and finally bitter astringent tannins. By restricting the shot volume, ristretto isolates the sweetest oils.",
      relatedDrinkId = "ristretto"
    ),
    QuizQuestion(
      id = 9,
      question = "What is an Espresso Tonic, and what makes it special?",
      options = listOf(
        "Espresso with protein powder and vitamin water",
        "Chilled tonic water over ice with an espresso shot floated gently on top",
        "Espresso brewed with carbonated water in the boiler",
        "Coffee with ginger ale and cinnamon"
      ),
      correctIndex = 1,
      explanation = "Created in Scandinavia, an Espresso Tonic combines effervescent botanical tonic water with ice and a gently floated espresso shot, creating a refreshing, citrusy, sparkling summer brew.",
      relatedDrinkId = "espresso_tonic"
    ),
    QuizQuestion(
      id = 10,
      question = "What gives Viennese 'Einspänner' (Vienna Coffee) its distinctive texture?",
      options = listOf(
        "Hot espresso sipped directly through a thick, cold layer of whipped cream",
        "Fine chocolate powder baked into the cup",
        "Boiled egg yolk beaten into the coffee",
        "Frozen coffee cubes that melt in hot cream"
      ),
      correctIndex = 0,
      explanation = "Vienna coffee is historically crowned with a cold, dense cap of whipped cream. The patron drinks the hot, dark espresso through the cold sweet cream blanket without stirring!",
      relatedDrinkId = "vienna"
    )
  )
}
