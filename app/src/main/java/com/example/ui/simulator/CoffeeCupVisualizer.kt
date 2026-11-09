package com.example.ui.simulator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.example.model.BeverageLayer
import com.example.model.CupType
import com.example.model.IngredientType

@Composable
fun CoffeeCupVisualizer(
  layers: List<BeverageLayer>,
  cupType: CupType,
  modifier: Modifier = Modifier,
  showMeasureLines: Boolean = true
) {
  val totalVolume = layers.sumOf { it.volumeMl }.toFloat()
  val maxCapacity = cupType.capacityMl.toFloat()
  val fillFraction = (totalVolume / maxCapacity).coerceIn(0f, 1f)

  val animatedFill by animateFloatAsState(
    targetValue = fillFraction,
    animationSpec = tween(durationMillis = 400),
    label = "fill_fraction"
  )

  Box(modifier = modifier.padding(8.dp)) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Calculate cup dimensions based on vessel type
      val cupTopY = h * 0.15f
      val cupBottomY = h * 0.88f
      val cupHeight = cupBottomY - cupTopY

      val cupTopWidth = when (cupType) {
        CupType.DEMITASSE -> w * 0.58f
        CupType.GIBRALTAR -> w * 0.56f
        CupType.CERAMIC_CUP -> w * 0.72f
        CupType.TALL_GLASS -> w * 0.48f
        CupType.ICED_TUMBLER -> w * 0.54f
      }

      val cupBottomWidth = when (cupType) {
        CupType.DEMITASSE -> w * 0.42f
        CupType.GIBRALTAR -> w * 0.40f
        CupType.CERAMIC_CUP -> w * 0.44f
        CupType.TALL_GLASS -> w * 0.38f
        CupType.ICED_TUMBLER -> w * 0.42f
      }

      val centerX = w / 2f
      val cupLeftTop = centerX - cupTopWidth / 2f
      val cupRightTop = centerX + cupTopWidth / 2f
      val cupLeftBottom = centerX - cupBottomWidth / 2f
      val cupRightBottom = centerX + cupBottomWidth / 2f

      // Build Cup Inner Clip Path
      val cupPath = Path().apply {
        moveTo(cupLeftTop, cupTopY)
        when (cupType) {
          CupType.CERAMIC_CUP -> {
            // Rounded ceramic bowl shape
            cubicTo(
              cupLeftTop - 4f, cupTopY + cupHeight * 0.5f,
              cupLeftBottom - 8f, cupBottomY - 4f,
              cupLeftBottom, cupBottomY
            )
            lineTo(cupRightBottom, cupBottomY)
            cubicTo(
              cupRightBottom + 8f, cupBottomY - 4f,
              cupRightTop + 4f, cupTopY + cupHeight * 0.5f,
              cupRightTop, cupTopY
            )
          }
          CupType.GIBRALTAR -> {
            // Faceted tumbler lines
            lineTo(cupLeftBottom, cupBottomY)
            lineTo(cupRightBottom, cupBottomY)
            lineTo(cupRightTop, cupTopY)
          }
          else -> {
            // Elegant smooth tapered glass/cup
            lineTo(cupLeftBottom, cupBottomY)
            lineTo(cupRightBottom, cupBottomY)
            lineTo(cupRightTop, cupTopY)
          }
        }
        close()
      }

      // Draw Saucer for Demitasse / Ceramic
      if (cupType == CupType.DEMITASSE || cupType == CupType.CERAMIC_CUP) {
        val saucerY = cupBottomY + 8f
        val saucerWidth = cupTopWidth * 1.35f
        drawOval(
          color = Color(0xFFD7CCC8),
          topLeft = Offset(centerX - saucerWidth / 2f, saucerY - 6f),
          size = Size(saucerWidth, 20f)
        )
        drawOval(
          color = Color(0xFFA1887F),
          topLeft = Offset(centerX - saucerWidth / 2f, saucerY - 6f),
          size = Size(saucerWidth, 20f),
          style = Stroke(width = 2.5f)
        )
      }

      // Draw Handle for Ceramic / Demitasse
      if (cupType == CupType.CERAMIC_CUP || cupType == CupType.DEMITASSE) {
        val handlePath = Path().apply {
          val handleTopY = cupTopY + cupHeight * 0.22f
          val handleBottomY = cupTopY + cupHeight * 0.72f
          val handleStartX = cupRightTop - 4f
          val handleReachX = cupRightTop + (if (cupType == CupType.CERAMIC_CUP) 42f else 32f)

          moveTo(handleStartX, handleTopY)
          cubicTo(
            handleReachX, handleTopY - 6f,
            handleReachX + 4f, handleBottomY + 6f,
            cupRightBottom + 8f, handleBottomY
          )
        }
        drawPath(
          path = handlePath,
          color = Color(0xFF8D6E63),
          style = Stroke(width = 8f, cap = StrokeCap.Round)
        )
        drawPath(
          path = handlePath,
          color = Color(0xFFD7CCC8),
          style = Stroke(width = 4f, cap = StrokeCap.Round)
        )
      }

      // Draw Cup Glass/Ceramic Translucent Background
      val vesselBackground = when (cupType) {
        CupType.DEMITASSE, CupType.CERAMIC_CUP -> Brush.linearGradient(
          colors = listOf(Color(0xFFF7F5F0).copy(alpha = 0.95f), Color(0xFFECE7DE).copy(alpha = 0.95f)),
          start = Offset(cupLeftTop, cupTopY),
          end = Offset(cupRightBottom, cupBottomY)
        )
        else -> Brush.linearGradient(
          colors = listOf(Color.White.copy(alpha = 0.15f), Color.White.copy(alpha = 0.05f)),
          start = Offset(cupLeftTop, cupTopY),
          end = Offset(cupRightBottom, cupBottomY)
        )
      }

      drawPath(
        path = cupPath,
        brush = vesselBackground
      )

      // Clip and Draw Liquid Layers
      clipPath(cupPath) {
        if (layers.isNotEmpty() && animatedFill > 0f) {
          // Calculate layer bounding vertical positions
          // Sort layers by density or natural settling (higher density at bottom)
          val sortedLayers = layers.sortedByDescending { it.type.density }
          val totalSortedVol = sortedLayers.sumOf { it.volumeMl }.toFloat().coerceAtLeast(1f)

          var currentTopY = cupBottomY
          val availableHeight = cupHeight * 0.94f * animatedFill

          for (layer in sortedLayers) {
            val layerHeight = (layer.volumeMl.toFloat() / totalSortedVol) * availableHeight
            val layerBottom = currentTopY
            val layerTop = currentTopY - layerHeight
            currentTopY = layerTop

            // Liquid Rect
            drawRect(
              brush = Brush.verticalGradient(
                colors = listOf(layer.type.accentColor, layer.type.primaryColor),
                startY = layerTop,
                endY = layerBottom
              ),
              topLeft = Offset(0f, layerTop),
              size = Size(w, layerHeight + 1.5f)
            )

            // Special ingredient visuals:
            // 1. Ice cubes
            if (layer.type == IngredientType.ICE_CUBES) {
              drawIceCubes(centerX, layerTop, layerBottom, w)
            }

            // 2. Microfoam / Airy Foam bubbles texture
            if (layer.type == IngredientType.MICROFOAM || layer.type == IngredientType.AIRY_FOAM) {
              drawFoamTexture(centerX, layerTop, layerHeight, w)
            }

            // 3. Gelato scoop
            if (layer.type == IngredientType.ICE_CREAM_GELATO) {
              drawCircle(
                brush = Brush.radialGradient(
                  colors = listOf(Color(0xFFFFFBEA), Color(0xFFFFE8A3)),
                  center = Offset(centerX, layerTop + layerHeight * 0.4f),
                  radius = 35f
                ),
                radius = 35f,
                center = Offset(centerX, layerTop + layerHeight * 0.4f)
              )
            }

            // 4. Whipped cream peaks
            if (layer.type == IngredientType.WHIPPED_CREAM) {
              drawWhippedCream(centerX, layerTop, w)
            }

            // 5. Crema top for Espresso if near top
            if ((layer.type == IngredientType.ESPRESSO || layer.type == IngredientType.RISTRETTO) && layer == sortedLayers.last()) {
              drawCremaTop(centerX, layerTop, w)
            }

            // 6. Cocoa or Cinnamon Dust
            if (layer.type == IngredientType.COCOA_DUST || layer.type == IngredientType.CINNAMON_SPICE) {
              drawSpiceDust(centerX, layerTop, w, layer.type.primaryColor)
            }

            // Thin surface boundary line
            drawLine(
              color = Color.White.copy(alpha = 0.25f),
              start = Offset(0f, layerTop),
              end = Offset(w, layerTop),
              strokeWidth = 1f
            )
          }
        }
      }

      // Draw Glass/Cup Outline & Rim
      val outlineColor = when (cupType) {
        CupType.DEMITASSE, CupType.CERAMIC_CUP -> Color(0xFF8D6E63)
        else -> Color(0xFFB0BEC5)
      }
      drawPath(
        path = cupPath,
        color = outlineColor,
        style = Stroke(width = 4f, cap = StrokeCap.Round)
      )

      // Rim Highlight
      val rimRect = Rect(cupLeftTop - 3f, cupTopY - 4f, cupRightTop + 3f, cupTopY + 4f)
      drawRoundRect(
        color = outlineColor.copy(alpha = 0.85f),
        topLeft = rimRect.topLeft,
        size = rimRect.size,
        cornerRadius = CornerRadius(4f, 4f)
      )

      // Draw Measurement Lines on the side
      if (showMeasureLines) {
        val totalTicks = 4
        for (i in 1..totalTicks) {
          val tickVolume = (cupType.capacityMl * i / (totalTicks + 1))
          val tickY = cupBottomY - (cupHeight * 0.94f * (i.toFloat() / (totalTicks + 1)))
          val tickX = cupLeftBottom + (cupLeftTop - cupLeftBottom) * (1f - (i.toFloat() / (totalTicks + 1)))

          drawLine(
            color = Color.Gray.copy(alpha = 0.6f),
            start = Offset(tickX + 4f, tickY),
            end = Offset(tickX + 18f, tickY),
            strokeWidth = 2f
          )
        }
      }
    }
  }
}

