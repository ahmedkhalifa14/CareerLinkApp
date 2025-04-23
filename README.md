
# 🚀 CareerLinkApp
### Your Smart Android Job Finder App

CareerLinkApp is your all-in-one job search companion! 🚀 Whether you're hunting for your first job or your next big opportunity, this modern Android app makes it seamless to discover, save, and apply to jobs with an elegant UI and robust features like offline support, push notifications, and more!

---

## 📌 Table of Contents
- [🏁 Quick Start](#-quick-start)
- [✨ Features](#-features)
- [🎥 Demo Video](#-demo-video)
- [🏗️ Architecture](#-architecture)
- [🧰 Tech Stack](#-tech-stack)
- [⚙️ Installation Guide](#-installation-guide)
- [📱 How to Use](#-how-to-use)
- [🤝 Contributing](#-contributing)

---

## 🏁 Quick Start
```bash
git clone https://github.com/ahmedkhalifa14/CareerLinkApp.git
```
1. Open in Android Studio
2. Add `google-services.json` to `app/`
3. Sync Gradle & Run ✅

> 💡 Check [Installation Guide](#-installation-guide) for full setup

---

## ✨ Features
| Feature           | Description                                                               | Tech Used                      |
|------------------|---------------------------------------------------------------------------|-------------------------------|
| 🔐 Authentication | Email/Google sign-in, profile storage in Firestore                      | Firebase Auth, Firestore      |
| 🔎 Job Search     | Real-time + offline browsing using Remotive API & Room                  | Retrofit, Room, Coroutines    |
| ⭐ Favorites      | Save jobs locally for offline access                                    | Room, DataStore               |
| 📩 Notifications  | Push notifications for job applications                                 | Local Notification
|
| 🎉 Splash Screen  | Smooth launch experience                                                | SplashScreen 
|
| 🚀 Onboarding     | Intro tutorial for new users                                            | Jetpack Compose, Navigation  | 
| 🌙 Theme Switch   | Dark & Light mode                                                        | DataStore, Compose           |
| 🌍 Multilingual   | English 🇬🇧 and Arabic 🇦🇪 support                                       | DataStore, Compose            |

---

## 🎥 Demo Video


---

## 🏗️ Architecture
Clean, modular, and scalable using:
- **Clean Architecture**
- **MVVM**
- **Repository Pattern**

### Layers
- **Presentation**: Jetpack Compose UI + ViewModels
- **Data**: API, Firebase, Room, DataStore

### Modules
- **App**: UI Layer
- **Data**: APIs + local storage

---

## 🧰 Tech Stack
| Tool              | Purpose                          | Docs |
|-------------------|----------------------------------|------|
| Kotlin            | Modern language for Android      | [Docs](https://kotlinlang.org) |
| Jetpack Compose   | Declarative UI                   | [Docs](https://developer.android.com/jetpack/compose) |
| Hilt              | Dependency Injection             | [Docs](https://dagger.dev/hilt) |
| Room              | Offline database                 | [Docs](https://developer.android.com/jetpack/androidx/releases/room) |
| Firebase          | Auth, Firestore,                 | [Docs](https://firebase.google.com/docs) |
| Retrofit + OkHttp |                                  | [Docs](https://square.github.io/retrofit/) |
| DataStore         | Preference storage               | [Docs](https://developer.android.com/topic/libraries/architecture/datastore) |
| Coil              | Image loading                    | [Docs](https://coil-kt.github.io/coil/) |
| Navigation Compose| In-app navigation                | [Docs](https://developer.android.com/jetpack/compose/navigation) |

---

## ⚙️ Installation Guide
### ✅ Requirements
- Android Studio Giraffe+
- Android SDK API 33+
- Firebase project with Auth & Firestore enabled

### 🚀 Steps
1. Clone the repo
2. Open in Android Studio
3. Add `google-services.json` in `app/`
4. Add Remotive API Key to `local.properties` (if required)
5. Sync & build the project
6. Run on device/emulator

---

## 📱 How to Use
1. **Launch App** – Starts with splash screen and onboarding
2. **Login/Register** – Email or Google account
3. **Setup Profile** – Add name, image & address (synced to Firestore)
4. **Browse Jobs** – Online or offline
5. **Save/Apply** – Save to favorites or apply instantly
6. **Customize Settings** – Switch language and theme

---

## 🤝 Contributing
We welcome contributions! 🎉

1. Fork the repo
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m "Add feature"`
4. Push and open a PR ✅

> Follow best practices & include tests when possible

---

⭐ Enjoyed CareerLinkApp? Star the repo and share it! Have questions or ideas? Open an issue or reach out!

