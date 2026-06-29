# HRMS — Human Resource Management System

A web-based HRMS application built with the [WebforJ](https://webforj.com) framework in Java. Designed for Vivekanand Technologies, it provides HR workflows for employees, managers, and admins from a single interface.

## Features

- **Login** — Role-aware sign-in for admins, managers, HR, and employees
- **Dashboard** — At-a-glance view of today's attendance status, leave balance, and attendance rate with recent notifications
- **Attendance** — Track check-in/check-out and view attendance history
- **Leaves** — Submit and manage leave requests (planned, casual, sick)
- **Reports** — Generate HR reports
- **Settings** — Application configuration

## Quick Start

### Prerequisites

- Java 21+
- Maven 3.8+

### Run locally

```bash
git clone <repo-url>
cd hrms
mvn jetty:run
```

The app starts on [http://localhost:8080](http://localhost:8080).

### Default credentials

| Field    | Value    |
|----------|----------|
| Username | `admin`  |
| Password | `password` |

## Tech Stack

| Layer     | Technology              |
|-----------|-------------------------|
| Framework | WebforJ 25.00           |
| Language  | Java 21                 |
| Build     | Maven                   |
| Server    | Jetty 12 (development)  |
| Packaging | WAR                     |

## Project Structure

```
src/main/java/com/webforj/hrms/
├── Application.java          # App entry point
└── views/
    ├── MainLayout.java       # Navigation shell
    ├── LoginView.java        # Authentication
    ├── DashboardView.java    # Home / overview
    ├── AttendanceView.java   # Attendance tracking
    ├── LeavesView.java       # Leave management
    ├── ReportsView.java      # Reporting
    └── SettingsView.java     # Settings
```
