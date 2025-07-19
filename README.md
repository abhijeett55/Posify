
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