private fun DrawScope.drawIceCubes(centerX: Float, topY: Float, bottomY: Float, totalW: Float) {
  val cubeSize = 36f
  val positions = listOf(
    Offset(centerX - 40f, topY + 12f),
    Offset(centerX + 15f, topY + 8f),
    Offset(centerX - 15f, topY + 36f)
  )

  for (pos in positions) {
    if (pos.y < bottomY - 10f) {
      drawRoundRect(
        color = Color(0xFFE1F5FE).copy(alpha = 0.6f),
        topLeft = pos,
        size = Size(cubeSize, cubeSize),
        cornerRadius = CornerRadius(6f, 6f)
      )
      drawRoundRect(
        color = Color.White.copy(alpha = 0.85f),
        topLeft = pos,
        size = Size(cubeSize, cubeSize),
        cornerRadius = CornerRadius(6f, 6f),
        style = Stroke(width = 1.8f)
      )
      // glint line
      drawLine(
        color = Color.White.copy(alpha = 0.9f),
        start = Offset(pos.x + 6f, pos.y + 6f),
        end = Offset(pos.x + 14f, pos.y + 6f),
        strokeWidth = 2f
      )
    }
  }
}

private fun DrawScope.drawFoamTexture(centerX: Float, topY: Float, height: Float, totalW: Float) {
  // Micro bubble highlights
  val bubbleOffsets = listOf(
    Offset(centerX - 45f, topY + height * 0.35f),
    Offset(centerX - 18f, topY + height * 0.25f),
    Offset(centerX + 20f, topY + height * 0.40f),
    Offset(centerX + 48f, topY + height * 0.30f),
    Offset(centerX - 5f, topY + height * 0.65f),
    Offset(centerX + 28f, topY + height * 0.70f)
  )

  for (b in bubbleOffsets) {
    drawCircle(
      color = Color.White.copy(alpha = 0.5f),
      radius = 3.5f,
      center = b
    )
  }
}

