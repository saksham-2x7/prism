package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.hypot
import kotlin.random.Random

private val DarkBackground = Color(0xFF000000)
private val SubtitleText = Color(0xFF8E8E93)
private val AccentCyan = Color(0xFF00E5FF)

data class NeuronNode(var x: Float, var y: Float, var vx: Float, var vy: Float, val radius: Float)

@Composable
fun NeuralBackground(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }

    // Initialize nodes
    val nodes = remember {
        List(40) {
            NeuronNode(
                x = Random.nextFloat() * screenWidth,
                y = Random.nextFloat() * screenHeight,
                vx = (Random.nextFloat() - 0.5f) * 2f,
                vy = (Random.nextFloat() - 0.5f) * 2f,
                radius = Random.nextFloat() * 4f + 2f
            )
        }
    }

    // Use a state to force recomposition/redraw
    var frameTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { time ->
                frameTime = time
                // Update positions
                nodes.forEach { node ->
                    node.x += node.vx
                    node.y += node.vy

                    // Bounce off walls
                    if (node.x < 0) { node.x = 0f; node.vx *= -1 }
                    if (node.x > screenWidth) { node.x = screenWidth; node.vx *= -1 }
                    if (node.y < 0) { node.y = 0f; node.vy *= -1 }
                    if (node.y > screenHeight) { node.y = screenHeight; node.vy *= -1 }
                }
            }
        }
    }

    Canvas(modifier = modifier.fillMaxSize().background(DarkBackground)) {
        // Read frameTime to force redraw on every frame
        frameTime.hashCode()
            
        // Draw Nodes
        nodes.forEach { node ->
            drawCircle(color = AccentCyan.copy(alpha = 0.8f), radius = node.radius, center = Offset(node.x, node.y))
        }

        // Draw connections (Synapses)
        val connectionDistance = 350f
        for (i in nodes.indices) {
            for (j in i + 1 until nodes.size) {
                val n1 = nodes[i]
                val n2 = nodes[j]
                val dist = hypot(n2.x - n1.x, n2.y - n1.y)

                if (dist < connectionDistance) {
                    val opacity = (1f - (dist / connectionDistance)).coerceIn(0f, 1f)
                    drawLine(
                        color = AccentCyan.copy(alpha = opacity * 0.4f),
                        start = Offset(n1.x, n1.y),
                        end = Offset(n2.x, n2.y),
                        strokeWidth = 2f
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Dynamic Neural Network Background
        NeuralBackground()

        // Foreground UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Neuron",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 6.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Your phone sees code differently.",
                fontSize = 16.sp,
                color = SubtitleText,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(64.dp))

            OutlinedButton(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    containerColor = Color.Black.copy(alpha = 0.5f) // blur effect base
                )
            ) {
                Text(
                    text = "G",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(
                    text = "Sign in with Google",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate("home") }
            ) {
                Text(
                    text = "Continue as Guest",
                    color = SubtitleText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
