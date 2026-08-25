package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceToCodeScreen(onBackClick: () -> Unit = {}) {
    var currentState by remember { mutableIntStateOf(0) }

    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val violet = Color(0xFFBB86FC)
    val subText = Color(0xFF8E8E93)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Voice to Code", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = darkBg)
            )
        },
        containerColor = darkBg
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            when (currentState) {
                0, 1 -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val infiniteTransition = rememberInfiniteTransition()
                        val scale by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = if (currentState == 1) 1.2f else 1f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .scale(scale)
                                .background(if (currentState == 1) cyan.copy(alpha = 0.2f) else Color.Transparent, CircleShape)
                                .border(2.dp, if (currentState == 1) cyan else subText, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Mic,
                                contentDescription = "Mic",
                                modifier = Modifier.size(80.dp),
                                tint = if (currentState == 1) cyan else subText
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        if (currentState == 1) {
                            Row(
                                modifier = Modifier.height(40.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(5) {
                                    val heightScale by infiniteTransition.animateFloat(
                                        initialValue = 0.3f,
                                        targetValue = 1f,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(400, delayMillis = it * 100),
                                            repeatMode = RepeatMode.Reverse
                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .width(8.dp)
                                            .fillMaxHeight(heightScale)
                                            .background(cyan, RoundedCornerShape(4.dp))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Listening...", color = cyan)

                            LaunchedEffect(Unit) {
                                delay(3000)
                                currentState = 2
                            }
                        } else {
                            Text("Tap to speak", color = Color.White, style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf("Kotlin", "Python", "JS", "Swift", "Go").forEachIndexed { index, lang ->
                                    FilterChip(
                                        selected = index == 0,
                                        onClick = {},
                                        label = { Text(lang) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = cyan.copy(alpha = 0.2f),
                                            selectedLabelColor = cyan
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            Text("Or type your prompt", color = subText)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = "",
                                onValueChange = {},
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = subText,
                                    focusedBorderColor = cyan,
                                    unfocusedTextColor = Color.White,
                                    focusedTextColor = Color.White
                                ),
                                placeholder = { Text("Describe what to code...", color = subText) }
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            TextButton(onClick = { currentState = 1 }) {
                                Text("Try Demo", color = cyan)
                            }
                        }
                    }
                }
                2 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = cardBg),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "“Create a function that sorts a list and returns top 3”",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color.Black, RoundedCornerShape(8.dp))
                                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = """
fun topThree(list: List<Int>): List<Int> {
    return list.sortedDescending().take(3)
}
                                """.trimIndent(),
                                color = violet,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = { currentState = 0 },
                                colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Copy")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            OutlinedButton(
                                onClick = { },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Text("Explain")
                            }
                        }
                    }
                }
            }
        }
    }
}
