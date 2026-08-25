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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.hypot
import kotlin.random.Random

data class NeuronNode(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val radius: Float
)

@Composable
fun NeuralBackground() {
    var width by remember { mutableFloatStateOf(1000f) }
    var height by remember { mutableFloatStateOf(1000f) }
    
    val nodes = remember {
        List(40) {
            NeuronNode(
                x = Random.nextFloat() * 1000f,
                y = Random.nextFloat() * 1000f,
                vx = (Random.nextFloat() - 0.5f) * 2f,
                vy = (Random.nextFloat() - 0.5f) * 2f,
                radius = Random.nextFloat() * 4f + 2f
            )
        }
    }
    
    var time by remember { mutableLongStateOf(0L) }
    
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { frameTime ->
                time = frameTime
                nodes.forEach { node ->
                    node.x += node.vx
                    node.y += node.vy
                    
                    if (node.x < 0) { node.x = 0f; node.vx *= -1 }
                    if (node.x > width) { node.x = width; node.vx *= -1 }
                    if (node.y < 0) { node.y = 0f; node.vy *= -1 }
                    if (node.y > height) { node.y = height; node.vy *= -1 }
                }
            }
        }
    }
    
    Canvas(modifier = Modifier.fillMaxSize().background(Color(0xFF000000))) {
        width = size.width
        height = size.height
        
        // Force recomposition
        time.hashCode()
        
        // Draw lines
        for (i in nodes.indices) {
            for (j in i + 1 until nodes.size) {
                val n1 = nodes[i]
                val n2 = nodes[j]
                val dist = hypot(n1.x - n2.x, n1.y - n2.y)
                if (dist < 300f) {
                    val alpha = (1f - dist / 300f).coerceIn(0f, 1f)
                    drawLine(
                        color = Color(0xFF00E5FF).copy(alpha = alpha * 0.5f),
                        start = Offset(n1.x, n1.y),
                        end = Offset(n2.x, n2.y),
                        strokeWidth = 1f
                    )
                }
            }
        }
        
        // Draw nodes
        nodes.forEach { node ->
            drawCircle(
                color = Color(0xFF00E5FF),
                radius = node.radius,
                center = Offset(node.x, node.y)
            )
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        NeuralBackground()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "NEURON",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "On-Device AI Developer Toolkit",
                fontSize = 16.sp,
                color = Color(0xFFB0B0B0)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Color(0xFF00E5FF))
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(text = "Sign in with Google", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = { navController.navigate("home") }
            ) {
                Text(
                    text = "Continue as Guest",
                    color = Color(0xFFB0B0B0),
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Text(
            text = "100% On-Device • Zero Cloud • NPU Powered",
            color = Color(0xFF6E6E73),
            fontSize = 10.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