private fun DrawScope.drawWhippedCream(centerX: Float, topY: Float, totalW: Float) {
  // Swirling whipped cream peaks
  val peakPath = Path().apply {
    moveTo(centerX - 50f, topY + 10f)
    cubicTo(centerX - 30f, topY - 12f, centerX - 10f, topY + 2f, centerX, topY - 18f)
    cubicTo(centerX + 15f, topY - 10f, centerX + 35f, topY - 8f, centerX + 50f, topY + 10f)
    close()
  }
  drawPath(
    path = peakPath,
    brush = Brush.radialGradient(
      colors = listOf(Color.White, Color(0xFFF9F6EE)),
      center = Offset(centerX, topY - 10f),
      radius = 45f
    )
  )
}

private fun DrawScope.drawCremaTop(centerX: Float, topY: Float, totalW: Float) {
  // Golden crema ring on espresso
  drawOval(
    brush = Brush.radialGradient(
      colors = listOf(Color(0xFFE5A85C), Color(0xFFBD7931)),
      center = Offset(centerX, topY + 3f),
      radius = 60f
    ),
    topLeft = Offset(centerX - 60f, topY),
    size = Size(120f, 10f)
  )
}

private fun DrawScope.drawSpiceDust(centerX: Float, topY: Float, totalW: Float, color: Color) {
  val dustPoints = listOf(
    Offset(centerX - 30f, topY + 2f),
    Offset(centerX - 15f, topY + 4f),
    Offset(centerX, topY + 2f),
    Offset(centerX + 18f, topY + 3f),
    Offset(centerX + 32f, topY + 1f),
    Offset(centerX - 8f, topY + 6f),
    Offset(centerX + 10f, topY + 5f)
  )

  for (p in dustPoints) {
    drawCircle(
      color = color.copy(alpha = 0.85f),
      radius = 2.2f,
      center = p
    )
  }
}
