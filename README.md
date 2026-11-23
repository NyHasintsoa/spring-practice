# 🚀 Spring Api Resource

This backlog tracks the key features and tasks for implementing JWT authentication in the project.  
Statuses follow the legend below and are updated as development progresses.

---

## 📜 Table of Contents

1. [🚀 Spring Api Resource](#-spring-api-resource)
   1. [📜 Table of Contents](#-table-of-contents)
   2. [📖 About the Project](#-about-the-project)
   3. [✨ Features](#-features)
   4. [🛠 Tech Stack](#-tech-stack)
   5. [🚀 Getting Started](#-getting-started)
      1. [Prerequisites](#prerequisites)
      2. [Installation](#installation)
      3. [Running the App](#running-the-app)
   6. [⚙ Configuration](#-configuration)
   7. [📦 Usage](#-usage)
   8. [📚 API Documentation](#-api-documentation)
   9. [📂 Project Structure](#-project-structure)
   10. [🔐 Security Notes](#-security-notes)
   11. [🤝 Contributing](#-contributing)
   12. [📄 License](#-license)
   13. [📬 Contact](#-contact)

---

## 📖 About the Project

This is a **Spring Boot Maven** application that implements **JWT-based authentication** with RSA keys.  
The private and public keys are loaded from the `resources/jwt/` directory, allowing secure token signing and verification.

---

## ✨ Features

- ✅ Secure JWT authentication using **RSA** keys
- ✅ REST API with **Spring Boot**
- ✅ Maven project structure

---

## 🛠 Tech Stack

- **Java** 17+ (or your project’s Java version)
- **Spring Boot** 4.2.x
- **Maven**
- **JWT (Java JWT / jjwt)**

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

- Java 17+ (`java -version`)
- Maven (`mvn -v`)
- Git

### Installation

```bash
# Clone the repository
git clone https://github.com/NyHasintsoa/your-project-name.git

# Go into the project directory
cd your-project-name

# Init the project
make init
```

### Running the App

Development (Spring Boot embedded server)

```bash
mvn clean spring-boot:run
```

Access the app at: http://localhost:8080

## ⚙ Configuration

RSA Keys
Keys are stored in:

```swift
src/main/resources/jwt/private.pem
src/main/resources/jwt/public.pem
```

Development: keys are loaded from the classpath.

Production: consider using environment variables or an external key file for security.

## 📦 Usage

Example code to load keys:

```bash
make keypair
```

## 📚 API Documentation

For API documentation, you can integrate:

Springdoc OpenAPI for Swagger UI

Postman Collection for API testing

## 📂 Project Structure

```bash
.
├── src
│   ├── main
│   │   ├── java/com/exercise/project # Project Source Code
│   │   └── resources # Project Resources
│   │       ├── application.properties # Main properties of the application
│   │       ├── application.properties.example # Example of application's properties
│   │       ├── jwt # Keypair's Storage
│   │       ├── static
│   │       └── templates
│   └── test/
└── target/

```

## 🔐 Security Notes

Never commit real production keys to the repository.

Use .gitignore to exclude sensitive files.

Restrict file permissions on server: chmod 600 private.pem.

Rotate keys periodically.

## 🤝 Contributing

Fork the project

Create your feature branch (git checkout -b feature/amazing-feature)

Commit changes (git commit -m 'Add amazing feature')

Push to branch (git push origin feature/amazing-feature)

Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📬 Contact

Author: Ny Aina Hasintsoa

Email: hasinaramalanjaona@gmail.com

GitHub: NyHasintsoa

---
