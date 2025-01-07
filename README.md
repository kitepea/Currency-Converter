## App Overview
The Currency Converter app allows users to convert amounts between different currencies. The app uses a ViewModel to manage data and perform network operations, and it observes changes in the data to update the UI accordingly.

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

Current device: Pixel 9 API 33