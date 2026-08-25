# 🔱 Neuron — Multi-Modal On-Device AI Developer Toolkit

> **iQOO Hackathon 2026 | Track 06: Developer Tools**

*Your phone sees code differently.*

Neuron turns your phone's unique sensors — **camera, microphone, touchscreen** — into developer superpowers. Every feature runs 100% on-device via the Snapdragon 8 Elite NPU. Zero cloud. Zero leaks.

## ✨ Features

| # | Feature | Input | Output |
|---|---------|-------|--------|
| 📸 | **Sketch to Code** | Camera → Wireframe photo | Jetpack Compose / HTML code |
| 🎙️ | **Voice to Code** | Microphone → Spoken logic | Working code in any language |
| 🐛 | **Visual Bug Reporter** | Screenshot | Structured bug report |
| 📝 | **Handwriting to Tasks** | Camera → Meeting notes | Structured dev tasks with priorities |
| 🔍 | **Code Explainer** | Import code file | Explanation, complexity, smells, fixes |
| 🏗️ | **Architecture Diagrams** | Import project | Dependency graphs, class diagrams |
| 🔐 | **Privacy Vault** | — | AES-256 encrypted history & export |

## 🧠 AI Models (All On-Device)

| Model | Size | Purpose |
|-------|------|---------|
| Gemma-3n E2B | ~1.2 GB (INT4) | Vision + Text generation |
| Gemma-3n E4B | ~2.3 GB (INT4) | Deep code analysis |
| Whisper Small | ~50 MB (INT8) | Speech-to-text |
| ML Kit Text Rec v2 | Built-in | OCR for handwriting |

## 🛠️ Tech Stack

- **Language**: Kotlin + Jetpack Compose (Material 3)
- **AI Runtime**: MediaPipe LLM Inference / LiteRT-LM (Snapdragon NPU)
- **Camera**: CameraX
- **Database**: Room DB (AES-256 encrypted)
- **Auth**: Google Sign-In + Firebase
- **Preferences**: DataStore

## 🚦 Red Light / Green Light Ready

- 🔴 **Red Light**: All 7 features work fully on the phone — camera, mic, touch
- 🟢 **Green Light**: Import codebases from laptop via iQOO Office Kit

## 👥 Contributors & Core Team

| Name | Role | GitHub |
|------|------|--------|
| **Naman Raghav** | Android Lead, Debugging | [@NAMAN121208](https://github.com/NAMAN121208) |
| **Chezhil** | AI/ML, Complex Problem Solving | [@chezhil](https://github.com/chezhil) |
| **Saksham** | Product, UI/UX, Prompt Engineering | [@saksham-2x7](https://github.com/saksham-2x7) |

## 📂 Project Structure

```
app/src/main/java/com/prism/app/
├── MainActivity.kt
├── NeuronApp.kt
├── auth/
│   └── GoogleAuthManager.kt
├── data/
│   └── PreferencesManager.kt
├── engine/              # AI inference engines (WIP)
├── ui/
│   ├── components/
│   │   ├── CodeBlock.kt
│   │   ├── FeatureCard.kt
│   │   └── NeuronTopBar.kt
│   ├── navigation/
│   │   └── NeuronNavHost.kt
│   ├── screens/
│   │   ├── LoginScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── SketchToCodeScreen.kt
│   │   ├── VoiceToCodeScreen.kt
│   │   ├── BugReporterScreen.kt
│   │   ├── HandwritingToTasksScreen.kt
│   │   ├── CodeExplainerScreen.kt
│   │   ├── ArchitectureDiagramScreen.kt
│   │   ├── PrivacyVaultScreen.kt
│   │   └── SettingsScreen.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
```

## 🎬 Demo Presentation

Open `prism-video-deck.html` in a browser for a cinematic 20-slide product walkthrough. Screen-record it for your submission video.

## Tagline

> *Your code. Your phone. Your vision.*
