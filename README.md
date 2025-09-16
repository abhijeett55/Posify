
# 📍 PointOnScale – Android App with Firebase Auth & Supabase Storage

**PointOnScale** is an Android application developed in Java that allows users to interact with points over a scalable surface (e.g. zoomable map, image, or canvas). It uses **Firebase Authentication** for secure login and **Supabase** for real-time data storage and retrieval.

---

## 🚀 Features

- 🔐 **Firebase Authentication**
  - Email/Password login
  - Google Sign-In (optional)
- 🗃 **Supabase Backend**
  - Store point coordinates
  - Fetch, update, and delete user-specific point data
- 🖼️ **Point-on-Scale View**
  - Users can tap/drag to add points on a zoomable and pannable canvas
  - Points scale correctly with gestures
- ☁️ **Realtime Sync**
  - Points update in real-time across devices
- 🎨 Clean Material UI with Java (no Kotlin dependency)

---

## 🛠 Tech Stack

| Layer         | Tech Used              |
|---------------|------------------------|
| Language       | Java                  |
| Authentication| Firebase Auth          |
| Storage/DB     | Supabase (PostgreSQL) |
| UI             | Android Views + TouchListeners |
| Gradle         | Android Gradle Plugin |

---

## 📦 Project Structure

```
app/
├── activities/
│   └── MainActivity.java
├── fragments/
│   └── AuthFragment.java
├── utils/
│   └── SupabaseClient.java
├── services/
│   └── FirebaseAuthManager.java
└── res/
    ├── layout/
    ├── drawable/
    └── values/
```

---

## 🔧 Setup Instructions

### 1. Clone the Repo

```bash
git clone https://github.com/abhijeett55/PointOnScale.git
cd PointOnScale
```
### 2. Open in Android Studio


### 3. Firebase Configuration

Go to Firebase Console
Create a new Firebase project.
Enable Firebase Authentication → Email/Password (and Google Sign-In if required).
Download the google-services.json file and place it in:
```json
app/google-services.json
```
## 📖 Usage

Launch the app.
Register or login with Firebase Authentication.
Tap/drag on the canvas to add points.
Points will sync in real-time with Supabase.
Logout and login on another device → your points will still be there!

## ✅ Future Improvements
Multi-user collaboration with shared canvases.
Point grouping & labeling.
Export/import points (CSV/JSON).
Offline mode with local caching.
Dark mode UI.

## 🤝 Contributing

Contributions are welcome!
Fork the repository.
Create a new branch:
git checkout -b feature/your-feature
Commit your changes:
git commit -m "Added new feature"
Push and open a Pull Request.

## 👨‍💻 Author
Abhijeet Biswas
