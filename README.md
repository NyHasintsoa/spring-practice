# 🚀 Spring Api Resource

This backlog tracks the key features and tasks for implementing JWT authentication in the project.  
Statuses follow the legend below and are updated as development progresses.  
Use Spring MCP Client to perform connexion with MCP Server from express.

---

## 📜 Table of Contents

- [About the Project](#📖-about-the-project)
- [Features](#✨-features)
- [Tech Stack](#🛠-tech-stack)
- [Getting Started](#🚀-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the App](#running-the-app)
- [Configuration](#⚙-configuration)
- [Usage](#📦-usage)
- [API Documentation](#📚-api-documentation)
- [Project Structure](#📂-project-structure)
- [Security Notes](#🔐-security-notes)
- [Contributing](#🤝-contributing)
- [License](#📄-license)
- [Contact](#📬-contact)

---

## 📖 About the Project

This is a **Spring Boot Maven** application that implements **JWT-based authentication** with RSA keys.  
The private and public keys are loaded from the `resources/jwt/` directory, allowing secure token signing and verification.

---

## ✨ Features

- ✅ Secure JWT authentication using **RSA** keys
- ✅ REST API with **Spring Boot**
- ✅ Maven project structure
- ✅ Ready for deployment on **Tomcat**
- ✅ External configuration for production security

---

## 🛠 Tech Stack

- **Java** 17+ (or your project’s Java version)
- **Spring Boot** 3.5.x
- **Maven**
- **Tomcat** (deployment)
- **JWT (Java JWT / jjwt)**

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

- Java 17+ (`java -version`)
- Maven (`mvn -v`)
- Git
- Tomcat (for deployment) or use embedded Spring Boot server for dev

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

Production (Tomcat)
Deploy the generated WAR from target/ into your Tomcat webapps/ directory.

```bash
make deploy
```

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
