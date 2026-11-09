package com.example.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoffeeDatabase
import com.example.data.CoffeeQuizRepository
import com.example.data.QuizQuestion
import com.example.model.CoffeeDrink
import com.example.ui.simulator.CoffeeCupVisualizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaristaQuizScreen(
  onSelectDrinkForBrew: (CoffeeDrink) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Quiz Challenge, 1: Quick Reference Cheat Sheet

  Column(modifier = modifier.fillMaxSize()) {
    PrimaryTabRow(
      selectedTabIndex = selectedTab,
      modifier = Modifier.fillMaxWidth()
    ) {
      Tab(
        selected = selectedTab == 0,
        onClick = { selectedTab = 0 },
        icon = { Icon(Icons.Default.School, contentDescription = null) },
        text = { Text("Barista Quiz") },
        modifier = Modifier.testTag("tab_barista_quiz")
      )
      Tab(
        selected = selectedTab == 1,
        onClick = { selectedTab = 1 },
        icon = { Icon(Icons.Default.Star, contentDescription = null) },
        text = { Text("Visual Comparison") },
        modifier = Modifier.testTag("tab_layer_comparison")
      )
    }

    if (selectedTab == 0) {
      QuizChallengeView()
    } else {
      VisualComparisonCheatSheet(onSelectDrinkForBrew = onSelectDrinkForBrew)
    }
  }
}

@Composable
fun QuizChallengeView() {
  val questions = remember { CoffeeQuizRepository.questions }
  var currentIndex by remember { mutableStateOf(0) }
  var selectedOption by remember { mutableStateOf<Int?>(null) }
  var hasSubmitted by remember { mutableStateOf(false) }
  var correctCount by remember { mutableStateOf(0) }
  var isQuizComplete by remember { mutableStateOf(false) }

  val currentQuestion = questions.getOrNull(currentIndex)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(16.dp)
      .padding(bottom = 70.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (isQuizComplete) {
      // Results Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(16.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text("🎓", fontSize = 48.sp)
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "You scored $correctCount / ${questions.size}",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
          )

          val rank = when {
            correctCount >= 9 -> "🏆 Master Specialty Barista"
            correctCount >= 6 -> "☕ Certified Coffee Artisan"
            else -> "🌱 Apprentice Brewer (Keep practicing!)"
          }

          Text(
            text = rank,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp)
          )

          Spacer(modifier = Modifier.height(20.dp))

          Button(
            onClick = {
              currentIndex = 0
              selectedOption = null
              hasSubmitted = false
              correctCount = 0
              isQuizComplete = false
            },
            modifier = Modifier.testTag("restart_quiz_button")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text("Take Quiz Again")
          }
        }
      }
    } else if (currentQuestion != null) {
      // Progress Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Question ${currentIndex + 1} of ${questions.size}",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = "Score: $correctCount",
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
      }

      LinearProgressIndicator(
        progress = { (currentIndex + 1).toFloat() / questions.size },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Question Card
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = currentQuestion.question,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(16.dp))

          // Options List
          currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOption == index
            val isCorrect = index == currentQuestion.correctIndex

            val optionColor = when {
              hasSubmitted && isCorrect -> Color(0xFF2E7D32)
              hasSubmitted && isSelected && !isCorrect -> MaterialTheme.colorScheme.error
              isSelected -> MaterialTheme.colorScheme.primary
              else -> MaterialTheme.colorScheme.outlineVariant
            }

            val optionBg = when {
              hasSubmitted && isCorrect -> Color(0xFFE8F5E9)
              hasSubmitted && isSelected && !isCorrect -> Color(0xFFFFEBEE)
              isSelected -> MaterialTheme.colorScheme.primaryContainer
              else -> MaterialTheme.colorScheme.surface
            }

            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clickable(enabled = !hasSubmitted) { selectedOption = index }
                .testTag("quiz_option_$index"),
              shape = RoundedCornerShape(10.dp),
              color = optionBg,
              border = androidx.compose.foundation.BorderStroke(1.5.dp, optionColor)
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = "${('A' + index)}. ",
                  fontWeight = FontWeight.Bold,
                  color = optionColor
                )
                Text(
                  text = optionText,
                  style = MaterialTheme.typography.bodyMedium,
                  modifier = Modifier.weight(1f)
                )

                if (hasSubmitted) {
                  if (isCorrect) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Correct", tint = Color(0xFF2E7D32))
                  } else if (isSelected) {
                    Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = MaterialTheme.colorScheme.error)
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Explanation Box when submitted
          if (hasSubmitted) {
            Surface(
              color = MaterialTheme.colorScheme.surfaceVariant,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = if (selectedOption == currentQuestion.correctIndex) "✓ Excellent!" else "💡 Barista Insight:",
                  fontWeight = FontWeight.Bold,
                  color = if (selectedOption == currentQuestion.correctIndex) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = currentQuestion.explanation,
                  style = MaterialTheme.typography.bodySmall
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))
          }

          // Action Button (Submit or Next)
          if (!hasSubmitted) {
            Button(
              onClick = {
                if (selectedOption != null) {
                  hasSubmitted = true
                  if (selectedOption == currentQuestion.correctIndex) {
                    correctCount++
                  }
                }
              },
              enabled = selectedOption != null,
              modifier = Modifier
                .fillMaxWidth()
                .testTag("submit_answer_button")
            ) {
              Text("Check Answer")
            }
          } else {
            Button(
              onClick = {
                if (currentIndex + 1 < questions.size) {
                  currentIndex++
                  selectedOption = null
                  hasSubmitted = false
                } else {
                  isQuizComplete = true
                }
              },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("next_question_button")
            ) {
              Text(if (currentIndex + 1 < questions.size) "Next Question →" else "See Final Results")
            }
          }
        }
      }
    }
  }
}

@Composable
fun VisualComparisonCheatSheet(
  onSelectDrinkForBrew: (CoffeeDrink) -> Unit
) {
  // Classic comparison pairs: Flat White vs Latte vs Cappuccino vs Cortado vs Americano vs Mocha
  val comparisonDrinks = remember {
    listOf(
      "espresso",
      "macchiato",
      "cortado",
      "flat_white",
      "cappuccino",
      "latte",
      "mocha",
      "americano",
      "affogato"
    ).mapNotNull { CoffeeDatabase.findDrinkById(it) }
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 80.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = "Quick Recipe Proportion Guide",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "Side-by-side anatomy of classic coffeehouse recipes and what makes each one distinct.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }

    items(comparisonDrinks) { drink ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Cup mini
          Card(
            modifier = Modifier.size(width = 60.dp, height = 75.dp),
            colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
          ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
              CoffeeCupVisualizer(
                layers = drink.layers,
                cupType = drink.recommendedCup,
                showMeasureLines = false
              )
            }
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(drink.tagLine, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))

            // Layers bar
            val total = drink.recommendedTotalMl.coerceAtLeast(1)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .clip(RoundedCornerShape(4.dp))
            ) {
              drink.layers.forEach { l ->
                val weight = (l.volumeMl.toFloat() / total).coerceAtLeast(0.05f)
                Box(
                  modifier = Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .background(l.type.primaryColor)
                )
              }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = drink.layers.joinToString(" • ") { "${it.type.shortName} (${it.volumeMl}ml)" },
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          IconButton(
            onClick = { onSelectDrinkForBrew(drink) },
            modifier = Modifier.testTag("brew_cheat_sheet_${drink.id}")
          ) {
            Icon(Icons.Default.School, contentDescription = "Brew in Simulator", tint = MaterialTheme.colorScheme.primary)
          }
        }
      }
    }
  }
}
