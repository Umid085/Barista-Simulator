package com.example.ui.receipt

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BaristaReceipt
import com.example.model.CoffeeDrink
import com.example.ui.theme.ParchmentReceipt
import com.example.ui.theme.ReceiptDivider
import com.example.ui.theme.ReceiptText

@Composable
fun CoffeeReceiptCard(
  drink: CoffeeDrink,
  modifier: Modifier = Modifier,
  onBrewInSimulator: (() -> Unit)? = null,
  isServedDrink: Boolean = false,
  accuracyScore: Int? = null
) {
  val context = LocalContext.current
  val receipt = drink.receipt

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("coffee_receipt_card_${drink.id}"),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = ParchmentReceipt),
    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp, vertical = 20.dp)
    ) {
      // Top Serrated Jagged Edge Decoration
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .drawBehind {
            val step = 16f
            val count = (size.width / step).toInt()
            val p = Path()
            p.moveTo(0f, size.height)
            for (i in 0..count) {
              val x1 = i * step + step / 2f
              val x2 = (i + 1) * step
              p.lineTo(x1, 0f)
              p.lineTo(x2, size.height)
            }
            drawPath(p, color = Color(0xFFD3CBBB))
          }
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Cafe Header
      Text(
        text = receipt.storeHeader,
        color = ReceiptText,
        fontSize = 17.sp,
        fontWeight = FontWeight.Black,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )
      Text(
        text = receipt.storeSubHeader,
        color = ReceiptText.copy(alpha = 0.7f),
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )

      // Accuracy Badge if evaluated
      if (isServedDrink && accuracyScore != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          color = if (accuracyScore >= 80) Color(0xFF2E7D32) else Color(0xFFE65100),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
          Text(
            text = "★ CRAFT SCORE: $accuracyScore% MATCH ★",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
          )
        }
      }

      ReceiptDashedDivider()

      // Order Ticket Details
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "RECEIPT #${receipt.receiptNumber}",
          color = ReceiptText,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = receipt.timestamp,
          color = ReceiptText.copy(alpha = 0.8f),
          fontSize = 11.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Text(
        text = "ITEM: ${drink.name.uppercase()}",
        color = ReceiptText,
        fontSize = 18.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.padding(top = 4.dp)
      )

      Text(
        text = "AKA: ${drink.nativeName} • Origin: ${drink.origin}",
        color = ReceiptText.copy(alpha = 0.75f),
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )

      ReceiptDashedDivider()

      // Itemized Ingredients Table Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "INGREDIENTS & PROPORTIONS",
          color = ReceiptText,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "VOL / TEMP",
          color = ReceiptText,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Itemized Rows
      receipt.items.forEach { item ->
        Column(modifier = Modifier.padding(vertical = 3.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "• ${item.label}",
              color = ReceiptText,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              fontFamily = FontFamily.Monospace,
              modifier = Modifier.weight(1f)
            )
            Text(
              text = item.quantity,
              color = ReceiptText,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "  Share: ${item.percentage}% of cup",
              color = ReceiptText.copy(alpha = 0.7f),
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = item.temperatureDesc,
              color = ReceiptText.copy(alpha = 0.7f),
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }

      ReceiptDashedDivider()

      // Extraction Specs
      Text(
        text = "BARISTA EXTRACTION SPECS:",
        color = ReceiptText,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "• Ratio: ${receipt.brewRatio}",
        color = ReceiptText,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )
      Text(
        text = "• Extraction: ${drink.specs.extractionTimeSec}s @ ${drink.specs.waterTempC}°C (Grind: ${drink.specs.grindSize})",
        color = ReceiptText,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )
      if (drink.specs.milkTempC != null) {
        Text(
          text = "• Milk Target: ${drink.specs.milkTempC}°C silky texturing",
          color = ReceiptText,
          fontSize = 11.sp,
          fontFamily = FontFamily.Monospace
        )
      }
      Text(
        text = "• Vessel: ${drink.recommendedCup.displayName} (${drink.recommendedTotalMl} ml)",
        color = ReceiptText,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )

      ReceiptDashedDivider()

      // Sensory & Nutrition
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(
            text = "CAFFEINE: ~${drink.specs.caffeineMg} mg",
            color = ReceiptText,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = "ENERGY: ${drink.specs.caloriesKcal} kcal",
            color = ReceiptText.copy(alpha = 0.75f),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
        }
        Column(horizontalAlignment = Alignment.End) {
          Text(
            text = "TASTING PROFILE",
            color = ReceiptText,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
          Text(
            text = drink.specs.tastingNotes.joinToString(" • "),
            color = ReceiptText.copy(alpha = 0.85f),
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            textAlign = TextAlign.End
          )
        }
      }

      ReceiptDashedDivider()

      // Preparation Steps
      Text(
        text = "PREPARATION ORDER:",
        color = ReceiptText,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.height(4.dp))
      receipt.prepSteps.forEachIndexed { index, step ->
        Text(
          text = "${index + 1}. $step",
          color = ReceiptText,
          fontSize = 11.sp,
          fontFamily = FontFamily.Monospace,
          modifier = Modifier.padding(vertical = 2.dp)
        )
      }

      ReceiptDashedDivider()

      // Barista Tip Box
      Surface(
        color = Color(0xFFECE5D8),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Text(
            text = "💡 BARISTA PRO-TIP",
            color = Color(0xFF5D4037),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = receipt.baristaProTip,
            color = ReceiptText,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Barcode Graphic
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = receipt.barcode,
          color = ReceiptText,
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 2.sp,
          fontFamily = FontFamily.Monospace
        )
        Text(
          text = "* COFFEE-LAB-${drink.id.uppercase()} *",
          color = ReceiptText.copy(alpha = 0.6f),
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        OutlinedButton(
          onClick = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = buildReceiptText(drink)
            clipboard.setPrimaryClip(ClipData.newPlainText("Coffee Recipe Receipt", text))
            Toast.makeText(context, "Recipe receipt copied to clipboard!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier
            .weight(1f)
            .testTag("copy_receipt_button_${drink.id}"),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF4E342E))
        ) {
          Icon(Icons.Default.ContentCopy, contentDescription = "Copy Receipt", modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Copy", fontSize = 12.sp, fontFamily = FontFamily.Monospace)
        }

        if (onBrewInSimulator != null) {
          Button(
            onClick = onBrewInSimulator,
            modifier = Modifier
              .weight(1.5f)
              .testTag("brew_recipe_button_${drink.id}"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Brew in Simulator", modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Brew Recipe", fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
fun ReceiptDashedDivider() {
  Spacer(modifier = Modifier.height(8.dp))
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .height(1.dp)
      .drawBehind {
        drawLine(
          color = ReceiptDivider,
          start = Offset(0f, 0f),
          end = Offset(size.width, 0f),
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f),
          strokeWidth = 2f
        )
      }
  )
  Spacer(modifier = Modifier.height(8.dp))
}

fun buildReceiptText(drink: CoffeeDrink): String {
  val r = drink.receipt
  return buildString {
    appendLine("========================================")
    appendLine("       ${r.storeHeader}")
    appendLine("   ${r.storeSubHeader}")
    appendLine("========================================")
    appendLine("RECEIPT #${r.receiptNumber}   ${r.timestamp}")
    appendLine("ITEM: ${drink.name.uppercase()} (${drink.nativeName})")
    appendLine("ORIGIN: ${drink.origin}")
    appendLine("----------------------------------------")
    appendLine("INGREDIENTS & PROPORTIONS:")
    drink.receipt.items.forEach { item ->
      appendLine("• ${item.label.padEnd(24)} ${item.quantity} (${item.percentage}%)")
      appendLine("  Temp/Note: ${item.temperatureDesc}")
    }
    appendLine("----------------------------------------")
    appendLine("BREW RATIO: ${r.brewRatio}")
    appendLine("EXTRACTION: ${drink.specs.extractionTimeSec}s @ ${drink.specs.waterTempC}°C (Grind: ${drink.specs.grindSize})")
    if (drink.specs.milkTempC != null) {
      appendLine("MILK TEMP: ${drink.specs.milkTempC}°C")
    }
    appendLine("VESSEL: ${drink.recommendedCup.displayName} (${drink.recommendedTotalMl}ml)")
    appendLine("CAFFEINE: ~${drink.specs.caffeineMg}mg | CALORIES: ${drink.specs.caloriesKcal}kcal")
    appendLine("NOTES: ${drink.specs.tastingNotes.joinToString(", ")}")
    appendLine("----------------------------------------")
    appendLine("PREPARATION STEPS:")
    r.prepSteps.forEachIndexed { i, s ->
      appendLine("${i + 1}. $s")
    }
    appendLine("----------------------------------------")
    appendLine("PRO-TIP: ${r.baristaProTip}")
    appendLine("========================================")
  }
}
