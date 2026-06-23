# TalentBoard 🚀
> **Intelligent Job Vacancy and Application Management System**
>
> **GitHub Repository:** https://github.com/salva608/prueba-java-spring-boot.git

TalentBoard is a web application developed with **Spring Boot, Thymeleaf, and PostgreSQL**, designed to centralize and automate recruitment processes. The project is fully containerized using **Docker** and **Docker Compose**, ensuring an immediate deployment in any local environment.

---

## 👥 System Roles

The system implements security at the view and route level (`Spring Security`), dividing into three main flows:

* **Candidate:** Plain public registration. Allows viewing available vacancies, seeing offer details, and applying by attaching notes.
* **Recruiter:** Private control panel (`Dashboard`) to manage the vacancy lifecycle (`OPEN` / `CLOSED`) and advance the status of received applications (`SCREENING`, `INTERVIEWING`, `OFFER`, `REJECTED`).
* **Administrator:** Shares the global management of the Recruiter but with the exclusive permission to onboard and register new Recruiter accounts from the control panel, protecting public access.

---

## 🛠️ Technologies Used

* **Backend:** Java 21, Spring Boot 3.x, Spring Security, Spring Data JPA.
* **Frontend:** HTML5, Thymeleaf (Server-side dynamic rendering).
* **Database:** PostgreSQL 15.
* **Containerization:** Docker & Docker Compose (Multi-stage build with Maven and lightweight JRE).

---

## 🐳 Container Architecture

The deployment consists of two services connected via an isolated internal network (`talentboard_network`):

1. **`talentboard_db_container`**: PostgreSQL instance exposed locally on port `5432`.
2. **`talentboard_app_container`**: Spring Boot application that compiles the source code and exposes the web interface on port `8080`.

---

## 🚀 Prerequisites and Local Deployment

To spin up the entire project, you do not need to install Java, Maven, or PostgreSQL on your operating system; you only need to have **Docker** and **Docker Compose** installed.

### Steps to run:

1. Clone or download this repository (`git clone https://github.com/salva608/prueba-java-spring-boot.git`) and navigate to the project's root folder.
2. Open a terminal and run the following command to compile and bring up the environment:
   ```bash
   docker compose up --build