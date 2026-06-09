# Mock Interview Scheduler [Live Demo](https://mockprepinterviewscheduler.netlify.app/)

A full-stack interview scheduling platform that connects students with industry professionals for mock interviews. Built with a focus on scheduling integrity, secure access control, and automated matching.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Data Model](#data-model)
- [Tech Stack](#tech-stack)
- [API Reference](#api-reference)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Roadmap](#roadmap)

---

## Overview

Mock Interview Scheduler automates the end-to-end process of scheduling mock interviews between students and interviewers. The platform handles user registration, admin-driven matching, slot negotiation, interview scheduling, feedback collection, and interviewer rating — all without manual coordination.

Three distinct roles drive the workflow:

- **Student** — registers, submits an interview request, adds availability slots and areas of expertise
- **Interviewer** — registers, awaits admin approval, adds availability and expertise domains
- **Placement Coordinator (Admin)** — approves interviewers, triggers matching, manages the interview lifecycle

---

## Features

- Role-based access control with JWT authentication and OAuth2 resource server pattern
- Admin-triggered FCFS matching algorithm based on shared expertise subdomains (threshold: 3+)
- Slot overlap detection to find mutually available time windows
- Email notifications at every stage — match found, interview confirmed, feedback reminder
- Running average rating system for interviewers, updated on every student feedback submission
- Bi-directional feedback — in-depth interviewer assessment + student rating and comments
- Interviewer approval workflow with experience-based eligibility check

---

## Architecture

The application follows a layered architecture with clear separation between the web, service, and data layers.

```
┌─────────────────────────────────────────────────────┐
│                    REST Controllers                  │
│        StudentController  InterviewerController      │
│              PlacementController                     │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                  Service Layer                       │
│                                                      │
│  StudentService      InterviewerService              │
│  PlacementService    MatchService                    │
│  InterviewService    SlotService                     │
│  ExpertiseService    EmailService                    │
│  StudentFeedbackService  InterviewerFeedbackService  │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│               Repository Layer (JPA)                 │
│                                                      │
│  StudentRepository      InterviewerRepository        │
│  MatchRepository        InterviewSessionRepository   │
│  SlotRepository         ExpertiseRepository          │
│  InterviewRequestRepository                          │
│  StudentFeedbackRepository  InterviewerFeedbackRepo  │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                   PostgreSQL                         │
└─────────────────────────────────────────────────────┘
```

### Matching Algorithm

Matching is triggered manually by the admin on a per-request basis. The algorithm runs as follows:

1. Fetch the student's interview request and their expertise subdomains
2. Iterate approved interviewers in insertion order (FCFS)
3. For each interviewer, count shared subdomains across all expertise entries using a `HashSet` for O(1) lookup
4. If shared subdomain count exceeds the threshold (default: 3), search for an overlapping availability slot
5. Slot overlap is determined using the standard interval overlap formula: `A.start < B.end && B.start < A.end`
6. First valid match found — slot is marked `BOOKED`, match record saved, and process exits
7. If no match is found after all interviewers are evaluated, a `NOT_FOUND` match is recorded for tracking

### Feedback and Rating

The interviewer's `overallRating` is a stored running average, updated on every student feedback submission using:

```
newAverage = ((oldAverage × totalReviews) + newRating) / (totalReviews + 1)
```

This avoids storing all historical ratings while keeping the average accurate across any number of reviews. Result is rounded to one decimal place.

### Inheritance Strategy

`StudentFeedback` and `InterviewerFeedback` extend an abstract `Feedback` base class using JPA `TABLE_PER_CLASS` inheritance strategy. This means no parent `feedback` table exists — each concrete subtype has its own fully independent table with base fields duplicated. This was chosen because the two feedback types are never queried together, making join-free per-type queries the right trade-off over normalization.

### Email Service

Email notifications are handled by `EmailService` using Spring Mail with Gmail SMTP. A `app.mail.mock` flag controls behaviour:

- `true` (default) — emails are logged to console instead of sent, safe for development
- `false` — real emails sent via Gmail SMTP using configured credentials

Three notification types are implemented:
- **Acceptance request** — sent to both parties after a match is found
- **Interview confirmation** — sent after both accept, includes Google Meet link
- **Feedback reminder** — sent after admin marks the interview complete

---

## Data Model

### Entity Inheritance

`Student` and `Interviewer` both extend `CustomUser`, which implements Spring Security's `UserDetails`. `CustomUser` holds authentication data (email, password, role) and shared collections (slots, expertise).

```
CustomUser (base)
├── Student      (college, branch, targetRole, interviewRequest)
├── Interviewer  (company, designation, approvalStatus, overallRating, totalReviews)
└── PlacementCoordinator
```

### Core Relationships

```
Student      ──────── InterviewRequest  (one-to-one)
Student      ──────── Match             (many-to-one)
Interviewer  ──────── Match             (many-to-one)
Match        ──────── InterviewSession  (one-to-one)
Match        ──────── AvailabilitySlot  (one-to-one)
CustomUser   ──────── AvailabilitySlot  (one-to-many)
CustomUser   ──────── Expertise         (one-to-many, cascade ALL)
InterviewSession ──── StudentFeedback   (one-to-one)
InterviewSession ──── InterviewerFeedback (one-to-one)
```

### Match Lifecycle

```
FOUND  →  (both accept)  →  interview created  →  COMPLETED
  └──  (either rejects) →  re-match triggered
NOT_FOUND  →  admin retries matching later
```

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security, OAuth2 Resource Server, JWT |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Email | Spring Mail, Gmail SMTP |
| Build | Maven |
| Deployment | Railway |

---

## API Reference

### Student Endpoints `(/api/v1/students)`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/addStudent` | Register a new student |
| GET | `/{id}` | Get student by ID |
| PUT | `/updateStudent/{id}` | Update student profile |
| DELETE | `/delete/{id}` | Delete student |
| GET | `/all` | Get all students |
| POST | `/{id}/expertise` | Add expertise |
| GET | `/{id}/expertises` | Get all expertise |
| POST | `/{id}/addSlot` | Add availability slot |
| GET | `/{id}/slots` | Get all slots |
| PUT | `/{userId}/updateSlots/{slotId}` | Update a slot |
| POST | `/accept/{id}` | Accept interview match |
| POST | `/{userId}/feedback/{sessionId}` | Submit feedback |
| GET | `/feedback/{id}` | Get feedback for student |

### Interviewer Endpoints `(/api/v1/interviewers)`

| Method | Endpoint | Description |
|---|---|---|
| POST | `/` | Register a new interviewer |
| GET | `/{id}` | Get interviewer by ID |
| PUT | `/{id}/` | Update interviewer profile |
| DELETE | `/{id}` | Delete interviewer |
| POST | `/{id}/expertise` | Add expertise |
| GET | `/{id}/expertises` | Get all expertise |
| POST | `/{id}/addSlot` | Add availability slot |
| GET | `/{id}/slots` | Get all slots |
| PUT | `/{userId}/updateSlots/{slotId}` | Update a slot |
| POST | `/accept/{id}` | Accept interview match |
| POST | `/{userId}/feedback/{sessionId}` | Submit feedback |
| GET | `/feedback/{id}` | Get feedback for interviewer |

### Admin Endpoints `(/placement-admin)`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/students/all` | Get all students |
| GET | `/interview-requests/pending` | Get pending requests |
| GET | `/interviewers/pending` | Get pending approvals |
| POST | `/approve-interviewer/{id}` | Approve an interviewer |
| POST | `/match-request/{id}` | Trigger matching for a request |
| POST | `/send-acceptance-request/{id}` | Send acceptance emails |
| POST | `/create-interview/{id}` | Create interview session |
| POST | `/complete-interview/{sessionId}` | Mark interview complete |

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL 14+

### Local Setup

```bash
# Clone the repository
git clone https://github.com/abby1323/InterviewScheduler.git
cd InterviewScheduler

# Configure database and mail in application.properties
# (see Configuration section below)

# Build and run
./mvnw spring-boot:run
```

### application.properties

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/interview_scheduler
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=YOUR_JWT_SECRET
jwt.expiration=86400000

# Mail
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=YOUR_GMAIL_ADDRESS
spring.mail.password=YOUR_APP_PASSWORD
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Mock email (set to false to send real emails)
app.mail.mock=true
app.mail.from=YOUR_GMAIL_ADDRESS
```

> **Note:** For Gmail SMTP, use an App Password rather than your account password. Generate one at Google Account → Security → 2-Step Verification → App Passwords.

---

## Configuration

| Property | Default | Description |
|---|---|---|
| `app.mail.mock` | `true` | Logs emails instead of sending |
| `app.mail.from` | — | Sender email address |
| `jwt.expiration` | `86400000` | Token validity in milliseconds (24h) |

---

## Roadmap

- [ ] DTOs for all request/response bodies
- [ ] Global exception handling with custom exception classes
- [ ] `@AuthenticationPrincipal` wired into all secured endpoints
- [ ] Frontend — HTML/CSS/Vanilla JS
- [ ] Google Meet API integration for automatic link generation
- [ ] Docker support
- [ ] Kafka integration for async email notifications