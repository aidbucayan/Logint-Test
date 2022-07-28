# Logint-Test


Android project written in Kotlin which demonstrates MVVM/MVI design architecture.

- Retrieve data from an api with Retrofit
- Display data in UI.

Design Pattern
The project used MVI and Repository design pattern approach. State in app is defined by user's action which is called intent (not the android Intent class) which the ViewModel will get and decide the state to be reflected to the View. Intent represents an intention or a desire to perform an action, either by the user or the app itself. For every action, a View receives an Intent. The Presenter observes the Intent, and Models translate it into a new state.

# Libraries
- [Jetpack Hilt](https://dagger.dev/hilt/) - Dependency injection
- [Retrofit](https://square.github.io/retrofit/)  - API http network requests.
- [OkHttp](https://square.github.io/okhttp/) - Use as http client for logging interceptor.
- [Jackson](https://github.com/square/retrofit/tree/master/retrofit-converters/jackson) - JSON serialization.
- [Timber](https://github.com/JakeWharton/timber) - Logging and crash reports.
- [Coil](https://github.com/coil-kt/coil) - Image loader to views.
- [Material](https://material.io/) Design - Google's material design ui.  
- [Coroutine](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAjwq5-WBhB7EiwAl-HEkqzFPUj-fstYu_CElohZ5kFNqOibnSiuBhHc-bRiO9yxtm4YNqMm5hoCRY4QAvD_BwE&gclsrc=aw.ds) A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.                                                                                      
- [Coroutine Flow](https://developer.android.com/kotlin/flow) - In coroutines, a flow is a type that can emit multiple values sequentially.
- [Unit Test](https://developer.android.com/training/testing/local-tests) - Basic unit test for testing the Ulits function in the app.
- [Room Database](https://developer.android.com/training/data-storage/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [Google Truth](https://github.com/google/truth) - Truth makes your test assertions and failure messages more readable.
- [MapBox](https://www.mapbox.com/) - The Mapbox Maps SDK for Android is an open-source toolset for building mapping applications for Android devices. The SDK gives you flexibility for visual styling and customizability.
