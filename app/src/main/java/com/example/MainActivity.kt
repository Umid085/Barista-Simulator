package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CoffeeDrink
import com.example.ui.menu.CoffeeMenuScreen
import com.example.ui.quiz.BaristaQuizScreen
import com.example.ui.simulator.BaristaSimulatorScreen
import com.example.ui.theme.BaristaSimulatorTheme

enum class BaristaScreen(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val testTag: String) {
  SIMULATOR("Brew Station", Icons.Default.LocalDrink, "nav_simulator"),
  RECEIPTS("Menu & Receipts", Icons.Default.Receipt, "nav_receipts"),
  ACADEMY("Coffee IQ", Icons.Default.School, "nav_academy")
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      BaristaSimulatorTheme {
        BaristaApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaristaApp() {
  var currentScreen by remember { mutableStateOf(BaristaScreen.SIMULATOR) }
  var targetDrinkForBrew by remember { mutableStateOf<CoffeeDrink?>(null) }

  // Handle hardware back button to return to simulator if on secondary tab
  BackHandler(enabled = currentScreen != BaristaScreen.SIMULATOR) {
    currentScreen = BaristaScreen.SIMULATOR
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Barista Simulator",
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "Coffee Anatomy, Recipes & Receipts",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
              )
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    },
    bottomBar = {
      NavigationBar(
        modifier = Modifier.testTag("main_navigation_bar"),
        containerColor = MaterialTheme.colorScheme.surface
      ) {
        BaristaScreen.values().forEach { screen ->
          val isSelected = currentScreen == screen
          NavigationBarItem(
            selected = isSelected,
            onClick = { currentScreen = screen },
            icon = {
              Icon(
                screen.icon,
                contentDescription = screen.label
              )
            },
            label = {
              Text(
                text = screen.label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 11.sp
              )
            },
            modifier = Modifier.testTag(screen.testTag)
          )
        }
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentScreen) {
        BaristaScreen.SIMULATOR -> {
          BaristaSimulatorScreen(
            targetDrinkInitial = targetDrinkForBrew,
            onViewAllReceipts = {
              currentScreen = BaristaScreen.RECEIPTS
            }
          )
        }
        BaristaScreen.RECEIPTS -> {
          CoffeeMenuScreen(
            onSelectDrinkForBrew = { drink ->
              targetDrinkForBrew = drink
              currentScreen = BaristaScreen.SIMULATOR
            }
          )
        }
        BaristaScreen.ACADEMY -> {
          BaristaQuizScreen(
            onSelectDrinkForBrew = { drink ->
              targetDrinkForBrew = drink
              currentScreen = BaristaScreen.SIMULATOR
            }
          )
        }
      }
    }
  }
}
