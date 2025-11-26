Online Healthcare Management System (Java – Swing + MySQL)

A full-featured healthcare management application built with Java Swing, FlatLaf, MySQL, Gradle, JDBC, and a clean service–DAO architecture.

🚀 Project Overview

This system digitizes major healthcare tasks, allowing:

Patients to book/manage appointments

Doctors to manage schedules & records

Admins to manage users, appointments, and analytics

The project includes a modern UI, database connectivity, PDF export, multithreading, and charts.

🎨 Features
🔐 Role-Based Login

Admin login

Doctor login

Patient login

Supports BCrypt hashing

Fallback demo users for offline testing

🖥 Modern UI

Java Swing (FlatLaf Modern Light/Dark theme toggle)

Clean and minimal interface

Separate dashboards for each user type

⏳ Appointment Management

Patient booking (with conflict prevention)

Doctor schedule view

Admin full appointment list

Synchronized slot blocking using multithreading

📚 Medical Records (Doctor)

View patient details

Manage and update medical notes

(Extendable for future EHR system)

📄 PDF Export

Patients can export appointment history using PDFBox.

📊 Analytics (Admin)

Simple bar chart using JFreeChart

Shows appointments per doctor (demo data)

Fully expandable for real statistics

⚙ Database Layer

Full DAO + JDBC:

Users

Doctors

Patients

Appointments

PreparedStatements used (safe from SQL injection).

🏗 Architecture
Layered Architecture
UI Layer → Service Layer → DAO Layer → Database

Packages
app/         → Main entry point  
ui/          → All Swing UI screens  
model/       → POJO classes (User, Appointment…)  
dao/         → DAO interfaces + JDBC implementations  
service/     → Business logic (booking, scheduling)  
util/        → Config, DBConnection, Exceptions  
sql/         → SQL schema  

🗄 Database Schema

Run this file to create all tables:

sql/schema_full.sql


Includes tables:

roles

users

doctors

patients

appointments

With UNIQUE constraint on appointment slots.

🧵 Multithreading

ScheduledExecutorService for background heartbeat

SwingWorker for non-blocking UI updates

synchronized locking for appointment booking conflict detection

🧰 Tech Stack

Language: Java 17
UI: Swing + FlatLaf
Database: MySQL
Build Tool: Gradle
Libraries:

FlatLaf

MySQL Connector

BCrypt

PDFBox

JFreeChart

▶️ How to Run
Step 1: Install JDK 17+
Step 2: Install MySQL (optional)

If you want real DB data:

Update src/main/resources/config.properties

Run schema_full.sql

Step 3: Run project
./gradlew run


or on Windows:

gradlew.bat run

📌 Demo Login Credentials

(Works even without database)

Role	Email	Password
Admin	admin@hms.com
	admin123
Doctor	doctor@hms.com
	doctor123
Patient	patient@hms.com
	patient123
🧭 Future Enhancements

Doctor calendar view

Admin analytics dashboard v2

Email/SMS notifications

Cloud deployment

User registration system
