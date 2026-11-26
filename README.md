# WeatherApp

An Android app written in **Kotlin** that shows the current weather for any coordinates using the
[OpenWeather Current Weather API](https://openweathermap.org/current).

![WeatherApp screenshot](https://user-images.githubusercontent.com/39758424/51750709-d981a100-20ed-11e9-9af3-cd9006e829ef.png)

## Features

- Enter a latitude and longitude and fetch the current conditions
- Shows the weather description, current, minimum and maximum temperature in °C, and the condition icon
- Validates the input and checks for a network connection before calling the API
- Typed response models (`WeatherData`, `Main`, `Wind`, …) parsed with Gson

## Tech stack

Kotlin · Android SDK · Retrofit 2 · Gson · Data Binding

## Project structure

```
app/src/main/java/com/example/weatherapp/
├── MainActivity.kt      # UI, input validation and the API call
├── WeatherService.kt    # Retrofit interface for /data/2.5/weather
└── WeatherData.kt, Main.kt, Wind.kt, ...   # response models
```

## API key

The OpenWeather key is **not** stored in the repository. Get a free key at
<https://home.openweathermap.org/api_keys> and add it to `local.properties` (already git-ignored) in the project root:

```properties
OPEN_WEATHER_API_KEY=your_key_here
```

The build exposes it to the app as `BuildConfig.OPEN_WEATHER_API_KEY`.

## Getting started

1. Clone the repository and open the project folder in Android Studio.
2. Let Android Studio sync Gradle. The project was written in 2019 against Android Gradle Plugin 3.x and the
   Support Library, so Android Studio may offer to upgrade the Gradle wrapper and plugin; accept the
   suggested upgrades if the sync fails on a recent IDE.
3. Run the `app` configuration on an emulator or device.

## License

[MIT](LICENSE)
