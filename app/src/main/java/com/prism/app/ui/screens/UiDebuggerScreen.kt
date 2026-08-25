package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
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
private val AccentViolet = Color(0xFFBB86FC)
private val AccentCyan = Color(0xFF00E5FF)
private val SuccessGreen = Color(0xFF00E676)
private val WarningAmber = Color(0xFFFFD600)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFAAAAAA)

data class UiNode(
    val name: String,
    val recompositions: Int,
    val drawTimeMs: Double,
    val depth: Int,
    val bounds: String,
    val hasIssue: Boolean = false,
    val issueHint: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiDebuggerScreen(onBackClick: () -> Unit = {}) {
    var selectedNodeIndex by remember { mutableIntStateOf(2) }
    var isOptimizing by remember { mutableStateOf(false) }
    var optimizationApplied by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val nodes = remember(optimizationApplied) {
        listOf(
            UiNode("Scaffold", 1, 0.4, 0, "1080 x 2400 px"),
            UiNode("├── Box [Root Container]", 1, 0.6, 1, "1080 x 2400 px"),
            UiNode(
                "│   ├── LazyVerticalGrid",
                if (optimizationApplied) 1 else 14,
                if (optimizationApplied) 1.1 else 4.8,
                2,
                "1040 x 1800 px",
                hasIssue = !optimizationApplied,
                issueHint = "Unstable lambda parameter causes full grid recomposition on scroll"
            ),
            UiNode("│   │   ├── GlassCard [Sketch]", 1, 0.8, 3, "500 x 360 px"),
            UiNode("│   │   ├── GlassCard [Voice]", 1, 0.7, 3, "500 x 360 px"),
            UiNode("│   │   └── GlassCard [Architecture]", 1, 0.9, 3, "500 x 360 px"),
            UiNode("│   └── GlassBottomBar", 1, 0.5, 2, "760 x 160 px")
        )
    }

    val selectedNode = nodes.getOrElse(selectedNodeIndex) { nodes[0] }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("UI & Hierarchy Debugger", color = TextPrimary, fontWeight = FontWeight.Bold) },
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
                .padding(20.dp)
        ) {
            // Live Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    color = CardBg,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Frame Rate", color = TextSecondary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(if (optimizationApplied) "120 FPS" else "58 FPS", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(if (optimizationApplied) "Smooth (0 Janks)" else "2 Skipped Frames", color = if (optimizationApplied) SuccessGreen else WarningAmber, fontSize = 11.sp)
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    color = CardBg,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Layers, contentDescription = null, tint = AccentViolet, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Tree Depth", color = TextSecondary, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("4 Levels", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("7 Active Nodes", color = AccentViolet, fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "COMPOSE HIERARCHY TREE",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = CardBg,
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    nodes.forEachIndexed { index, node ->
                        val isSelected = selectedNodeIndex == index
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) AccentViolet.copy(alpha = 0.15f) else Color.Transparent)
                                .clickable { selectedNodeIndex = index }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (node.hasIssue) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                }
                                Text(
                                    text = node.name,
                                    color = if (isSelected) TextPrimary else TextSecondary,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 13.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }

                            Text(
                                text = "Recomp: ${node.recompositions}x",
                                color = if (node.recompositions > 5) WarningAmber else SuccessGreen,
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Node Inspector Details
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = CardBg,
                border = BorderStroke(1.dp, if (selectedNode.hasIssue) WarningAmber.copy(alpha = 0.5f) else CardBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("NODE INSPECTOR: ${selectedNode.name.replace("│", "").replace("├", "").replace("─", "").replace("└", "").trim()}", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Bounds", color = TextSecondary, fontSize = 13.sp)
                        Text(selectedNode.bounds, color = TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Draw Duration", color = TextSecondary, fontSize = 13.sp)
                        Text("${selectedNode.drawTimeMs} ms", color = TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
                    }

                    if (selectedNode.hasIssue) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = WarningAmber.copy(alpha = 0.15f),
                            border = BorderStroke(1.dp, WarningAmber.copy(alpha = 0.3f))
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = WarningAmber, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(selectedNode.issueHint, color = WarningAmber, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isOptimizing = true
                    coroutineScope.launch {
                        delay(2000)
                        optimizationApplied = true
                        isOptimizing = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !isOptimizing,
                colors = ButtonDefaults.buttonColors(containerColor = AccentViolet, contentColor = Color.Black)
            ) {
                if (isOptimizing) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.Black, strokeWidth = 3.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("NPU Analyzing AST & Recompositions...", color = Color.Black, fontWeight = FontWeight.Bold)
                } else if (optimizationApplied) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Layout Tree Fully Optimized (120 FPS)", fontWeight = FontWeight.Bold)
                } else {
                    Icon(Icons.Default.Speed, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Run NPU Layout Optimization", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
