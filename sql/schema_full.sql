-- Full schema for Online Healthcare System
CREATE DATABASE IF NOT EXISTS online_healthcare;
USE online_healthcare;

CREATE TABLE roles (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(20) NOT NULL UNIQUE
);

INSERT IGNORE INTO roles (name) VALUES ('ADMIN'),('DOCTOR'),('PATIENT');

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  role_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE doctors (
  id INT PRIMARY KEY,
  specialization VARCHAR(100),
  available_from TIME,
  available_to TIME,
  FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE patients (
  id INT PRIMARY KEY,
  date_of_birth DATE,
  gender VARCHAR(10),
  FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  doctor_id INT NOT NULL,
  patient_id INT NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  status VARCHAR(20) DEFAULT 'SCHEDULED',
  notes TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (doctor_id) REFERENCES doctors(id),
  FOREIGN KEY (patient_id) REFERENCES patients(id),
  UNIQUE (doctor_id, appointment_date, appointment_time)
);

-- Sample users (passwords are plaintext for demo; replace with bcrypt in production)
INSERT IGNORE INTO users (name,email,password,role_id) VALUES
('Admin User','admin@hms.com','admin123', (SELECT id FROM roles WHERE name='ADMIN')),
('Dr. Ravi','doctor@hms.com','doctor123', (SELECT id FROM roles WHERE name='DOCTOR')),
('Demo Patient','patient@hms.com','patient123', (SELECT id FROM roles WHERE name='PATIENT'));

INSERT IGNORE INTO doctors (id,specialization, available_from, available_to) VALUES
((SELECT id FROM users WHERE email='doctor@hms.com'),'General Physician','09:00:00','17:00:00');

INSERT IGNORE INTO patients (id,date_of_birth,gender) VALUES
((SELECT id FROM users WHERE email='patient@hms.com'),'2000-01-01','Male');
