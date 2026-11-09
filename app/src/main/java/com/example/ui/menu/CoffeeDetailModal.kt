package com.example.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Receipt
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BeverageLayer
import com.example.model.CoffeeDrink
import com.example.ui.receipt.CoffeeReceiptCard
import com.example.ui.simulator.CoffeeCupVisualizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeDetailModal(
  drink: CoffeeDrink,
  onDismiss: () -> Unit,
  onBrewInSimulator: (CoffeeDrink) -> Unit
) {
  var selectedTab by remember { mutableStateOf(0) } // 0: Anatomy Breakdown, 1: Coffee Receipt
  var selectedLayer by remember { mutableStateOf<BeverageLayer?>(drink.layers.firstOrNull()) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = {
            Column {
              Text(drink.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
              Text(drink.nativeName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
          },
          navigationIcon = {
            IconButton(
              onClick = onDismiss,
              modifier = Modifier.testTag("close_detail_modal_button")
            ) {
              Icon(Icons.Default.Close, contentDescription = "Close details")
            }
          },
          actions = {
            Button(
              onClick = {
                onBrewInSimulator(drink)
                onDismiss()
              },
              modifier = Modifier
                .padding(end = 8.dp)
                .testTag("brew_from_modal_button"),
              colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
              Icon(Icons.Default.LocalDrink, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Brew")
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
          )
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        // Tab row: Anatomy vs Receipt
        PrimaryTabRow(
          selectedTabIndex = selectedTab,
          modifier = Modifier.fillMaxWidth()
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            icon = { Icon(Icons.Default.LocalDrink, contentDescription = null) },
            text = { Text("Layer Anatomy") },
            modifier = Modifier.testTag("tab_layer_anatomy")
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            icon = { Icon(Icons.Default.Receipt, contentDescription = null) },
            text = { Text("Barista Receipt") },
            modifier = Modifier.testTag("tab_barista_receipt")
          )
        }

        if (selectedTab == 0) {
          // LAYER ANATOMY VIEW
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Visual Cup Container
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
              colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
              )
            ) {
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .padding(12.dp),
                contentAlignment = Alignment.Center
              ) {
                CoffeeCupVisualizer(
                  layers = drink.layers,
                  cupType = drink.recommendedCup,
                  modifier = Modifier.fillMaxSize()
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Origin and Description
            Text(
              text = drink.tagLine,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = drink.description,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Layer Proportion Explanations
            Text(
              text = "Cup Anatomy (Tap any layer to learn):",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val totalVol = drink.recommendedTotalMl.coerceAtLeast(1)

            drink.layers.forEach { layer ->
              val pct = ((layer.volumeMl.toFloat() / totalVol) * 100).toInt()
              val isSelected = selectedLayer == layer

              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clickable { selectedLayer = layer }
                  .testTag("layer_row_${layer.type.id}"),
                colors = CardDefaults.cardColors(
                  containerColor = if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer
                  else
                    MaterialTheme.colorScheme.surface
                ),
                border = if (isSelected)
                  androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                else
                  androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Color swatch
                  Box(
                    modifier = Modifier
                      .size(24.dp)
                      .clip(CircleShape)
                      .background(layer.type.primaryColor)
                      .border(1.dp, Color.Gray.copy(alpha = 0.4f), CircleShape)
                  )

                  Spacer(modifier = Modifier.width(12.dp))

                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = "${layer.type.iconEmoji} ${layer.type.displayName}",
                      fontWeight = FontWeight.Bold,
                      fontSize = 14.sp
                    )
                    Text(
                      text = layer.customNote ?: layer.type.description,
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }

                  Spacer(modifier = Modifier.width(8.dp))

                  Column(horizontalAlignment = Alignment.End) {
                    Text(
                      text = "${layer.volumeMl} ml",
                      fontWeight = FontWeight.ExtraBold,
                      fontSize = 14.sp,
                      color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                      text = "$pct%",
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  }
                }
              }
            }

            // Selected Layer Detail Callout
            selectedLayer?.let { layer ->
              Spacer(modifier = Modifier.height(14.dp))
              Surface(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
                  Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                  )
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = "Why this is added (${layer.type.displayName}):",
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = "${layer.type.description} Poured at ~${layer.type.typicalTempC}°C with density factor ${layer.type.density}.",
                      style = MaterialTheme.typography.bodySmall
                    )
                  }
                }
              }
            }
          }
        } else {
          // RECEIPT TAB
          Box(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState())
              .padding(16.dp)
          ) {
            CoffeeReceiptCard(
              drink = drink,
              onBrewInSimulator = {
                onBrewInSimulator(drink)
                onDismiss()
              }
            )
          }
        }
      }
    }
  }
}
