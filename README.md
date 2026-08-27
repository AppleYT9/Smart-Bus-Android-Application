# SmartBus – Bus Tracking and Route Management System

SmartBus is a modern, student-focused native Android application designed to track campus shuttle buses and manage routes. Built entirely using **Java** and **XML** with **Material Design 3**, the application delivers a premium, responsive user interface with live simulation animations, shared alerts databases, and application-wide theme switching.

---

## 📱 Features

1. **Animated Loading Splash Screen**:
   * Centered loading layout with a space-blue background.
   * Smooth zoom-scale entrance transition for the neon transit bus logo badge.
   * Fades in the layout components and redirects to welcome onboarding slides automatically after 2.2 seconds.
2. **Onboarding & Credential Authentication**:
   * Modern "Get Started" onboarding screen displaying an AI-generated bus route illustration.
   * Dynamic form validation on email and password text fields.
   * Pre-filled credentials (`student@example.com` / `student123`) to speed up demonstration during academic vivas.
3. **Home Dashboard**:
   * Greeting widgets ("Hello, Student 👋") and notification badges.
   * Current active bus status card (SB-101) with live indicators, next stop, and ETA.
   * Quick action cards routing to details, route list, alerts, and settings.
   * Vertical stops timeline displaying the schedule of stops for the day.
4. **Bus Route Directory**:
   * Interactive recycler view displaying available buses (`SB-101`, `SB-102`, `SB-103`).
   * Displays status badges, route paths, number of stops, and a "View Details" button.
5. **Bus Details Page**:
   * Detailed specifications: Driver name, driver contact, occupancy ratio with a `LinearProgressIndicator` (seats occupied vs capacity), and active ETA.
   * Smooth-scrolling anchor animation: Tapping "View Route" glides the viewport down to the stops recycler timeline.
6. **Simulated Live Tracking with Route Switcher**:
   * **Route Switcher Dropdown**: Directly select a bus (`SB-101`, `SB-102`, or `SB-103`) to track it. It dynamically updates path coordinates and stop milestone labels.
   * **Visual Map Path**: A vertical route path representing milestone stop dots.
   * **Glide Animation**: Tapping *Simulate Bus Movement* slides the bus icon smoothly between stop markers using Y-axis animations.
7. **Dynamic Notification Alerts (Shared Database)**:
   * Thread-safe Singleton `NotificationRepository` acting as an in-memory alert database.
   * Completing a tracking simulation triggers a system status bar notification and dynamically inserts an unread alert (e.g., "Bus SB-101 has arrived at Central Station.") into the alerts list.
   * **Unread Indicators**: Unread alerts display with a blue dot and slate-blue highlights. Tapping marks them as read.
   * **Mark All Read**: Bulk update button to clear all unread markers.
8. **Profile settings & Real Dark Mode**:
   * Editable student details (Name, ID, Email, preferred bus and stops) via custom Material edit dialogs.
   * **Real Night Mode Switch**: Toggles system night-theme immediately using `AppCompatDelegate`, converting layout card styles, text inputs, and vector drawables into Dark Mode.
   * **Secure Logout**: Redirection using task flags `FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK` to destroy the backstack, preventing users from backing back into the dashboard.

---

## 🛠️ Technology Stack

* **Programming Language**: Java
* **UI Layout**: XML (Material Design 3 Components)
* **SDK Compatibility**: compileSdk 34, minSdk 26
* **Build System**: Gradle 8.7 structure (running JBR JDK 21 compiler target)
* **Dependency Libraries**: Material Components, ConstraintLayout, Recyclerview

---

## 🗺️ Application Navigation Flow

```
                  [ SplashActivity ] (Zoom Neon Loading Splash)
                         │
                         ▼ (Auto-transitions / finishes Splash)
                  [ MainActivity ] (Welcome Onboarding Screen)
                         │
                         ▼ (Click Get Started / finishes Welcome)
                  [ LoginActivity ] (Pre-filled credentials & validation)
                         │
                         ▼ (Click Login / finishes Login)
                  [ HomeActivity ] (Dashboard View)
                   │     │      │
     ┌─────────────┘     │      └──────────────┐
     ▼                   ▼                     ▼
[ RoutesActivity ]  [ TrackingActivity ]  [ ProfileActivity ]
     │      ▲        (Live simulation &    (Real dark mode / edit dialog)
     │      │         route switcher)          │      ▲
     │      │ (Back)                           │      │
     ▼      │                                  ▼      │ (Back)
[ BusDetailsActivity ]                    [ LoginActivity ]
     │                                     (Flags: NEW_TASK | CLEAR_TASK)
     ▼ (Click Track / passes bus extra)    (Clears back stack to exit app)
[ TrackingActivity ]
```

---

## 🚀 Setup & Installation

### Option 1: Open in Android Studio
1. Open Android Studio.
2. Go to **File** ➔ **Open...** and select this project directory.
3. Allow Gradle to sync.
4. Launch an Emulator (AVD) or connect an Android device with USB debugging enabled.
5. Click the green **Run** play icon (or press `Shift + F10`).

### Option 2: Build APK via Command Line
Run the following commands inside PowerShell to compile the debug package:
```powershell
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat assembleDebug
```
The compiled installation package will be generated at:
`app/build/outputs/apk/debug/app-debug.apk`

---

## 🎓 Viva Q&As (For Presentation Prep)

1. **Q: Why use mock/static data instead of real-time server connections?**
   * *A*: Local mockup guarantees 100% reliable live demonstrations inside presentation rooms where internet/GPS signals fail. It keeps the codebase simple, clean, and easy to explain under pressure.
2. **Q: How does the simulated bus movement animation work?**
   * *A*: We run a Handler on the UI thread (`Looper.getMainLooper()`) scheduling a repeating `Runnable` every 3.5 seconds. During execution, it Y-translates the bus icon dynamically. To support all phone screens, dp values are programmatically converted to device pixels using `getResources().getDisplayMetrics().density`.
3. **Q: How is data shared between the tracking screen and notifications screen?**
   * *A*: We created a thread-safe Singleton database class (`NotificationRepository`). When the bus completes its journey on the tracking page, it appends a new `Notification` instance to this repository. When the notification screen loads, it reads from this repository, syncing the data instantly.
4. **Q: How does the application clear the backstack on Logout?**
   * *A*: We start the `LoginActivity` using flags:
     `intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);`
     This tells the Android Task manager to destroy all previous activities in the stack, ensuring that pressing the back button on the login screen exits the app instead of returning to the dashboard.
