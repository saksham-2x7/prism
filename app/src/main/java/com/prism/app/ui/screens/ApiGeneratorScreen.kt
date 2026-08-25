package com.prism.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
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
private val AccentCyan = Color(0xFF00E5FF)
private val AccentViolet = Color(0xFFBB86FC)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFAAAAAA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiGeneratorScreen(onBackClick: () -> Unit = {}) {
    var state by remember { mutableIntStateOf(0) } // 0=Input, 1=Loading, 2=Result
    var modelInput by remember {
        mutableStateOf(
            "data class User(\n" +
            "    val id: String,\n" +
            "    val username: String,\n" +
            "    val email: String,\n" +
            "    val role: String\n" +
            ")"
        )
    }
    var selectedFramework by remember { mutableStateOf("Ktor") }
    val frameworks = listOf("Ktor", "FastAPI", "Spring Boot", "Express")
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("API Generator", color = TextPrimary, fontWeight = FontWeight.Bold) },
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
                Text(
                    text = "Generate REST API from Data Models",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "On-device NPU synthesizes complete routing, schema validation, and CRUD endpoints.",
                    color = TextSecondary,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Framework selector
                Text(
                    text = "TARGET FRAMEWORK",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    frameworks.forEach { framework ->
                        val isSelected = selectedFramework == framework
                        Surface(
                            onClick = { selectedFramework = framework },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) AccentCyan.copy(alpha = 0.2f) else CardBg,
                            border = BorderStroke(1.dp, if (isSelected) AccentCyan else CardBorder),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Text(
                                    text = framework,
                                    color = if (isSelected) AccentCyan else TextSecondary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "DATA MODEL DEFINITION",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = modelInput,
                    onValueChange = { modelInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(CardBg, RoundedCornerShape(16.dp)),
                    textStyle = LocalTextStyle.current.copy(
                        color = AccentCyan,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentCyan,
                        unfocusedBorderColor = CardBorder
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        state = 1
                        coroutineScope.launch {
                            delay(2800)
                            state = 2
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.Code, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Synthesize $selectedFramework API", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            } else if (state == 1) {
                Spacer(modifier = Modifier.height(40.dp))
                NeuralThinkingAnimation(color = AccentCyan)
            } else {
                // Result State
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = CardBg,
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Generated $selectedFramework Routes", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("4 Endpoints Synthesized", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                            IconButton(onClick = { /* copy */ }) {
                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = AccentCyan)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val generatedCode = when (selectedFramework) {
                            "FastAPI" -> """
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

app = FastAPI()

class User(BaseModel):
    id: str
    username: str
    email: str
    role: str

db: dict[str, User] = {}

@app.get("/users/{user_id}", response_model=User)
async def get_user(user_id: str):
    if user_id not in db:
        raise HTTPException(404, "User not found")
    return db[user_id]

@app.post("/users", status_code=201)
async def create_user(user: User):
    db[user.id] = user
    return {"status": "created", "id": user.id}
                            """.trimIndent()
                            "Spring Boot" -> """
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        return userService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.save(user));
    }
}
                            """.trimIndent()
                            else -> """
fun Route.userRoutes() {
    route("/users") {
        get {
            val users = userService.getAll()
            call.respond(HttpStatusCode.OK, users)
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = userService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, user)
        }
        post {
            val user = call.receive<User>()
            userService.create(user)
            call.respond(HttpStatusCode.Created, user)
        }
    }
}
                            """.trimIndent()
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF080808),
                            border = BorderStroke(1.dp, Color(0xFF1E1E1E))
                        ) {
                            Text(
                                text = generatedCode,
                                color = Color(0xFF80D8FF),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { state = 0 },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary)
                ) {
                    Text("Generate Another API Schema", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
