# 🌍 RoamIndia – Your Ultimate Travel Companion for India

<div align="center">
  
![RoamIndia Logo](https://via.placeholder.com/150)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue?logo=kotlin)](https://kotlinlang.org/)  
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green?logo=android)](https://developer.android.com/jetpack/compose)  

</div>

---

## 📱 Overview

**RoamIndia** is a sleek, feature-packed Android app crafted to elevate your travel experience across India. Whether you're planning your next adventure or exploring from your couch, RoamIndia provides real-time weather, curated state and place information, beautiful visuals, personalized recommendations, and a seamless user experience powered by Jetpack Compose and modern Android tech.

---

## ✨ Features

### 🌦️ Real-Time Weather Forecasting
- Live weather updates using [WeatherAPI.com](https://www.weatherapi.com/)
- Temperature, humidity, wind speed, visibility, and air quality index
- Dynamic weather icons with day/night indicators
- Smooth and intuitive layout with visual cues

### 🏞️ Explore Indian States & Destinations
- Vibrant cards for all Indian states
- Handpicked attractions and cultural highlights
- Place-specific details with images, names, and descriptions
- Interactive "Visit" action for planning or bookmarking

### 🎯 Smart Recommendation System
- Personalized destination suggestions based on user preferences
- Top 25 curated places across India with key information
- Complete database of attractions with detailed attributes
- Rich place profiles including establishment dates, visiting hours, entry fees, and more
- Filter by zone, state, city, type, or other criteria
- Beautiful card-based UI with immersive imagery

### 🚀 Onboarding Experience
- Elegant, Lottie-powered onboarding screen
- ViewModel-backed state management
- Highlights app features on first launch

### 📍 Location Detection
- Fetches real-time latitude and longitude
- Ready for future integrations like local suggestions or maps

### 🔍 Smart Search Interface
- Responsive and beautiful search screen
- Type any city or place to get relevant real-time results
- Efficient filtering using Compose state handling

---

## 🛠️ Tech Stack

| Layer              | Technologies Used                                |
|--------------------|--------------------------------------------------|
| **Language**       | Kotlin                                          |
| **UI**             | Jetpack Compose with Material 3                  |
| **Architecture**   | MVVM (Model-View-ViewModel)                      |
| **Networking**     | Retrofit + Kotlin Coroutines                     |
| **Animations**     | Lottie                                           |
| **State Mgmt.**    | LiveData + ViewModel                             |
| **Data Storage**   | Pickle (for recommendation system data)          |
| **Web Interface**  | Flask (for recommendation system prototype)      |
| **Build System**   | Gradle (Version Catalog + Modular Setup)         |

---

## 🧩 Project Structure

```
RoamIndia/
 └── app/
     ├── MainActivity.kt              # App launch & navigation host
     ├── data/
     │   ├── model/                   # Data classes for states, places, weather
     │   ├── repository/              # Repository for data sources
     │   └── network/                 # Retrofit APIs and response handlers
     ├── ui/
     │   ├── screens/
     │   │   ├── onboarding/          # Lottie animation + intro slides
     │   │   ├── weather/             # Weather screen
     │   │   ├── states/              # State list and detail
     │   │   ├── places/              # Place detail cards
     │   │   └── recommendations/     # Recommendation system UI
     │   ├── components/              # Reusable Composables
     │   └── theme/                   # Theme, colors, typography
     ├── viewmodel/                   # ViewModels for each feature
     └── utils/                       # Helper functions (e.g., location)
 └── recommendation_system/           # Flask-based prototype
     ├── app.py                       # Flask app for recommendation system
     ├── templates/                   # HTML templates
     │   ├── index.html               # Top 25 places display
     │   ├── allPlaces.html           # Complete places catalog
     │   └── place_details.html       # Detailed place information
     └── data/                        # Data files for recommendations
         ├── popular.pkl              # Top-rated destinations
         └── all.pkl                  # Complete attraction database
```

---

## 📊 Recommendation System Details

The recommendation system helps users discover the perfect destinations across India with:

### Key Features
- **Top 25 Places**: Curated list of must-visit attractions with rating, location, and images
- **Complete Destination Catalog**: Browse all attractions with comprehensive filtering options
- **Detailed Place Profiles**: Rich information cards containing:
  - Visit duration and best time recommendations
  - Entry fees and weekly closure information
  - Historical significance and establishment dates
  - Camera policies and nearby airport information
  - Visitor ratings and review counts

### Data Sources
The recommendation system leverages rich datasets with multiple attributes for each destination:
- Geographic data (Zone, State, City)
- Type classification (Temple, Beach, Fort, etc.)
- Historical information (Establishment year)
- Practical visitor information (Visiting hours, fees, best times)
- Travel planning data (Nearby airports, photography policies)

### Implementation
Currently prototyped as a Flask web application, the recommendation system will be fully integrated into the RoamIndia Android app with:
- Native Jetpack Compose UI matching the app's design language
- Offline-first data availability for key destinations
- Personalized recommendations based on user preferences and behavior

---

## 🔧 Installation Guide

### 📋 Prerequisites
- Android Studio Hedgehog or later
- Kotlin 1.9+
- Android SDK 33+
- Minimum SDK: 23

### 🚀 Steps

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/RoamIndia.git
```

2. **Open in Android Studio**

3. **Create a file named `apikey.properties`** in the root of the project:
```properties
WEATHER_API_KEY="your-weatherapi-key-here"
```

4. **Build and run** the project on an emulator or device:
```bash
./gradlew build
```

---

## ☁️ Weather API Configuration

RoamIndia uses [WeatherAPI.com](https://www.weatherapi.com/) to fetch real-time weather data.

### Setup:
1. Register at [https://www.weatherapi.com](https://www.weatherapi.com)
2. Get your API key from the dashboard
3. Add it to `apikey.properties`:
```properties
WEATHER_API_KEY="your-key"
```
4. The app automatically injects this key via `local.properties` setup in Gradle.

---

## 🤝 Contributing

We're open to collaboration! RoamIndia is perfect for learning modern Android development or adding exciting features.

### 🧑‍💻 How to Contribute
1. Fork the repo
2. Create a new branch:
```bash
git checkout -b feature/amazing-idea
```
3. Make your changes and commit:
```bash
git commit -m "Added awesome feature"
```
4. Push and create a Pull Request

### 📌 Contribution Checklist
- Follow Kotlin best practices
- Keep UI consistent and reusable
- Add helpful comments and KDocs
- Test thoroughly before pushing

---

## 🐞 Troubleshooting

| Problem                            | Solution                                                       |
|------------------------------------|----------------------------------------------------------------|
| Weather not loading                | Double-check API key and internet connection                   |
| App crashes on launch              | Ensure all Gradle syncs are successful and dependencies up-to-date |
| UI rendering issues                | Update to latest stable Jetpack Compose and Android Studio     |
| Recommendation data not appearing  | Verify pickle files are correctly placed in assets folder      |

---

## 📜 License

NIL

---

## 🙌 Acknowledgements

- **Weather API**: [WeatherAPI.com](https://www.weatherapi.com/)  
- **Icons**: [Material Icons](https://fonts.google.com/icons), [Flaticon](https://www.flaticon.com)  
- **Images**: [Unsplash](https://unsplash.com/), [Pexels](https://pexels.com)  
- **Jetpack Compose Team** for making UI fun again!

---

> 🧭 *RoamIndia is a journey through incredible India — one click at a time.*
