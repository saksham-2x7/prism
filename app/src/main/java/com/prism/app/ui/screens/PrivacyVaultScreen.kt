package com.prism.app.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BgColor = Color(0xFF000000)
private val CardColor = Color(0xFF1A1A1A)
private val CardBorderColor = Color(0xFF2A2A2A)
private val TextPrimaryColor = Color.White
private val TextSecondaryColor = Color(0xFFB0B0B0)
private val AccentCyanColor = Color(0xFF00E5FF)
private val AccentVioletColor = Color(0xFFBB86FC)
private val ErrorColor = Color(0xFFFF5252)
private val SuccessColor = Color(0xFF00E676)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyVaultScreen(onBackClick: () -> Unit = {}) {
    Scaffold(
        containerColor = BgColor,
        topBar = {
            TopAppBar(
                title = { Text("Privacy Vault", color = TextPrimaryColor, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimaryColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BgColor)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SuccessColor.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .border(1.dp, SuccessColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = "Encrypted", tint = SuccessColor)
                Spacer(Modifier.width(12.dp))
                Text("Everything encrypted and 100% Local", color = SuccessColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(title = "Sessions", value = "12", modifier = Modifier.weight(1f))
                StatCard(title = "Uploaded", value = "0 B", modifier = Modifier.weight(1f))
                StatCard(title = "Local", value = "100%", modifier = Modifier.weight(1f))
            }
            Spacer(Modifier.height(32.dp))
            Text("Recent Activity", color = TextPrimaryColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))
            
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ActivityItem("Sketch analyzed", "Code Gen", AccentCyanColor, "2 mins ago")
                ActivityItem("Voice transcribed", "Audio", AccentVioletColor, "1 hr ago")
                ActivityItem("Bug report generated", "QA", ErrorColor, "3 hrs ago")
                ActivityItem("Code explained", "Analysis", SuccessColor, "Yesterday")
                ActivityItem("Tasks extracted", "OCR", Color(0xFFFFD600), "Yesterday")
            }

            Spacer(Modifier.height(16.dp))
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, ErrorColor),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorColor)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Clear History", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardColor),
        border = BorderStroke(1.dp, CardBorderColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = TextPrimaryColor, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.height(4.dp))
            Text(title, color = TextSecondaryColor, fontSize = 12.sp)
        }
    }
}

@Composable
private fun ActivityItem(title: String, tag: String, color: Color, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = TextPrimaryColor, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.background(color.copy(alpha = 0.2f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                    Text(tag, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(8.dp))
                Text(time, color = TextSecondaryColor, fontSize = 12.sp)
            }
        }
    }
}
