# SubGuard: Premium Subscription & Trial Tracker

**Role:** Senior Android Architect & UI/UX Designer
**Goal:** A complete, production-ready Android Application using Kotlin and Jetpack Compose to track subscriptions and free trials manually. It is offline-first, privacy-focused, and supports multiple languages.

## 1. Design System & Aesthetics: "OLED Midnight Minimalist"

The application is designed with a dark, high-contrast theme optimized for OLED screens to save battery and provide a premium feel.

| Element | Color Code | Purpose |
| :--- | :--- | :--- |
| **Background** | `#000000` (Pure Black) | Optimized for OLED battery saving. |
| **Surface/Cards** | `#1C1C1E` (Dark Grey) | Subtle contrast for cards and containers. |
| **Primary Accent** | `#00E5FF` (Neon Teal) | Primary actions, buttons, and total monthly cost. |
| **Alert/Trial Accent** | `#FF5252` (Soft Red) | Urgent alerts, trial end dates, and warnings. |
| **Shapes** | `CornerRadius 24.dp` | Heavily rounded corners for a modern, soft aesthetic. |

## 2. Tech Stack & Architecture

The project follows a **Clean Architecture** (MVVM) pattern to ensure separation of concerns, testability, and maintainability.

| Layer | Technology | Purpose |
| :--- | :--- | :--- |
| **Language** | Kotlin | Modern, concise, and safe programming language for Android. |
| **UI** | Jetpack Compose (Material3) | Declarative UI framework for building native UIs. |
| **Architecture** | MVVM + Clean Architecture | Separation of Data, Domain, and Presentation layers. |
| **Database** | Room Database (SQLite) | Offline-first data persistence. |
| **DI** | Hilt | Dependency Injection for managing dependencies and scoping. |
| **Navigation** | Jetpack Compose Navigation | Managing navigation between screens. |
| **Localization** | `strings.xml` | Support for English (`en`) and Spanish (`es`). |
| **Background Tasks** | WorkManager | Scheduling local notifications for payment/trial reminders. |

## 3. Core Features Implemented

### A. Dashboard (Home)
*   **Total Monthly:** Displays the aggregated cost of all active, non-trial subscriptions.
*   **Subscription List:** Vertical scrollable list of subscriptions.
*   **Visual Cues:**
    *   Standard subscriptions show "Next Payment in X days" with **Neon Teal** accents.
    *   Free Trials show "Trial ends in X days" with **Soft Red** accents.
*   **Sorting:** List is automatically sorted by the closest payment/trial end date.

### B. Add/Edit Subscription Form
*   **Inputs:** Name, Price, Currency Selector, Billing Cycle (Weekly, Monthly, Yearly), Start Date.
*   **Trial Logic:** Toggle for Free Trial, which conditionally reveals the "Trial End Date" input.
*   **Notifications:** Dropdown for notification interval (1 day before, 3 days before).

### C. Settings & Localization
*   **Language Switcher:** Allows toggling between English and Spanish.
*   **Default Currency:** Set a global default currency symbol for display.

## 4. Business Logic & Utility

*   **Next Billing Date Calculation:** The `DateUtils.kt` utility class contains the logic to dynamically calculate the next payment date based on the `startDate` and `billingCycle`.
*   **Notifications:** The `NotificationScheduler` uses `WorkManager` to schedule a `NotificationWorker` to fire a local notification a specified number of days before the next payment or trial end date.

## 5. File Structure

The project structure adheres to the Clean Architecture principles:

```
SubGuard/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/subguard/
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── dao/ (SubscriptionDao.kt)
│   │   │   │   │   ├── entity/ (SubscriptionDatabase.kt, Converters.kt)
│   │   │   │   ├── repository/ (SubscriptionRepositoryImpl.kt, PreferenceRepositoryImpl.kt)
│   │   │   ├── di/ (AppModule.kt, DatabaseModule.kt)
│   │   │   ├── domain/
│   │   │   │   ├── model/ (Subscription.kt)
│   │   │   │   ├── repository/ (SubscriptionRepository.kt, PreferenceRepository.kt)
│   │   │   │   ├── usecase/ (SubscriptionUseCases.kt, GetSubscriptions.kt, etc.)
│   │   │   ├── presentation/
│   │   │   │   ├── navigation/ (SubGuardNavigation.kt)
│   │   │   │   ├── screens/
│   │   │   │   │   ├── home/ (HomeScreen.kt)
│   │   │   │   │   ├── add_edit/ (AddEditSubscriptionScreen.kt)
│   │   │   │   │   ├── settings/ (SettingsScreen.kt)
│   │   │   │   ├── theme/ (Theme.kt, Type.kt, Shape.kt)
│   │   │   │   ├── viewmodel/ (HomeViewModel.kt, AddEditViewModel.kt, SettingsViewModel.kt)
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── SubGuardApplication.kt
│   │   │   ├── util/ (DateUtils.kt, LocaleHelper.kt, NotificationScheduler.kt, NotificationWorker.kt)
│   │   ├── res/
│   │   │   ├── values/ (strings.xml - English)
│   │   │   ├── values-es/ (strings.xml - Spanish)
│   │   ├── AndroidManifest.xml
│   ├── build.gradle.kts (Module)
├── build.gradle.kts (Project)
├── settings.gradle.kts
├── gradle.properties
└── gradle/
    └── libs.versions.toml
