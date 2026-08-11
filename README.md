# Airline Reservation System

A full-stack airline reservation web application built with **Java Servlets, JSP, JDBC, MySQL, HTML, CSS and JavaScript**.

This project is suitable for a fresher portfolio/resume and demonstrates:
- User registration and login
- Secure password hashing with BCrypt
- Flight search by origin, destination and date
- Flight details and seat availability
- Passenger booking
- Seat-level double-booking protection
- Cancelled seats become available for future bookings
- My Bookings dashboard
- Booking cancellation
- Booking reference generation
- Admin flight management
- JDBC DAO/repository layer
- Session-based authentication
- Responsive JSP frontend
- Docker/Tomcat deployment

## Tech stack

| Layer | Technology |
|---|---|
| Frontend | JSP, HTML5, CSS3, JavaScript |
| Backend | Java 17, Servlets, JDBC |
| Database | MySQL 8+ |
| Build | Maven |
| Server | Apache Tomcat 9 |
| Security | BCrypt password hashing |
| Version control | Git/GitHub |
| Deployment | Docker + Render/Railway |

## Project structure

```text
airline-reservation-system/
├── database/
│   └── schema.sql
├── src/main/java/com/mahipal/airline/
│   ├── dao/
│   │   ├── BookingDAO.java
│   │   ├── FlightDAO.java
│   │   └── UserDAO.java
│   ├── filter/
│   │   └── AuthFilter.java
│   ├── model/
│   │   ├── Booking.java
│   │   ├── Flight.java
│   │   └── User.java
│   ├── servlet/
│   │   ├── AdminFlightServlet.java
│   │   ├── BookFlightServlet.java
│   │   ├── CancelBookingServlet.java
│   │   ├── FlightSearchServlet.java
│   │   ├── LoginServlet.java
│   │   ├── LogoutServlet.java
│   │   ├── MyBookingsServlet.java
│   │   └── RegisterServlet.java
│   └── util/
│       ├── DBConnection.java
│       └── PasswordUtil.java
├── src/main/webapp/
│   ├── WEB-INF/
│   │   └── web.xml
│   ├── css/style.css
│   ├── js/app.js
│   ├── index.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── search-results.jsp
│   ├── book.jsp
│   ├── my-bookings.jsp
│   └── admin-flights.jsp
├── Dockerfile
├── pom.xml
└── README.md
```

## 1. Prerequisites

Install:
1. JDK 17
2. Maven 3.9+
3. MySQL 8+
4. Apache Tomcat 9 (for local non-Docker run)
5. Git

Check:

```bash
java -version
mvn -version
mysql --version
git --version
```

## 2. Create the database

Open MySQL:

```bash
mysql -u root -p
```

Then run:

```sql
SOURCE database/schema.sql;
```

Or import `database/schema.sql` through MySQL Workbench.

## 3. Configure database credentials

Set environment variables.

Windows PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/airline_reservation?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
```

Linux/macOS:

```bash
export DB_URL="jdbc:mysql://localhost:3306/airline_reservation?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
export DB_USER="root"
export DB_PASSWORD="your_mysql_password"
```

The application also has safe local defaults for `DB_URL` and `DB_USER`; set `DB_PASSWORD` for your machine.

## 4. Run locally with Maven + Tomcat

Build:

```bash
mvn clean package
```

Copy:

```text
target/airline-reservation.war
```

to:

```text
TOMCAT_HOME/webapps/
```

Start Tomcat:

Windows:
```text
TOMCAT_HOME/bin/startup.bat
```

Linux/macOS:
```bash
TOMCAT_HOME/bin/startup.sh
```

Open:

```text
http://localhost:8080/airline-reservation/
```

## 5. Run with Docker

Build:

```bash
docker build -t airline-reservation .
```

Run:

```bash
docker run --rm -p 8080:8080 ^
  -e DB_URL="jdbc:mysql://host.docker.internal:3306/airline_reservation?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" ^
  -e DB_USER="root" ^
  -e DB_PASSWORD="your_mysql_password" ^
  airline-reservation
```

On Linux, replace `host.docker.internal` with a reachable MySQL host or add the appropriate Docker host mapping.

## 6. Main application flow

```text
User
  |
  v
JSP / HTML / CSS / JS
  |
  v
Servlet Controller
  |
  v
DAO / JDBC
  |
  v
MySQL
```

### Booking flow

```text
Search flight
   ↓
Select flight
   ↓
Enter passenger + seat
   ↓
Booking servlet
   ↓
Database transaction
   ↓
Check seat availability
   ↓
Insert booking
   ↓
Generate booking reference
   ↓
Confirmation
```

The booking DAO uses a transaction and a unique `(flight_id, seat_number)` constraint to prevent two users from successfully reserving the same seat.

## 7. Demo accounts

After importing `schema.sql`:

A normal user can be created from the Register page. For the first admin account, register a user and change its role to `ADMIN` in MySQL:

```sql
UPDATE users SET role='ADMIN' WHERE email='your-email@example.com';
```

This avoids shipping a known admin password in the repository.

## 8. GitHub

Create a new **public** GitHub repository, for example:

```text
airline-reservation-system
```

GitHub's official flow is: create a repository, then push your project files. See:
https://docs.github.com/en/get-started/start-your-journey/creating-a-repository-for-your-project

Commands:

```bash
git init
git add .
git commit -m "Initial airline reservation system"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/airline-reservation-system.git
git push -u origin main
```

Do NOT commit database passwords, `.env`, `target/`, or IDE files.

## 9. Production deployment

A practical portfolio setup is:

```text
GitHub
   |
   v
Render Web Service
   |
   v
Tomcat Docker container
   |
   v
Cloud MySQL
```

Render supports Docker-based web services and can deploy from a linked GitHub repository. Configure your database environment variables in the Render dashboard rather than committing credentials.

For MySQL, Railway currently provides a MySQL service and exposes variables such as `MYSQLHOST`, `MYSQLPORT`, `MYSQLUSER`, `MYSQLPASSWORD`, `MYSQLDATABASE` and `MYSQL_URL`. Aiven also currently offers a free MySQL tier suitable for learning/prototyping.

For a resume project, deploy the application backend on Render and use Railway/Aiven MySQL as the database.

## 10. Resume description

**Airline Reservation System | Java, JSP, Servlets, JDBC, MySQL, HTML, CSS, JavaScript**

- Developed a full-stack airline reservation system using Java Servlets, JSP, JDBC and MySQL with user authentication, flight search, seat selection, booking and cancellation workflows.
- Implemented DAO-based database operations and transactional seat booking with database constraints to prevent duplicate seat reservations.
- Designed a responsive JSP frontend with reusable navigation, search forms, booking forms and user booking dashboard.
- Added role-based access for administrators to create and manage flight schedules and deployed the application using Docker/Tomcat.

## 11. Good GitHub repository sections

Add these to your README:
- Project overview
- Features
- Architecture
- Tech stack
- Database schema
- Screenshots
- Local setup
- API/Servlet routes
- Deployment
- Future enhancements

## 12. Future enhancements

For a stronger final-year portfolio version, add:
- Online payment gateway in sandbox/test mode
- Email booking confirmation
- PDF ticket generation
- Admin dashboard with revenue and booking analytics
- Password reset
- JWT-based REST API
- Spring Boot migration
- React frontend
- Docker Compose for app + MySQL
- Unit/integration tests
- CI/CD with GitHub Actions
