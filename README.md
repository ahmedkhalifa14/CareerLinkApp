CareerLinkApp - Job Finder Android Application
CareerLinkApp is a modern Android application designed to help users find, save, and apply for jobs seamlessly. Built with a clean and intuitive UI, it supports online and offline job browsing, user authentication, job application notifications, and multilingual support (English and Arabic). The app is ideal for job seekers looking for a reliable platform to explore career opportunities.
Main Features
Authentication

Secure user authentication using Firebase Authentication (Email/Password, Google Sign-In).
Users can create accounts and save personal details (name, photo, address) to Firebase Firestore.

Job Listings

Fetches and displays job listings from a remote API when online.
Offline caching using Room Database to ensure job access without internet.
Users can save favorite jobs to local storage for quick access.

Job Applications

Users can apply for jobs directly through the app.
Sends push notifications upon successful job applications.

User Experience

Splash Screen: A welcoming screen for a smooth app launch.
Onboarding Screen: Guides new users through the app’s features.
Dark/Light Mode: Supports both themes based on user preference or system settings.
Multilingual Support: Available in English and Arabic.

Demo Video
Below is a video showcasing the app's features and user interface:
[Insert Video Link Here]
Note: To add a video, upload it to a platform like YouTube or Vimeo, or host it in the GitHub repository (e.g., in the assets/ folder). Replace [Insert Video Link Here] with the video URL or embed code. For example:

YouTube: https://www.youtube.com/watch?v=your-video-id
GitHub: <video src="https://github.com/ahmedkhalifa14/CareerLinkApp/raw/main/assets/demo.mp4" controls width="600"></video>

App Architecture
CareerLinkApp follows Clean Architecture with the MVVM (Model-View-ViewModel) pattern and Repository Pattern to ensure:

Separation of concerns.
Scalability and maintainability.
Easy testing and debugging.

The app is a multi-module project divided into three modules:

UI Module (App Module): Handles the presentation layer with Jetpack Compose for UI and ViewModels for data management.
Data Module: Contains business logic, data sources (Remote API, Room, DataStore, Firebase), and repositories.
Domain Module: Encapsulates complex business logic and use cases, ensuring reusability and modularity.

Tools and Techniques

Kotlin: Primary programming language.
Jetpack Compose: Modern toolkit for building native UI.
Clean Architecture: Promotes modular, maintainable, and testable code.
Modularization: Organizes the codebase into UI, Data, and Domain modules.
MVVM: Separates UI from business logic.
Repository Pattern: Isolates data layer from the app.
Hilt: Dependency injection for managing dependencies.
Room: Local database for caching job listings.
DataStore: Stores user preferences (e.g., theme, language).
Firebase:
Firebase Authentication: For user sign-up and login.
Firestore: For storing user profiles.


Retrofit: HTTP client for fetching job listings.
OkHttp: Efficient HTTP networking.
Kotlin Coroutines: For asynchronous programming.
Flow: For reactive data streams.
StateFlow: For state management.
Navigation Component: For in-app navigation.
Coil: For image loading.
Timber: For logging.
SplashScreen API: For modern splash screen support.

Installation
Follow these steps to set up and run the project locally:
Prerequisites

Android Studio (latest stable version recommended).
Android SDK (API 33 or higher).
A Firebase project with Authentication and Firestore enabled.

Steps

Clone the repository:
git clone https://github.com/ahmedkhalifa14/CareerLinkApp.git


Open the project:

Open Android Studio and select Open an existing project.
Navigate to the cloned CareerLinkApp folder and open it.


Set up Firebase:

Create a Firebase project in the Firebase Console.
Add an Android app to your Firebase project and download the google-services.json file.
Place the google-services.json file in the app/ directory.


Add API keys (if applicable):

If the job listings API requires an API key, add it to local.properties or a configuration file as specified.


Sync the project:

Click Sync Project with Gradle Files in Android Studio to download dependencies.


Build and run:

Connect an Android device or start an emulator.
Click Run to build and install the app.



Usage

Launch the app:
The app starts with a splash screen, followed by an onboarding flow for first-time users.


Sign up or log in:
Use Firebase Authentication to create an account or sign in with Google.


Complete your profile:
Add your name, photo, and address, which will be saved to Firestore.


Browse jobs:
View job listings fetched from the API (cached locally for offline access).


Save or apply for jobs:
Save jobs to your favorites (stored in Room).
Apply for a job and receive a notification.


Customize settings:
Switch between dark/light mode or change the language (English/Arabic).



Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Make your changes and commit (git commit -m 'Add some feature').
Push to the branch (git push origin feature/your-feature).
Open a Pull Request with a detailed description of your changes.

Please ensure your code follows the project's coding standards and includes appropriate tests.
License
This project is licensed under the MIT License - see the LICENSE file for details.

