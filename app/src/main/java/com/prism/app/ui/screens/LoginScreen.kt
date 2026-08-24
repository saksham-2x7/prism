package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val DarkBackground = Color(0xFF0D0D0D)
private val SubtitleText = Color(0xFF8E8E93)

@Composable
fun LoginScreen(navController: NavController) {
    // Gradient animation
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color(0xFF000000), Color(0xFF1A1A1A), Color(0xFF0D0D0D)),
        start = Offset(0f, offset),
        end = Offset(offset, 1000f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔱",
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "PRISM",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 8.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
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
