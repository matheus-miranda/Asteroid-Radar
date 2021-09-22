# Project Title

Asteroid Radar

## Getting Started

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

The app is consists of two screens: A Main screen with a list of all the detected asteroids and a Details screen that is going to display the data of that asteroid once it´s selected in the Main screen list. The main screen will also show the NASA image of the day.

It can filter by weekly, daily or all of the saved asteroids. Accessibility enabled for Google Talkback.

### Screenshots

<!--
![Screenshot 1](screenshots/screen_1.png)
![Screenshot 2](screenshots/screen_2.png)
![Screenshot 3](screenshots/screen_3.png)
![Screenshot 4](screenshots/screen_4.png)
-->

<table style="width:100%">
  <tr>
    <td><img src="screenshots/screen_1.png"></td>
    <td><img src="screenshots/screen_2.png"></td>
    <td><img src="screenshots/screen_3.png"></td>
    <td><img src="screenshots/screen_4.png"></td>
  </tr>
</table>

### Dependencies

```
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'androidx.appcompat:appcompat:1.3.1'
    implementation 'androidx.core:core-ktx:1.6.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.0'

    // Lifecycle
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
    implementation 'androidx.fragment:fragment-ktx:1.3.6'

    // Navigation
    implementation "androidx.navigation:navigation-fragment-ktx:2.3.5"
    implementation "androidx.navigation:navigation-ui-ktx:2.3.5"

    // Moshi
    implementation "com.squareup.moshi:moshi:1.9.3"
    implementation "com.squareup.moshi:moshi-kotlin:1.9.3"

    // Retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-moshi:2.9.0"
    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'
    
    // OkHttp
    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.1'

    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"
    implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

    // RV
    implementation "androidx.recyclerview:recyclerview:1.2.1"

    // Picasso
    implementation 'com.squareup.picasso:picasso:2.5.2'

    // Room DB
    implementation "androidx.room:room-runtime:2.3.0"
    kapt "androidx.room:room-compiler:2.3.0"
    implementation "androidx.room:room-ktx:2.3.0"

    // Work Manager
    implementation "androidx.work:work-runtime-ktx:2.6.0"
    
    // Reflect
    implementation "org.jetbrains.kotlin:kotlin-reflect:1.5.30"

    // Koin
    implementation 'io.insert-koin:koin-android:3.1.2'
    implementation 'io.insert-koin:koin-android-ext:3.0.2'

    // Timber
    implementation 'com.jakewharton.timber:timber:5.0.1'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
```

### Installation

To get the project running on your local machine, you need to add the NASA API Key in local.properties `apiKey=ADD_KEY_HERE`, in the root of the project. You can get your own key in the link below or define "DEMO_KEY" which permits a limited number of requests.

## Built With

https://api.nasa.gov/
