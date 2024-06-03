# Exzi Project

## Overview
The Exzi project is a sample application developed for showcase proficiency in Android development using modern technologies and architectural patterns. The project demonstrates the implementation of the MVVM architecture, use cases, dependency injection with Hilt, and unit testing.

## Technologies and Architectural Patterns

### MVVM (Model-View-ViewModel)
The project follows the MVVM architectural pattern to separate the concerns of the UI (View) from the business logic (ViewModel) and data (Model). This helps in achieving a cleaner and more maintainable codebase.

- **View**: Includes `Fragment` classes like `HomeFragment` and `TradeFragment` that handle the UI logic and interactions.
- **ViewModel**: Includes `HomeViewModel` and `TradeViewModel` classes that hold the business logic and interact with the use cases.
- **Model**: Includes data models such as `CandleResponseModel` and `OrderBookResponseModel` that represent the data structure.

### Use Cases
The project uses the use case pattern to encapsulate the application's business logic. This helps in making the business logic reusable and testable.

- **GetCandlesUseCase**: Handles the fetching of candle data.
- **GetOrderBookUseCase**: Manages the retrieval of order book data.

### Dependency Injection with Hilt
Hilt is used for dependency injection to manage the creation and provision of dependencies. This helps in writing more modular and testable code.

- **Modules**: The project defines Hilt modules to provide dependencies like network clients and repositories.
- **Annotations**: Classes like `HomeViewModel` and `TradeViewModel` are annotated with `@HiltViewModel` to enable Hilt's injection.

### Unit Testing
Unit tests are written to ensure the correctness of the business logic and the ViewModel classes.

- **JUnit**: Used for writing and running unit tests.
- **Mockito**: Used for mocking dependencies in tests.
- **Core Testing**: `androidx.arch.core:core-testing` is used for testing LiveData and ViewModel components.

## Dynamic Data Fetching
The application dynamically fetches data every 3 seconds to ensure the latest prices are displayed. This is achieved using Coroutines in the HomeViewModel and TradeViewModel classes.
``` 
viewModelScope.launch {
    while (isActive) {
        fetchCandles()
        delay(3000)
    }
}
``` 

## Project Structure
```
com.muhammedalikocabey.exzi
├── core
│ ├── adapters
│ └── utils
├── data
│ ├── model
│ ├── network
│ └── remote
├── domain
│ ├── repository
│ └── usecases
├── presentation
│ ├── base
│ ├── home
│ ├── trade
│ └── viewmodels
├── di
│ └── AppModule.kt
└── ExziApp.kt
``` 

## Setup Instructions

    Clone the repository:
    git clone https://github.com/muhammedalikocabey/Exzi_CryptoCurrencyTrade_AndroidApp/
    Open the project in Android Studio.
    Sync the project with Gradle files.
    Run the application on an emulator or a physical device.


Dependencies

    MPAndroidChart: com.github.PhilJay:MPAndroidChart:v3.1.0
    
    SwipeRefreshLayout: androidx.swiperefreshlayout:swiperefreshlayout:1.1.0
    
    Kotlin Serialization: org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2
    
    AndroidX Libraries: Core KTX, AppCompat, Material, ConstraintLayout
    
    Navigation Components: androidx.navigation:navigation-fragment-ktx:2.7.6, 
        androidx.navigation:navigation-ui-ktx:2.7.6
    
    ViewModel and LiveData: androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0, 
        androidx.lifecycle:lifecycle-livedata-ktx:2.7.0
    
    DataStore: androidx.datastore:datastore-preferences:1.1.0-beta01
    
    Retrofit: com.squareup.retrofit2:retrofit:2.9.0, 
        com.squareup.retrofit2:converter-gson:2.9.0
    
    Dagger Hilt: com.google.dagger:hilt-android:2.49, 
        com.google.dagger:hilt-compiler:2.49
    
    Testing: JUnit, Mockito, AndroidX Test

