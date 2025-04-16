# 🌏 RoamIndia

<div align="center">

![RoamIndia Logo](https://via.placeholder.com/150)

[![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-blue.svg)](https://kotlinlang.org/)  
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)  

</div>

---

## 📱 Overview

**RoamIndia** is a modern, intuitive travel companion app built using Jetpack Compose. It helps users explore Indian states and destinations with rich visuals, live weather updates, and smooth onboarding. Designed with modular architecture and scalability in mind, it's perfect for both travelers and Android developers learning best practices.

---

## ✨ Key Features

### 🌦️ Weather Forecasting
- Real-time weather data for any Indian city
- Shows temperature, humidity, wind speed, visibility, and air quality
- Animated weather conditions and day/night indicators
- Powered by a dynamic API via Retrofit

### 🏞️ Explore Indian States & Places
- Curated list of Indian states with images and short summaries
- Dive deeper into places of interest with detailed cards
- Includes high-quality visuals and "Visit" interactivity

### 🚀 Onboarding Experience
- Smooth Lottie-powered onboarding for first-time users
- State handled with ViewModel for persistence across configuration changes

### 📍 Location Tracking
- Detect and display user’s current coordinates
- Easy integration with Android location services

### 🔍 Custom Search Interface
- Lightweight, real-time city and place search
- Fast filtering and Compose-based clean UI

---

## 🛠️ Technical Architecture

### 🧱 Tech Stack
| Layer            | Tech Used                                 |
|------------------|--------------------------------------------|
| Language         | Kotlin                                     |
| UI               | Jetpack Compose + Material 3               |
| Architecture     | MVVM with clean ViewModel separation       |
| Networking       | Retrofit + Coroutines                      |
| State Management | LiveData, ViewModel                        |
| Animation        | Lottie                                     |
| Build Tool       | Gradle (with Version Catalog)              |

---

### 🧩 Project Structure

```
app/
 ├── MainActivity.kt              # Entry point, sets up navigation
 ├── data/
 │   ├── model/                   # Data classes for Weather, Places, States
 │   ├── repository/              # Data retrieval logic
 │   └── network/                 # Retrofit setup & API interfaces
 ├── ui/
 │   ├── screens/
 │   │   ├── onboarding/          # Onboarding screens and ViewModel
 │   │   ├── weather/             # Weather UI & logic
 │   │   ├── states/              # State explorer screens
 │   │   └── places/              # Place details
 │   ├── components/              # Reusable UI widgets
 │   └── theme/                   # Typography, colors, and theme settings
 ├── utils/                       # Helper methods (e.g., location, formatting)
 └── viewmodel/                   # ViewModels for each screen
```

---

## 🚀 Getting Started

### 📋 Prerequisites
- Android Studio Hedgehog (or higher)
- Android SDK 33+
- Minimum SDK: 23 (Android 6.0)

### 🧪 Installation Steps

1. Clone the repository:
```bash
git clone https://github.com/yourusername/RoamIndia.git
```

2. Open in **Android Studio**

3. Create an `apikey.properties` file in your root directory and add:
```properties
WEATHER_API_KEY="your-api-key-here"
```

4. Sync Gradle and run the app on an emulator or device.

---

## 🔑 API Configuration

RoamIndia integrates with a weather data provider.

1. Sign up at [Weather API Provider]  
2. Retrieve your API key  
3. Add it to `apikey.properties` as:
```properties
WEATHER_API_KEY="your-api-key"
```

---

## 🤝 Contributing

We 💖 contributions!

### 🛤 How to Contribute
1. Fork this repo
2. Create your feature branch:
   ```bash
   git checkout -b feature/my-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add my feature'
   ```
4. Push and open a Pull Request

### ✅ Guidelines
- Follow Kotlin & Compose best practices
- Ensure UI consistency
- Write reusable and modular code
- Include comments and documentation
- Test your changes before pushing

---

## 🐞 Troubleshooting

| Issue                        | Solution                                                           |
|-----------------------------|--------------------------------------------------------------------|
| Weather data not loading    | Check your internet or `WEATHER_API_KEY` setup                    |
| App crashing on startup     | Ensure dependencies are properly synced and API key is valid      |
| UI looks broken             | Use latest Android Studio and stable emulator/device              |

---

## 🙌 Acknowledgements

- Weather data from [Weather API Provider]  
- Indian images and assets from [Unsplash / Pexels / Gov.in Open Data]  
- Icons by [Material Icons / Flaticon]
