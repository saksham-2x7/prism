package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val BgBlack = Color(0xFF000000)
private val CardBg = Color(0xFF111111)
private val CardBorder = Color(0xFF222222)
private val AccentCyan = Color(0xFF00E5FF)
private val AccentGreen = Color(0xFF00E676)
private val UserBubbleBg = Color(0xFF1E293B)
private val AiBubbleBg = Color(0xFF18181B)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFAAAAAA)

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "ai"
    val text: String,
    val inferenceTimeMs: Int = 0,
    val tokensPerSec: Double = 0.0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocalLlmScreen(onBackClick: () -> Unit = {}) {
    var messageInput by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "1",
                sender = "ai",
                text = "Hello! I am your 100% on-device Gemma-3n code assistant running on the Snapdragon 8 Elite NPU. Ask me anything about your project architecture, algorithms, or Android optimization!",
                inferenceTimeMs = 45,
                tokensPerSec = 52.4
            )
        )
    }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val quickPrompts = listOf(
        "Explain ViewModel architecture",
        "How to avoid Compose jank?",
        "Write a Room DB entity"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Local Code LLM", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(6.dp).clip(RoundedCornerShape(3.dp)).background(AccentGreen))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Gemma-3n-E2B (INT4) • 52 t/s NPU", color = AccentGreen, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgBlack)
            )
        },
        containerColor = BgBlack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Chat Messages List
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages, key = { it.id }) { msg ->
                    if (msg.sender == "user") {
                        // User message
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Surface(
                                shape = RoundedCornerShape(18.dp, 18.dp, 4.dp, 18.dp),
                                color = UserBubbleBg,
                                border = BorderStroke(1.dp, Color(0xFF334155)),
                                modifier = Modifier.widthIn(max = 280.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    color = TextPrimary,
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                        }
                    } else {
                        // AI message
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column(modifier = Modifier.widthIn(max = 320.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(18.dp, 18.dp, 18.dp, 4.dp),
                                    color = AiBubbleBg,
                                    border = BorderStroke(1.dp, CardBorder)
                                ) {
                                    Text(
                                        text = msg.text,
                                        color = TextPrimary,
                                        fontSize = 14.sp,
                                        lineHeight = 22.sp,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                }
                                if (msg.tokensPerSec > 0) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 8.dp)
                                    ) {
                                        Icon(Icons.Default.Memory, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(12.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "${msg.tokensPerSec} tokens/sec • ${msg.inferenceTimeMs}ms TTFT",
                                            color = AccentCyan,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (isThinking) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(18.dp),
                            color = AiBubbleBg,
                            border = BorderStroke(1.dp, CardBorder),
                            modifier = Modifier.widthIn(max = 240.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    color = AccentCyan,
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("NPU Inferring token stream...", color = AccentCyan, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }

            // Quick Prompts Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                quickPrompts.forEach { prompt ->
                    Surface(
                        onClick = {
                            messageInput = prompt
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = CardBg,
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Text(
                            text = prompt,
                            color = TextSecondary,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Input Bar
            Surface(
                color = CardBg,
                border = BorderStroke(1.dp, CardBorder),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = { Text("Ask local Gemma model...", color = Color.DarkGray, fontSize = 14.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                val userText = messageInput
                                messageInput = ""
                                val userMsg = ChatMessage(System.currentTimeMillis().toString(), "user", userText)
                                messages.add(userMsg)
                                isThinking = true

                                coroutineScope.launch {
                                    delay(1800)
                                    val aiResponse = when {
                                        userText.contains("ViewModel", ignoreCase = true) ->
                                            "In Jetpack Compose, retain state across config changes using `viewModel()`. Expose state via `StateFlow` and collect with `collectAsStateWithLifecycle()` to avoid resource leakage during backgrounding."
                                        userText.contains("jank", ignoreCase = true) || userText.contains("Compose", ignoreCase = true) ->
                                            "To eliminate Compose jank: 1) Use `@Immutable` or `@Stable` on UI models. 2) Defer state reads to layout/draw phases via lambda modifiers `Modifier.graphicsLayer { ... }`. 3) Use `key` in `LazyColumn` items."
                                        else ->
                                            "On-device inference completed. Code analyzed with zero telemetry. Using INT4 quantization ensures memory footprint stays below 1.2GB VRAM on Snapdragon 8 Elite."
                                    }
                                    messages.add(
                                        ChatMessage(
                                            id = (System.currentTimeMillis() + 1).toString(),
                                            sender = "ai",
                                            text = aiResponse,
                                            inferenceTimeMs = 38,
                                            tokensPerSec = 49.8
                                        )
                                    )
                                    isThinking = false
                                    listState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        },
                        enabled = messageInput.isNotBlank() && !isThinking
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = if (messageInput.isNotBlank()) AccentCyan else TextSecondary
                        )
                    }
                }
            }
        }
    }
}
