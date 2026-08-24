package com.prism.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyVaultScreen(onBackClick: () -> Unit = {}) {
    val darkBg = Color(0xFF0D0D0D)
    val cardBg = Color(0xFF1A1A1A)
    val cyan = Color(0xFF00E5FF)
    val violet = Color(0xFFBB86FC)
    val red = Color(0xFFFF5252)
    val amber = Color(0xFFFFD600)
    val subText = Color(0xFF8E8E93)
    val green = Color(0xFF00E676)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Vault", color = Color.White) },
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
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatCard("12 Scans", modifier = Modifier.weight(1f), cardBg)
                StatCard("3 Reports", modifier = Modifier.weight(1f), cardBg)
                StatCard("100% Local", modifier = Modifier.weight(1f), cardBg, green)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = true, onClick = {}, label = { Text("All") }, colors = FilterChipDefaults.filterChipColors(selectedContainerColor = cyan.copy(alpha = 0.2f), selectedLabelColor = cyan))
                FilterChip(selected = false, onClick = {}, label = { Text("Code") })
                FilterChip(selected = false, onClick = {}, label = { Text("Bugs") })
                FilterChip(selected = false, onClick = {}, label = { Text("Tasks") })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Swipe to delete items", color = subText, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item { HistoryItem("Login wireframe scan", "Sketch to Code", "2 min ago", cyan, cardBg, subText) }
                item { HistoryItem("Sort function", "Voice to Code", "15 min ago", violet, cardBg, subText) }
                item { HistoryItem("Nav bug report", "Bug Reporter", "1 hr ago", red, cardBg, subText) }
                item { HistoryItem("Sprint notes", "Handwriting", "3 hrs ago", amber, cardBg, subText) }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = green, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("All data encrypted with AES-256 on your device", color = green, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = red)
                ) {
                    Text("Clear History")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.Black),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Export All")
                }
            }
        }
    }
}

@Composable
fun StatCard(text: String, modifier: Modifier = Modifier, cardBg: Color, textColor: Color = Color.White) {
    Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().padding(12.dp), contentAlignment = Alignment.Center) {
            Text(text, color = textColor, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun HistoryItem(title: String, type: String, time: String, dotColor: Color, cardBg: Color, subText: Color) {
    Card(colors = CardDefaults.cardColors(containerColor = cardBg), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).background(dotColor, CircleShape))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(type, color = subText, style = MaterialTheme.typography.bodySmall)
            }
            Text(time, color = subText, style = MaterialTheme.typography.labelSmall)
        }
    }
}
