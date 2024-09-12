# Kotlin MVVM Project with Coroutines, Koin, and Retrofit

## Overview

This project is a Kotlin-based application that utilizes the MVVM architecture along with modern libraries and frameworks including Coroutines, Koin, Retrofit, Moshi, and Glide. 

The application fetches data from a remote API and displays it in a UI that handles various types of sections, such as banners, horizontal image items, and split image items.

## Features

- **MVVM Architecture**: Separates concerns to make the codebase scalable and maintainable.
- **Kotlin Coroutines**: For handling asynchronous operations.
- **Koin**: A dependency injection framework for managing dependencies.
- **Retrofit**: For network operations and API calls.
- **Moshi**: For JSON serialization and deserialization.
- **Glide**: For efficient image loading and caching.
- **JUnit with Mocking**: For unit testing with mock data.

## UI Description

The UI is designed to handle different types of content:
- **Banner**: Displays a full-width image.
- **Horizontal Screen Item**: Displays a horizontal list of images.
- **Split Image Item**: Displays two images equally divided across the screen.

## Libraries Used

- **Kotlin Coroutines**: `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1`
- **Koin**: `io.insert-koin:koin-android:3.4.0`
- **Retrofit**: `com.squareup.retrofit2:retrofit:2.9.0`
- **Moshi**: `com.squareup.moshi:moshi:1.13.0`
- **Glide**: `com.github.bumptech.glide:glide:4.15.1`
- **JUnit**: `junit:junit:4.13.2`
- **Mockito**: `org.mockito:mockito-inline:4.10.0`
- **Kotlin Coroutines Test**: `org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1`
- **Arch Core Testing**: `androidx.arch.core:core-testing:2.2.0`

## Setup

1. **Clone the Repository**

   ```bash
   git clone https://github.com/chandankumar2517/zapcom-assignment.git
   ```

2. **Navigate to the Project Directory**

   ```bash
   cd zapcom-assignment
   ```

3. **Build the Project**

   Use Gradle to build the project.

   ```bash
   ./gradlew build
   ```

4. **Run the Application**

   Follow your usual procedure for running an Android project in Android Studio.

## Testing

To run unit tests:

```bash
./gradlew test
```

Unit tests are written using JUnit and Mockito for mocking. The `kotlinx-coroutines-test` library is used for testing coroutines.

## API Integration

The project uses Retrofit to handle API calls and Moshi for JSON parsing. The API responses are handled by the ViewModel and displayed in the UI.

## Contributing

Feel free to fork the repository and submit pull requests. For any issues or feature requests, please open an issue on the repository.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.