# API Automation Framework: Rest Assured + Karate + TestNG

This project automates API test scenarios using **Rest Assured**, **Karate DSL**, and **TestNG**, designed for both **data-driven testing** and **end-to-end API validation**.  
The framework is fully CI/CD-ready with **Allure reporting** and **Jenkins integration**, and is compatible with CLI-based dynamic input.

---

## 📦 Tech Stack

- **Java 11**
- **Maven**
- **Rest Assured** – for Reqres API
- **Karate DSL** – for Restful-Booker flows
- **TestNG** – test orchestration and parallelism
- **Allure** – reporting for both TestNG and Karate
- **Jenkins** – CI integration with email and report triggers

---

## 📁 Project Structure

```
API_Automation_Using_REST_ASSURED/
├── src/
│   └── test/
│       ├── java/
│       │   ├── base/                 → Base runner for REST Assured
│       │   ├── reqres/tests/         → REST Assured test cases
│       │   ├── runners/              → Karate wrapper runner
│       │   └── utils/                → Config readers, helpers, data providers
│       └── resources/
│           ├── config.properties     → Global config (REST Assured)
│           ├── data/UserIds.csv      → Data for data-driven tests
│           ├── features/             → Karate feature files
│           └── karate-config.js      → Karate environment config
├── pom.xml                           → Maven project configuration
├── testng.xml                        → TestNG suite definition
└── README.md                         → Project overview
```

---

## ✅ Features Covered

### 🔹 REST Assured (Reqres API)
- Fetch user list
- Create, update, patch, delete users
- Data-driven testing via CSV
- CLI-based filtering (`-Duser.id`)
- Utility classes for request handling and logging

### 🔹 Karate (Restful-Booker)
- Token-based auth
- Create → Update → Patch → Delete booking
- API chaining using `call` and loops
- Assertion on payloads and response headers

---

## 🚀 Execution Options

### 1. Run Full Test Suite
```
mvn clean test -DsuiteXmlFile=testng.xml
```

### 2. Run Individual Test Class
```
mvn clean test -Dtest=reqres.tests.UserCrudTests
mvn clean test -Dtest=runners.KarateBookingTestWrapper
```

### 3. Run with CLI Property
```
mvn clean test -DsuiteXmlFile=testng.xml -Duser.id=3
```

---

## 🧪 Allure Reports

After test execution:
```
allure generate --clean
allure open
```

> Ensure Allure CLI is installed  
> `brew install allure` (macOS) or follow [Allure Docs](https://docs.qameta.io/allure/)

---

## 🛠 Jenkins Integration

- Configured as a **Freestyle Job**
- Uses CLI: `mvn clean test -DsuiteXmlFile=testng.xml`
- **Email notifications** for pass/fail
- Allure report generated & archived post-build

---

## 📬 SMTP Integration

- SMTP via Gmail (`smtp.gmail.com:587`)
- Authentication handled with **App Password**
- Post-build emails via `Extended E-mail Notification Plugin`

---

## 👤 Author

**Muhammad Haaris Nayyar**  
Lead SQA Engineer | Automation Specialist  
📧 haaris.nayyar@venturedive.com  
📍 Lahore, Pakistan

---
