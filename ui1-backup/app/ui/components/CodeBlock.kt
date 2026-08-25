package com.prism.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun CodeBlock(
    code: String,
    language: String,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFF111111))
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF222222))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = language,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { clipboardManager.setText(AnnotatedString(code)) }) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy code",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }
        
        // Code content with line numbers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            val lines = code.lines()
            
            // Line numbers
            Column(horizontalAlignment = Alignment.End) {
                lines.forEachIndexed { index, _ ->
                    Text(
                        text = (index + 1).toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(1.dp).background(Color.White.copy(alpha = 0.1f)))
            
            // Code
            Column(modifier = Modifier.padding(start = 12.dp)) {
                lines.forEach { line ->
                    Text(
                        text = line.ifEmpty { " " },
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}
