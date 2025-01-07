## App Overview
The Currency Converter app allows users to convert amounts between different currencies. 

Utilized: MVVM architecture, Retrofit, LiveData, Coroutines, Flow, Dagger Hilt.
## Architecture Overview

### 1. Model (model)
- **Data Classes**: Represent the data structure of the API responses.
  - `ApiResponse`
  - `Rates`

### 2. View (view)
- **Activities**: Handle the UI and user interactions.
  - `MainActivity`
- **XML Layouts**: Define the UI components.
  - `activity_main.xml`

### 3. ViewModel (viewmodel)
- **ViewModel**: Manages UI-related data and handles business logic.
  - `MainViewModel`
- **Repository**: Acts as a single source of truth for data.
  - `MainRepository`

### 4. Network (network)
- **API Service**: Defines the API endpoints.
  - `ApiService`
- **Data Source**: Handles API calls and data fetching.
  - `ApiDataSource`
  - `BaseDataSource`

### 5. Dependency Injection (di)
- **Dagger Hilt**: Manages dependency injection.
  - `AppModule`
  - `MyApplication`

### 6. Helpers
- **Utility Classes**: Provide common functionalities.
  - `EndPoints`
  - `Resource`
  - `SingleLiveEvent`
  - `Utility`

## Challenges encountered
- Learning how to properly set up for the DI, Coroutines and Flow.
## Steps to Build and Run
1. **Clone the Repository:**
    ```sh
    git clone https://github.com/kitepea/Currency-Converter.git
    ```
2. **Configure API Key:**
    - Create a file name `apikeys.properties` inside `Currency-Converter` folder.
    - Go to [IP GEO API](https://getgeoapi.com/), create an account and get an API key.
    - Insert your API key in the `apikeys.properties` file as follows:
    ```API_KEY="YOUR_API_KEY"```
3. **Open the Project in Android Studio:**
    - Launch Android Studio.
    - Select **File/Open**.
    - Navigate to the `Currency-Converter` directory and open it.
    - Wait for the Gradle sync to finish.
5. **Build the Project:**
    - Click on **Build** in the top menu.
    - Select **Make Project** to compile the code.
6. **Run the App:**
    - Connect an Android device or start an emulator.
    - Click on the **Run** button (green play icon) in the toolbar.
    - Select the target device and click **OK**.

## App Demo
https://github.com/user-attachments/assets/eb3a62c9-c5d0-4db6-8f83-87b87190339f

Current device: Pixel 9 API 33
