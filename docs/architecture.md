# Architecture

```text
                 Browser
                    |
             JSP / CSS / JS
                    |
              HTTP requests
                    |
          Java Servlet Controllers
                    |
          -----------------------
          |         |           |
       UserDAO   FlightDAO   BookingDAO
          |         |           |
          -------- JDBC --------
                    |
                  MySQL
```

## Main routes

| Route | Method | Purpose |
|---|---|---|
| `/` | GET | Home/search page |
| `/register` | POST | Create user |
| `/login` | POST | Authenticate user |
| `/logout` | GET | End session |
| `/search` | GET | Search flights |
| `/book?flightId=1` | GET | Booking page |
| `/book` | POST | Create reservation |
| `/my-bookings` | GET | User reservations |
| `/cancel-booking` | POST | Cancel reservation |
| `/admin/flights` | GET | Admin flight list |
| `/admin/flights` | POST | Add flight |

## Security notes

- Passwords are stored as BCrypt hashes, not plaintext.
- SQL uses `PreparedStatement`.
- Booking operations use a database transaction.
- A unique database constraint protects a seat from duplicate reservation.
- Admin pages are protected by `AuthFilter`.
- Database secrets are supplied with environment variables.
