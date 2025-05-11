# Restaurant Management System

![Java](https://img.shields.io/badge/Java-22-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-21-orange)
![License](https://img.shields.io/badge/License-MIT-green)

A comprehensive restaurant management application with both CLI and GUI interfaces, developed by:
- **Armen Meliksetyan**
- **Davit Baghdasaryan** 
- **Rafayel Manasyan**

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Build Instructions](#build-instructions)
- [Project Structure](#project-structure)
- [License](#license)

## Features
- **Dual Interface Mode**
  - Graphical User Interface (GUI)
  - Command Line Interface (CLI)
- **Role-Based Access**
  - Admin: Full system control
  - Customer: Order placement and tracking
- **Core Functionalities**
  - Menu management (add/edit/remove items)
  - Order processing system
  - User authentication (login/registration)
  - Data persistence

## Requirements
- **JDK 22** ([Download](https://www.oracle.com/java/technologies/downloads/))
- **JavaFX 21** ([Download](https://gluonhq.com/products/javafx/)) (for GUI mode)
- Maven/Gradle (for building)

  src/
├── main/
│   ├── java/
│   │   ├── com.example.oopproject/
│   │   │   ├── cli/          # CLI implementation
│   │   │   ├── ui/           # JavaFX GUI implementation
│   │   │   ├── core/         # Business logic
│   │   │   └── Main.java     # Entry point
│   └── resources/            # FXML files and assets


## Usage
- **Use -ui as an argument while running the program to open UI and for CLI use argument -cli
