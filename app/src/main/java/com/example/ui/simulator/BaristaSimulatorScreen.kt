package com.example.ui.simulator

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoffeeDatabase
import com.example.data.DrinkEvaluation
import com.example.model.*
import com.example.ui.receipt.CoffeeReceiptCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaristaSimulatorScreen(
  targetDrinkInitial: CoffeeDrink? = null,
  onViewAllReceipts: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  // Default to Cappuccino if none provided, since it's the most famous multi-layered drink
  var selectedDrink by remember {
    mutableStateOf(targetDrinkInitial ?: CoffeeDatabase.findDrinkById("cappuccino") ?: CoffeeDatabase.drinks.first())
  }
  var layers by remember { mutableStateOf<List<BeverageLayer>>(emptyList()) }

  var isPouringAnimation by remember { mutableStateOf(false) }
  var showFullReceiptDialog by remember { mutableStateOf(false) }
  var servedEvaluation by remember { mutableStateOf<DrinkEvaluation?>(null) }

  // Sync if targetDrinkInitial updates externally (e.g. from Menu/Receipts tab)
  LaunchedEffect(targetDrinkInitial) {
    if (targetDrinkInitial != null) {
      selectedDrink = targetDrinkInitial
      layers = emptyList()
    }
  }

  fun triggerHaptic() {
    try {
      val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator?.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE))
      } else {
        @Suppress("DEPRECATION")
        vibrator?.vibrate(35)
      }
    } catch (_: Exception) {}
  }

  fun pourIngredient(type: IngredientType, customVolume: Int? = null) {
    val vol = customVolume ?: type.defaultVolumeMl
    val currentTotal = layers.sumOf { it.volumeMl }
    if (currentTotal + vol > selectedDrink.recommendedCup.capacityMl + 20) {
      Toast.makeText(context, "Cup is full! Tap 'Finish & Serve' or 'Start Over'", Toast.LENGTH_SHORT).show()
      return
    }

    triggerHaptic()
    layers = layers + BeverageLayer(type = type, volumeMl = vol)
    isPouringAnimation = true

    coroutineScope.launch {
      delay(800)
      isPouringAnimation = false
    }
  }

  fun undoLastPour() {
    if (layers.isNotEmpty()) {
      triggerHaptic()
      layers = layers.dropLast(1)
    }
  }

  fun resetCup() {
    triggerHaptic()
    layers = emptyList()
  }

  // Calculate which preparation step we are currently on
  val currentStepIndex = layers.size.coerceAtMost(selectedDrink.layers.size)
  val isRecipeComplete = layers.size >= selectedDrink.layers.size && layers.isNotEmpty()
  val nextExpectedLayer = selectedDrink.layers.getOrNull(currentStepIndex)

  val totalVolume = layers.sumOf { it.volumeMl }

  Column(
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .padding(bottom = 80.dp)
  ) {
    // 1. SIMPLE DRINK SELECTOR AT THE TOP
    Surface(
      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Choose Coffee to Make:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          TextButton(
            onClick = { showFullReceiptDialog = true },
            modifier = Modifier.testTag("view_full_receipt_button")
          ) {
            Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("View Full Receipt", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }

        // Horizontal drink chips
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(CoffeeDatabase.drinks) { drink ->
            val isSelected = selectedDrink.id == drink.id
            FilterChip(
              selected = isSelected,
              onClick = {
                if (selectedDrink.id != drink.id) {
                  selectedDrink = drink
                  layers = emptyList() // Start fresh for new coffee
                }
              },
              label = {
                Text(
                  text = drink.name,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  fontSize = 12.sp
                )
              },
              leadingIcon = {
                Text(
                  when (drink.category) {
                    CoffeeCategory.CLASSIC_ESPRESSO -> "⚡"
                    CoffeeCategory.MILK_AND_FOAM -> "🥛"
                    CoffeeCategory.SWEET_SPECIALTY -> "🍫"
                    CoffeeCategory.ICED_REFRESHING -> "🧊"
                    else -> "☕"
                  }
                )
              },
              modifier = Modifier.testTag("select_drink_${drink.id}")
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // 2. PROMINENT PREPARATION INSTRUCTIONS & RECIPE (ALWAYS VISIBLE WHILE PREPARING)
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .testTag("preparing_instructions_card"),
      shape = RoundedCornerShape(14.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        // Recipe Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "📋 PREPARING: ${selectedDrink.name.uppercase()}",
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 14.sp,
              color = MaterialTheme.colorScheme.primary
            )
            Text(
              text = "Vessel: ${selectedDrink.recommendedCup.displayName} (~${selectedDrink.recommendedTotalMl}ml)",
              fontSize = 11.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Surface(
            color = if (isRecipeComplete) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(8.dp)
          ) {
            Text(
              text = if (isRecipeComplete) "✓ Complete" else "Step ${currentStepIndex + 1} of ${selectedDrink.layers.size}",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = if (isRecipeComplete) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Follow these steps to make this coffee:",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Step-by-Step Instruction Checklist
        selectedDrink.layers.forEachIndexed { index, recipeLayer ->
          val isDone = index < layers.size
          val isCurrent = index == currentStepIndex && !isRecipeComplete

          Surface(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 3.dp),
            shape = RoundedCornerShape(8.dp),
            color = when {
              isDone -> Color(0xFFE8F5E9) // soft green for completed
              isCurrent -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) // highlighted active step
              else -> Color.Transparent
            },
            border = if (isCurrent)
              androidx.compose.foundation.BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
            else
              null
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Status Icon
              Text(
                text = if (isDone) "✓" else "${index + 1}.",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = if (isDone) Color(0xFF2E7D32) else if (isCurrent) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier.width(22.dp)
              )

              // Step Detail
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Step ${index + 1}: Add ${recipeLayer.type.displayName} (${recipeLayer.volumeMl}ml)",
                  fontSize = 12.sp,
                  fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                  color = if (isDone) Color(0xFF1B5E20) else MaterialTheme.colorScheme.onSurface
                )
                if (recipeLayer.customNote != null && isCurrent) {
                  Text(
                    text = recipeLayer.customNote,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }

              // Color swatch
              Box(
                modifier = Modifier
                  .size(14.dp)
                  .clip(CircleShape)
                  .background(recipeLayer.type.primaryColor)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // 3. COFFEE CUP DISPLAY WITH REAL-TIME LAYERS
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(230.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
      ),
      shape = RoundedCornerShape(16.dp)
    ) {
      Box(modifier = Modifier.fillMaxSize()) {
        CoffeeCupVisualizer(
          layers = layers,
          cupType = selectedDrink.recommendedCup,
          modifier = Modifier.fillMaxSize()
        )

        // Volume Indicator
        Surface(
          color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .align(Alignment.TopStart)
            .padding(10.dp)
        ) {
          Text(
            text = "$totalVolume / ${selectedDrink.recommendedTotalMl} ml",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }

        // Pouring indicator
        androidx.compose.animation.AnimatedVisibility(
          visible = isPouringAnimation,
          enter = fadeIn(),
          exit = fadeOut(),
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 10.dp)
        ) {
          Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(16.dp)
          ) {
            Text(
              text = "☕ Pouring...",
              color = Color.White,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
          }
        }

        // Cup Controls (Undo, Reset)
        Row(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(10.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          IconButton(
            onClick = { undoLastPour() },
            enabled = layers.isNotEmpty(),
            modifier = Modifier
              .size(32.dp)
              .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), CircleShape)
              .testTag("simple_undo_button")
          ) {
            Icon(Icons.Default.Undo, contentDescription = "Undo pour", modifier = Modifier.size(16.dp))
          }
          IconButton(
            onClick = { resetCup() },
            enabled = layers.isNotEmpty(),
            modifier = Modifier
              .size(32.dp)
              .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f), CircleShape)
              .testTag("simple_reset_button")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = "Start over", modifier = Modifier.size(16.dp))
          }
        }

        // Current layer chips at bottom of cup
        if (layers.isNotEmpty()) {
          LazyRow(
            modifier = Modifier
              .align(Alignment.BottomCenter)
              .fillMaxWidth()
              .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
              .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            items(layers) { l ->
              Text(
                text = "${l.type.shortName} (${l.volumeMl}ml)",
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                  .background(l.type.primaryColor.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 4. ACTION CONTROLS: QUICK NEXT STEP BUTTON (SUPER EASY 1-TAP PREPARATION)
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
      if (nextExpectedLayer != null) {
        Button(
          onClick = {
            pourIngredient(nextExpectedLayer.type, nextExpectedLayer.volumeMl)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("quick_next_step_button"),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "👉 Pour Step ${currentStepIndex + 1}: ${nextExpectedLayer.type.displayName} (${nextExpectedLayer.volumeMl}ml)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }
      } else {
        // Recipe completed!
        Button(
          onClick = {
            val eval = CoffeeDatabase.evaluateCreatedDrink(layers, selectedDrink.recommendedCup)
            servedEvaluation = eval
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("serve_coffee_button"),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "🎉 Coffee Done! Check Result & Receipt",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 5. OR MANUAL INGREDIENT TAP BUTTONS
      Text(
        text = "Or tap any ingredient to add:",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Clean 2-column or 3-column ingredient grid
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        SimpleIngredientButton(
          label = "Espresso",
          amount = "30ml",
          emoji = "☕",
          tag = "btn_add_espresso",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.ESPRESSO, 30) }

        SimpleIngredientButton(
          label = "Double Shot",
          amount = "60ml",
          emoji = "☕☕",
          tag = "btn_add_double_espresso",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.ESPRESSO, 60) }

        SimpleIngredientButton(
          label = "Ristretto",
          amount = "20ml",
          emoji = "⚡",
          tag = "btn_add_ristretto",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.RISTRETTO, 20) }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        SimpleIngredientButton(
          label = "Steamed Milk",
          amount = "50ml",
          emoji = "🥛",
          tag = "btn_add_steamed_milk",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.STEAMED_MILK, 50) }

        SimpleIngredientButton(
          label = "Microfoam",
          amount = "30ml",
          emoji = "🍶",
          tag = "btn_add_microfoam",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.MICROFOAM, 30) }

        SimpleIngredientButton(
          label = "Airy Foam",
          amount = "30ml",
          emoji = "☁️",
          tag = "btn_add_foam",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.AIRY_FOAM, 30) }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        SimpleIngredientButton(
          label = "Hot Water",
          amount = "50ml",
          emoji = "💧",
          tag = "btn_add_hot_water",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.HOT_WATER, 50) }

        SimpleIngredientButton(
          label = "Mocha Choc",
          amount = "20ml",
          emoji = "🍫",
          tag = "btn_add_chocolate",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.CHOCOLATE_SYRUP, 20) }

        SimpleIngredientButton(
          label = "Ice Cubes",
          amount = "40ml",
          emoji = "🧊",
          tag = "btn_add_ice",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.ICE_CUBES, 40) }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        SimpleIngredientButton(
          label = "Gelato Scoop",
          amount = "50ml",
          emoji = "🍨",
          tag = "btn_add_gelato",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.ICE_CREAM_GELATO, 50) }

        SimpleIngredientButton(
          label = "Whip Cream",
          amount = "30ml",
          emoji = "🧁",
          tag = "btn_add_whip",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.WHIPPED_CREAM, 30) }

        SimpleIngredientButton(
          label = "Cocoa Dust",
          amount = "Pinch",
          emoji = "✨",
          tag = "btn_add_cocoa",
          modifier = Modifier.weight(1f)
        ) { pourIngredient(IngredientType.COCOA_DUST, 2) }
      }
    }
  }

  // DIALOG 1: FULL THERMAL RECEIPT MODAL
  if (showFullReceiptDialog) {
    AlertDialog(
      onDismissRequest = { showFullReceiptDialog = false },
      confirmButton = {
        Button(onClick = { showFullReceiptDialog = false }) {
          Text("Close Receipt")
        }
      },
      text = {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp)
            .verticalScroll(rememberScrollState())
        ) {
          CoffeeReceiptCard(drink = selectedDrink)
        }
      }
    )
  }

  // DIALOG 2: SERVED DRINK RESULT & RECEIPT MODAL
  servedEvaluation?.let { eval ->
    AlertDialog(
      onDismissRequest = { servedEvaluation = null },
      confirmButton = {
        Button(
          onClick = {
            servedEvaluation = null
            layers = emptyList() // Clear for next coffee
            // Suggest another coffee
            val next = CoffeeDatabase.drinks.filter { it.id != selectedDrink.id }.random()
            selectedDrink = next
          },
          modifier = Modifier.testTag("brew_next_dialog_btn")
        ) {
          Text("Make Another Coffee")
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { servedEvaluation = null }) {
          Text("Keep This")
        }
      },
      title = {
        Text("☕ Coffee Prepared!")
      },
      text = {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 520.dp)
            .verticalScroll(rememberScrollState())
        ) {
          Text(
            text = eval.feedback,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
          )
          Spacer(modifier = Modifier.height(12.dp))
          eval.matchedDrink?.let { drink ->
            CoffeeReceiptCard(
              drink = drink,
              isServedDrink = true,
              accuracyScore = eval.accuracyScore
            )
          }
        }
      }
    )
  }
}

@Composable
fun SimpleIngredientButton(
  label: String,
  amount: String,
  emoji: String,
  tag: String,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Surface(
    modifier = modifier
      .clickable(onClick = onClick)
      .testTag(tag),
    shape = RoundedCornerShape(8.dp),
    color = MaterialTheme.colorScheme.surface,
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    tonalElevation = 1.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(emoji, fontSize = 16.sp)
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(
          text = label,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          maxLines = 1
        )
        Text(
          text = amount,
          fontSize = 9.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
