# RoamIndia

<div align="center">
  
  ![RoamIndia Logo](https://via.placeholder.com/150)
  
  [![Kotlin](https://img.shields.io/badge/kotlin-1.9.0-blue.svg)](https://kotlinlang.org/)
  [![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)
  [![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
  
</div>

## 📱 Overview

**RoamIndia** is a comprehensive travel companion application designed to enhance your exploration of India. Built with modern Android development practices, the app provides real-time weather updates, detailed information about Indian states and tourist destinations, all presented through an intuitive and engaging user interface.

## ✨ Key Features

### 🌦️ Weather Forecasting
- **Real-time weather data** for any Indian city
- Comprehensive metrics including temperature, humidity, wind speed, visibility
- Visual weather condition indicators and day/night status
- Powered by dynamic API integration

### 🏞️ State & Place Exploration
- Curated list of Indian states with vibrant imagery and concise descriptions
- Detailed exploration of places of interest within each state
- Rich visual content with location details
- Interactive 'Visit' functionality for deeper discovery

### 🚀 Onboarding Experience
- Smooth, visually engaging introduction for first-time users
- State preservation using ViewModel architecture
- Intuitive navigation through app features

### 🔍 Search Capabilities
- Custom-designed search interface for city and place discovery
- Real-time results with state management
- Optimized for quick access to information

## 🛠️ Technical Architecture

### Tech Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose with Material 3 design
- **Architecture Pattern:** MVVM (Model-View-ViewModel)
- **Networking:** Retrofit with Coroutines
- **State Management:** LiveData integrated with ViewModel
- **Build System:** Gradle

### Project Structure
```
app/
 ├── src/main/java/com/roamindia/travel/app/
 │   ├── MainActivity.kt          # App entry point
 │   ├── di/                      # Dependency injection
 │   ├── model/                   # Data models
 │   │   ├── weather/
 │   │   ├── places/
 │   │   └── states/
 │   ├── network/                 # API services
 │   │   ├── WeatherApiService.kt
 │   │   └── ApiResponse.kt
 │   ├── repository/              # Data repositories
 │   ├── ui/
 │   │   ├── screens/             # Compose screens
 │   │   │   ├── onboarding/
 │   │   │   ├── weather/
 │   │   │   ├── states/
 │   │   │   └── places/
 │   │   ├── components/          # Reusable UI components
 │   │   └── theme/               # App styling
 │   ├── utils/                   # Utility functions
 │   └── viewmodel/               # ViewModels
 │       ├── WeatherViewModel.kt
 │       ├── StateViewModel.kt
 │       ├── PlaceViewModel.kt
 │       └── OnboardingViewModel.kt
 └── res/                         # Resources
```

## 📲 Installation

### Prerequisites
- Android Studio Arctic Fox (2021.3.1) or higher
- Minimum SDK: Android 6.0 (API level 23)
- Target SDK: Android 13 (API level 33)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/RoamIndia.git
   ```

2. Open the project in Android Studio.

3. Configure the Weather API key:
   - Create a file named `apikey.properties` in the root project directory
   - Add your API key:
     ```properties
     WEATHER_API_KEY="your-api-key-here"
     ```

4. Sync Gradle and build the project.

5. Run on an emulator or physical device.

## ⚙️ API Configuration

RoamIndia uses a weather API for fetching real-time weather data. To configure:

1. Obtain an API key from [Weather API Provider]
2. Add it to the `apikey.properties` file as described in the installation section
3. The app automatically handles authentication and data fetching

## 🤝 Contributing

Contributions are welcome! Feel free to improve RoamIndia:

1. Fork the repository
2. Create a feature branch:
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. Commit your changes:
   ```bash
   git commit -m 'Add some amazing feature'
   ```
4. Push to the branch:
   ```bash
   git push origin feature/amazing-feature
   ```
5. Open a Pull Request

### Contribution Guidelines
- Follow Kotlin coding conventions
- Add appropriate documentation
- Ensure UI consistency with existing design
- Include tests for new functionality

## 🐛 Troubleshooting

- **Weather data not loading?** Check your API key and internet connection
- **UI issues?** Ensure you're using the latest version of Jetpack Compose
- **Build failures?** Update Android Studio and sync Gradle

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgements

- Weather data provided by [Weather API Provider]
- Images sourced from [Image Source]
- Icon assets from [Icon Library]
