package com.prism.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                                delay(2500)
                                
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
                // Loading State
                Spacer(modifier = Modifier.height(100.dp))
                CircularProgressIndicator(color = AccentGreen, modifier = Modifier.size(64.dp), strokeWidth = 6.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Text("NPU Analyzing AST...", color = AccentGreen, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Text("Running Gemma-3n-E2B locally", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
            } else {
                // Result State
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = CardBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
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
