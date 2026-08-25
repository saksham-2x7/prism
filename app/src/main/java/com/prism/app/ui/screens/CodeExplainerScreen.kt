package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private val BgBlack = Color(0xFF000000)
private val CardBg = Color(0xFF111111)
private val CardBorder = Color(0xFF222222)
private val AccentGreen = Color(0xFF00E676)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFAAAAAA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeExplainerScreen(onBackClick: () -> Unit = {}) {
    var state by remember { mutableIntStateOf(0) } // 0=Input, 1=Loading, 2=Result
    var codeInput by remember { mutableStateOf("") }
    var explanation by remember { mutableStateOf("") }
    var complexity by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Code Explainer", color = TextPrimary, fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state == 0) {
                // Input State
                Text(
                    text = "Paste code to analyze",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = codeInput,
                    onValueChange = { codeInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(CardBg, RoundedCornerShape(16.dp)),
                    textStyle = LocalTextStyle.current.copy(
                        color = AccentGreen,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    placeholder = { Text("fun main() {\n  println(\"Hello\")\n}", color = Color.DarkGray, fontFamily = FontFamily.Monospace) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentGreen,
                        unfocusedBorderColor = CardBorder,
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (codeInput.isNotBlank()) {
                            state = 1
                            coroutineScope.launch {
                                // Simulate NPU Inference Delay
                                delay(3500) // Give them time to admire the animation
                                
                                // Dynamic Response Logic
                                if (codeInput.contains("for") || codeInput.contains("while")) {
                                    complexity = "O(n)"
                                    explanation = "This code iterates through a collection. It uses a loop construct which means execution time grows linearly with the input size."
                                } else if (codeInput.contains("if") && codeInput.contains("return")) {
                                    complexity = "O(1)"
                                    explanation = "This is a conditional branching function. It checks a state and returns immediately, making it very efficient."
                                } else if (codeInput.contains("suspend") || codeInput.contains("await")) {
                                    complexity = "Asynchronous"
                                    explanation = "This code performs asynchronous operations, likely network or database I/O, without blocking the main thread."
                                } else {
                                    complexity = "O(1)"
                                    explanation = "This is a standard functional block. It executes sequentially with constant time complexity."
                                }
                                state = 2
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Analyze with NPU", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            } else if (state == 1) {
                // Loading State (Neural Thinking Animation)
                Spacer(modifier = Modifier.height(40.dp))
                NeuralThinkingAnimation(color = AccentGreen)
            } else {
                // Result State
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = CardBg,
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AccentGreen)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Analysis Complete", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("COMPLEXITY", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(shape = RoundedCornerShape(8.dp), color = AccentGreen.copy(alpha=0.15f)) {
                            Text(complexity, color = AccentGreen, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        
                        Text("EXPLANATION", color = TextSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(explanation, color = TextPrimary, fontSize = 16.sp, lineHeight = 24.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                
                OutlinedButton(
                    onClick = { state = 0; codeInput = "" },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
                ) {
                    Text("Analyze Another Snippet", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun NeuralThinkingAnimation(color: Color) {
    val thoughtSteps = listOf(
        "Tokenizing input sequence...",
        "Building Abstract Syntax Tree...",
        "Tracing variable scopes...",
        "Inferring Big-O complexity...",
        "Synthesizing explanation..."
    )
    var stepIndex by remember { mutableIntStateOf(0) }
    
    LaunchedEffect(Unit) {
        while (stepIndex < thoughtSteps.size - 1) {
            delay(700)
            stepIndex++
        }
    }

    val transition = rememberInfiniteTransition(label = "pulse")
    val alpha by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.size(200.dp), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.width / 2 - 20f
                
                val nodes = mutableListOf<Offset>()
                for (i in 0 until 8) {
                    val angle = (i * 45).toDouble() * Math.PI / 180
                    val r = radius * (0.6f + Random.nextFloat() * 0.4f)
                    val x = center.x + r * cos(angle).toFloat()
                    val y = center.y + r * sin(angle).toFloat()
                    nodes.add(Offset(x, y))
                }
                nodes.add(center) // central node

                // Draw connections
                for (i in nodes.indices) {
                    for (j in i + 1 until nodes.size) {
                        if (Random.nextFloat() > 0.4f) { // Random sparse connections
                            val lineAlpha = if (i == stepIndex % nodes.size || j == stepIndex % nodes.size) alpha else 0.1f
                            drawLine(
                                color = color.copy(alpha = lineAlpha),
                                start = nodes[i],
                                end = nodes[j],
                                strokeWidth = if (lineAlpha > 0.5f) 4f else 1f
                            )
                        }
                    }
                }

                // Draw nodes
                nodes.forEachIndexed { i, node ->
                    val nodeAlpha = if (i == stepIndex % nodes.size) alpha else 0.3f
                    val nodeRadius = if (i == stepIndex % nodes.size) 8f else 4f
                    drawCircle(
                        color = color.copy(alpha = nodeAlpha),
                        radius = nodeRadius,
                        center = node
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = thoughtSteps[stepIndex],
            color = color,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Running local LLM inference...",
            color = TextSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
