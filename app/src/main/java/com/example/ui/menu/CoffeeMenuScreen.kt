package com.example.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CoffeeDatabase
import com.example.model.CoffeeCategory
import com.example.model.CoffeeDrink
import com.example.ui.simulator.CoffeeCupVisualizer

@Composable
fun CoffeeMenuScreen(
  onSelectDrinkForBrew: (CoffeeDrink) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf(CoffeeCategory.ALL) }
  var activeDetailDrink by remember { mutableStateOf<CoffeeDrink?>(null) }

  val filteredDrinks = remember(searchQuery, selectedCategory) {
    CoffeeDatabase.drinks.filter { drink ->
      val matchesCategory = selectedCategory == CoffeeCategory.ALL || drink.category == selectedCategory
      val matchesSearch = searchQuery.isBlank() ||
        drink.name.contains(searchQuery, ignoreCase = true) ||
        drink.nativeName.contains(searchQuery, ignoreCase = true) ||
        drink.description.contains(searchQuery, ignoreCase = true) ||
        drink.layers.any { it.type.displayName.contains(searchQuery, ignoreCase = true) }
      matchesCategory && matchesSearch
    }
  }

  Column(modifier = modifier.fillMaxSize()) {
    // Search Bar
    OutlinedTextField(
      value = searchQuery,
      onValueChange = { searchQuery = it },
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .testTag("coffee_search_input"),
      placeholder = { Text("Search coffee recipes, ingredients, styles...") },
      leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
      trailingIcon = {
        if (searchQuery.isNotEmpty()) {
          IconButton(onClick = { searchQuery = "" }) {
            Icon(Icons.Default.Clear, contentDescription = "Clear search")
          }
        }
      },
      singleLine = true,
      shape = RoundedCornerShape(24.dp)
    )

    // Category Filter Chips
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(CoffeeCategory.values()) { category ->
        FilterChip(
          selected = selectedCategory == category,
          onClick = { selectedCategory = category },
          label = { Text("${category.iconEmoji} ${category.displayName}") },
          modifier = Modifier.testTag("filter_chip_${category.name.lowercase()}")
        )
      }
    }

    // Drinks List
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
      item {
        Text(
          text = "${filteredDrinks.size} Coffee Recipes & Receipts",
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(vertical = 4.dp)
        )
      }

      items(filteredDrinks, key = { it.id }) { drink ->
        CoffeeDrinkCard(
          drink = drink,
          onClick = { activeDetailDrink = drink },
          onBrew = { onSelectDrinkForBrew(drink) }
        )
      }
    }
  }

  // Detail Modal (Anatomy Breakdown & Receipt)
  activeDetailDrink?.let { drink ->
    CoffeeDetailModal(
      drink = drink,
      onDismiss = { activeDetailDrink = null },
      onBrewInSimulator = { onSelectDrinkForBrew(it) }
    )
  }
}

@Composable
fun CoffeeDrinkCard(
  drink: CoffeeDrink,
  onClick: () -> Unit,
  onBrew: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("coffee_card_${drink.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Miniature Cup Preview
      Card(
        modifier = Modifier
          .size(width = 72.dp, height = 90.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(10.dp)
      ) {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CoffeeCupVisualizer(
            layers = drink.layers,
            cupType = drink.recommendedCup,
            modifier = Modifier.fillMaxSize(),
            showMeasureLines = false
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      // Info
      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = drink.name,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
          Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "${drink.recommendedTotalMl}ml",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }

        Text(
          text = drink.tagLine,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.primary,
          maxLines = 1
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Ingredients summary tags
        val ingredientsSummary = drink.layers.joinToString(" • ") { it.type.shortName }
        Text(
          text = ingredientsSummary,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Action Buttons Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FilledTonalButton(
            onClick = onClick,
            modifier = Modifier
              .weight(1f)
              .height(34.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
          ) {
            Icon(Icons.Default.Receipt, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Receipt", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          }

          Button(
            onClick = onBrew,
            modifier = Modifier
              .weight(1f)
              .height(34.dp)
              .testTag("brew_button_${drink.id}"),
            contentPadding = PaddingValues(horizontal = 8.dp)
          ) {
            Icon(Icons.Default.LocalDrink, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Simulate", fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
