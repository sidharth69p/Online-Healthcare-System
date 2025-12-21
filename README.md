# **Online Healthcare Management System (Java – Swing + MySQL)**

A full-featured healthcare management platform built using **Java Swing**, **FlatLaf themes**, **JDBC**, **MySQL**, **Multithreading**, **JFreeChart**, **PDF export**, and a clean **layered architecture**.

---

## 🚀 Overview

This system digitalizes core operations of a healthcare center, allowing:

### **Patients**

* Book appointments
* View history
* Export appointments as PDF

### **Doctors**

* Manage daily schedules
* View patient records

### **Admins**

* Manage users
* Manage appointments
* Access analytics

---

## 🎨 UI & Experience

* Built using **Java Swing**
* Modern **FlatLaf Light + Dark Theme Toggle**
* Clean, responsive layouts
* Role-based dashboards

---

## 🔐 Authentication

* Role-based login system
* Supports **BCrypt** hashing
* Demo accounts included (offline mode)
* Config-based credentials (no hardcoded secrets)

---

## 🏗 Architecture (Layered)

```
UI Layer  →  Service Layer  →  DAO Layer  →  Database
```

### **Folders**

* `ui/` → All screens
* `model/` → User, Doctor, Patient, Appointment
* `dao/` → Interfaces + JDBC implementations
* `service/` → Business logic
* `util/` → Config, DB Connection, Exceptions
* `sql/` → Database schema

---
## Error Handling & Data Validation

The application implements robust error handling and validation to ensure system stability and prevent crashes.

- Client-side validation is performed before processing user actions (e.g., empty fields, invalid formats).
- User-friendly dialog messages are displayed for invalid inputs instead of terminating the application.
- Server-side validation is enforced at the DAO layer using exception handling and database constraints.
- All database-related exceptions are gracefully propagated and handled without application failure.

---

## Multithreading & Performance Optimization

To ensure a responsive user interface and efficient processing, the system utilizes multithreading:

- SwingWorker is used to perform long-running operations (such as appointment booking and data retrieval) in the background.
- This prevents UI freezing and maintains smooth user interaction.
- Background execution ensures thread safety while accessing shared resources such as appointment slots.

---

## Component Integration & Architecture

The system follows a layered architecture to maintain clean separation of concerns:

UI Layer → Service Layer → DAO Layer → Database

- The UI layer handles user interaction and input validation.
- The Service layer contains business logic and workflow management.
- The DAO layer manages all database operations using JDBC and PreparedStatements.
- This structure improves maintainability, scalability, and testability.

---


## 📚 Features

### ✔ Appointment Booking

* Conflict prevention using synchronized locks
* Book, view, manage appointments
* Doctor availability handled

### ✔ PDF Export (Patient)

* Appointment history → clean PDF
* Powered by **PDFBox**

### ✔ Analytics (Admin)

* Bar charts via **JFreeChart**
* Appointment statistics

### ✔ Multithreading

* `SwingWorker` for smooth UI
* `ScheduledExecutorService` for heartbeat/scheduler tasks
* Prevents UI freezing

---

## 🗄 Database Schema

Included in:

```
sql/schema_full.sql
```

Tables:

* roles
* users
* doctors
* patients
* appointments

Includes UNIQUE constraint to prevent double booking.

---

## 🧰 Tech Stack

* **Java 17**
* **Swing + FlatLaf**
* **Gradle**
* **MySQL + JDBC**
* **BCrypt**
* **JFreeChart**
* **PDFBox**

---

## ▶ How to Run

### **1. Install JDK 17+**

### **2. (Optional) Setup MySQL database**

Edit `config.properties`:

```
db.url=jdbc:mysql://localhost:3306/online_healthcare
db.username=YOUR_USER
db.password=YOUR_PASS
```

### **3. Run using Gradle**

```
./gradlew run
```

or on Windows:

```
gradlew.bat run
```

---

## 🧪 Demo Credentials (No Database Needed)

| Role    | Email                                     | Password   |
| ------- | ----------------------------------------- | ---------- |
| Admin   | [admin@hms.com](mailto:admin@hms.com)     | admin123   |
| Doctor  | [doctor@hms.com](mailto:doctor@hms.com)   | doctor123  |
| Patient | [patient@hms.com](mailto:patient@hms.com) | patient123 |

---

## 🚀 Future Enhancements

* Doctor calendar UI
* Admin analytics v2
* Email/SMS notifications
* User registration module
* Cloud deployment

---

## 📌 Conclusion

This project demonstrates:

* OOP
* JDBC mastery
* Clean architecture
* Modern UI
* Multithreaded processing
* Real-world healthcare workflow simulation

Perfect for academic evaluation and practical deployment.

---
