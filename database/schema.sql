CREATE DATABASE IF NOT EXISTS airline_reservation;
USE airline_reservation;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    role ENUM('USER','ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE flights (
    id INT PRIMARY KEY AUTO_INCREMENT,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    airline VARCHAR(100) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    total_seats INT NOT NULL DEFAULT 60,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_route_date (origin, destination, departure_time)
);

CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    booking_reference VARCHAR(20) NOT NULL UNIQUE,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    passenger_email VARCHAR(150) NOT NULL,
    passenger_phone VARCHAR(25) NOT NULL,
    seat_number VARCHAR(10) NULL,
    status ENUM('CONFIRMED','CANCELLED') NOT NULL DEFAULT 'CONFIRMED',
    payment_status ENUM('PENDING','PAID','REFUNDED') NOT NULL DEFAULT 'PENDING',
    booked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_booking_flight
        FOREIGN KEY (flight_id) REFERENCES flights(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_active_seat UNIQUE (flight_id, seat_number),

    INDEX idx_user_bookings (user_id, booked_at)
);
INSERT INTO flights
(flight_number, airline, origin, destination, departure_time, arrival_time, price, total_seats)
VALUES
(
    'AI101',
    'Air India',
    'Hyderabad',
    'Delhi',
    DATE_ADD(NOW(), INTERVAL 1 DAY),
    DATE_ADD(DATE_ADD(NOW(), INTERVAL 1 DAY), INTERVAL 120 MINUTE),
    5499.00,
    60
),
(
    '6E202',
    'IndiGo',
    'Hyderabad',
    'Mumbai',
    DATE_ADD(NOW(), INTERVAL 2 DAY),
    DATE_ADD(DATE_ADD(NOW(), INTERVAL 2 DAY), INTERVAL 100 MINUTE),
    3999.00,
    60
),
(
    'UK303',
    'Vistara',
    'Delhi',
    'Bengaluru',
    DATE_ADD(NOW(), INTERVAL 3 DAY),
    DATE_ADD(DATE_ADD(NOW(), INTERVAL 3 DAY), INTERVAL 150 MINUTE),
    6299.00,
    60
),
(
    'IX404',
    'Air India Express',
    'Chennai',
    'Hyderabad',
    DATE_ADD(NOW(), INTERVAL 4 DAY),
    DATE_ADD(DATE_ADD(NOW(), INTERVAL 4 DAY), INTERVAL 80 MINUTE),
    2899.00,
    60
),
(
    '6E505',
    'IndiGo',
    'Bengaluru',
    'Kolkata',
    DATE_ADD(NOW(), INTERVAL 5 DAY),
    DATE_ADD(DATE_ADD(NOW(), INTERVAL 5 DAY), INTERVAL 140 MINUTE),
    4599.00,
    60
);