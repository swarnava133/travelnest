<div align="center">

# 🏨 TravelNest
### Full Stack Hotel Booking Platform

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-3.9-black?style=for-the-badge&logo=apachekafka)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue?style=for-the-badge&logo=docker)
![HTML](https://img.shields.io/badge/HTML%2FCSS%2FJS-Frontend-yellow?style=for-the-badge&logo=html5)

A production-grade hotel booking system built with **8 Spring Boot Microservices**,
featuring JWT authentication, async notifications via Kafka, circuit breakers,
distributed tracing, and a responsive frontend.

[🌐 Live Demo](#) • [📖 API Docs](#api-endpoints) • [🚀 Quick Start](#quick-start)

</div>

---

## 📸 Screenshots

> Login Page | Hotel Listing | Booking Flow | Dashboard

---

## 🏗️ Architecture

```
                    ┌─────────────────┐
                    │  Config Server  │
                    │   port: 8888    │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │Service Registry │
                    │  (Eureka) 8761  │
                    └────────┬────────┘
                             │
              ┌──────────────▼──────────────┐
              │         API Gateway          │
              │    JWT Auth Filter :8080     │
              └──┬──────┬──────┬──────┬─────┘
                 │      │      │      │
         ┌───────▼─┐ ┌──▼───┐ ┌▼────┐ ┌▼──────────┐
         │  User   │ │Hotel │ │Pay  │ │  Booking  │
         │Service  │ │Svc   │ │Svc  │ │  Service  │
         │  :8081  │ │:8082 │ │:8084│ │   :8083   │
         └─────────┘ └──────┘ └─────┘ └─────┬─────┘
                                             │ Kafka
                                    ┌────────▼──────┐
                                    │ Notification  │
                                    │   Service     │
                                    │    :8085      │
                                    └───────────────┘
```

---

## ✨ Features

- 🔐 **JWT Authentication** — Secure login/register with token-based auth
- 🏨 **Hotel Management** — Browse 100+ hotels across 15 Indian cities
- 📅 **Room Booking** — Real-time availability checking and booking
- 💳 **Payment Processing** — Mock payment with transaction tracking
- 📧 **Email Notifications** — Async booking confirmation via Kafka
- ⚡ **Circuit Breaker** — Resilience4j fallback for service failures
- 🔍 **Distributed Tracing** — Full request tracking with Zipkin
- 🐳 **Docker Support** — One command to run everything
- 📱 **Responsive UI** — Works on desktop, tablet and mobile

---

## 🛠️ Tech Stack

### Backend
| Technology | Purpose |
|---|---|
| Spring Boot 3.2.5 | Microservices framework |
| Spring Cloud Gateway | API Gateway + JWT filter |
| Spring Cloud Netflix Eureka | Service discovery |
| Spring Cloud Config | Centralized configuration |
| Spring Data JPA | Database ORM |
| Spring Security | Authentication |
| Apache Kafka | Async messaging |
| Resilience4j | Circuit breaker |
| Zipkin + Micrometer | Distributed tracing |
| OpenFeign | Inter-service communication |
| JWT (JJWT) | Token authentication |
| MySQL 8.0 | Relational database |
| Docker | Containerization |

### Frontend
| Technology | Purpose |
|---|---|
| HTML5 | Structure |
| CSS3 | Styling (Dark Green & Gold theme) |
| JavaScript (Vanilla) | Interactivity |
| Fetch API | REST API calls |

---

## 🗄️ Microservices

| Service | Port | Description |
|---|---|---|
| config-server | 8888 | Centralized config for all services |
| service-registry | 8761 | Eureka service discovery |
| api-gateway | 8080 | Single entry point + JWT validation |
| user-service | 8081 | Auth, registration, JWT tokens |
| hotel-service | 8082 | Hotels, rooms, availability |
| payment-service | 8084 | Payment processing |
| booking-service | 8083 | Bookings, Feign client, Kafka producer |
| notification-service | 8085 | Kafka consumer, email notifications |

---

## 🗃️ Database Design

Each service has its own dedicated database:

```
user_db     → users table
hotel_db    → hotels, rooms tables
booking_db  → bookings table
payment_db  → payments table
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven
- MySQL 8.0
- Docker Desktop

### Option 1 — Run with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/swarnava133/travelnest.git
cd travelnest

# Build all services
./build-all.sh

# Start everything
docker-compose up --build
```

Visit `http://localhost:8761` to see all services in Eureka!

### Option 2 — Run Locally with IntelliJ

**Step 1 — Create MySQL Databases**
```sql
CREATE DATABASE user_db;
CREATE DATABASE hotel_db;
CREATE DATABASE booking_db;
CREATE DATABASE payment_db;
```

**Step 2 — Start Kafka & Zipkin**
```bash
docker-compose up -d kafka zookeeper zipkin
```

**Step 3 — Start Services in Order**
```
1. ConfigServerApplication       → :8888
2. ServiceRegistryApplication    → :8761
3. ApiGatewayApplication         → :8080
4. UserServiceApplication        → :8081
5. HotelServiceApplication       → :8082
6. PaymentServiceApplication     → :8084
7. BookingServiceApplication     → :8083
8. NotificationServiceApplication → :8085
```

**Step 4 — Open Frontend**

Open `frontend/index.html` in IntelliJ and click the Chrome browser icon.

---

## 🔌 API Endpoints

### Auth
```
POST /api/users/register    → Register new user
POST /api/users/login       → Login and get JWT token
```

### Hotels
```
GET  /api/hotels              → Get all hotels
GET  /api/hotels/{id}         → Get hotel by id
GET  /api/hotels/city/{city}  → Search by city
GET  /api/hotels/{id}/rooms   → Get hotel rooms
```

### Bookings
```
POST /api/bookings                → Create booking
GET  /api/bookings/user/{email}   → Get user bookings
PUT  /api/bookings/{id}/cancel    → Cancel booking
```

### Payments
```
POST /api/payments/process        → Process payment
GET  /api/payments/{id}           → Get payment details
```

---

## 🔄 Booking Flow

```
User selects room
      ↓
POST /api/bookings
      ↓
Booking Service checks room availability (Feign → Hotel Service)
      ↓
Booking Service processes payment (Feign → Payment Service)
      ↓
If SUCCESS → Booking CONFIRMED
      ↓
Kafka event published → "booking-confirmed"
      ↓
Notification Service consumes event → Sends email to user 📧
```

---

## 🏙️ Available Cities

```
Kolkata • Mumbai • Delhi • Chennai • Hyderabad
Bangalore • Goa • Jaipur • Pune • Kochi
Udaipur • Varanasi • Chandigarh • Bhubaneswar • Ahmedabad
```

**100+ luxury hotels across 15 cities!**

---

## 🐳 Docker Services

```yaml
Services:
  mysql              → Database
  zookeeper          → Kafka dependency
  kafka              → Message broker
  zipkin             → Distributed tracing UI
  config-server      → Centralized config
  service-registry   → Eureka server
  api-gateway        → Entry point
  user-service       → Auth service
  hotel-service      → Hotel management
  payment-service    → Payment processing
  booking-service    → Booking management
  notification-service → Email notifications
```

---

## 📊 Key Design Patterns

| Pattern | Implementation |
|---|---|
| API Gateway | Spring Cloud Gateway |
| Service Discovery | Netflix Eureka |
| Circuit Breaker | Resilience4j |
| Async Messaging | Apache Kafka |
| Distributed Tracing | Zipkin + Brave |
| Config Management | Spring Cloud Config |
| Inter-service Calls | OpenFeign |
| Database per Service | 4 separate MySQL DBs |

---

## 👨‍💻 Author

**Swarnava Dhar**

[![GitHub](https://img.shields.io/badge/GitHub-swarnava133-black?style=for-the-badge&logo=github)](https://github.com/swarnava133)

---

<div align="center">

⭐ **Star this repository if you found it helpful!** ⭐

Built with ❤️ using Spring Boot Microservices

</div>