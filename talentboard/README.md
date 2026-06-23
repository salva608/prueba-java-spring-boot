# TalentBoard 🚀
> **Sistema Inteligente de Gestión de Vacantes y Postulaciones**

TalentBoard es una aplicación web desarrollada con **Spring Boot, Thymeleaf y PostgreSQL**, diseñada para centralizar y automatizar los procesos de selección de personal. El proyecto está completamente contenerizado utilizando **Docker** y **Docker Compose**, lo que garantiza un despliegue inmediato en cualquier entorno local.

---

## 👥 Roles del Sistema

El sistema implementa seguridad a nivel de vistas y rutas (`Spring Security`), dividiéndose en tres flujos principales:

* **Candidato:** Registro público plano. Permite visualizar las vacantes disponibles, ver detalles de las ofertas y postularse adjuntando notas.
* **Reclutador:** Panel de control privado (`Dashboard`) para gestionar el ciclo de vida de las vacantes (`OPEN` / `CLOSED`) y avanzar los estados de las postulaciones recibidas (`SCREENING`, `INTERVIEWING`, `OFFER`, `REJECTED`).
* **Administrador:** Comparte la gestión global del Reclutador pero con el permiso exclusivo de dar de alta y registrar nuevas cuentas de Reclutadores desde el panel de control, protegiendo el acceso público.

---

## 🛠️ Tecnologías Utilizadas

* **Backend:** Java 21, Spring Boot 3.x, Spring Security, Spring Data JPA.
* **Frontend:** HTML5, Thymeleaf (Renderizado dinámico en servidor).
* **Base de Datos:** PostgreSQL 15.
* **Contenerización:** Docker & Docker Compose (Multi-stage build con Maven y JRE ligero).

---

## 🐳 Arquitectura de Contenedores

El despliegue se compone de dos servicios conectados mediante una red interna aislada (`talentboard_network`):

1.  **`talentboard_db_container`**: Instancia de PostgreSQL expuesta localmente en el puerto `5432`.
2.  **`talentboard_app_container`**: Aplicación Spring Boot que compila el código fuente y expone la interfaz web en el puerto `8080`.

---

## 🚀 Requisitos Previos y Despliegue Local

Para levantar el proyecto completo no necesitas instalar Java, Maven ni PostgreSQL en tu sistema operativo, únicamente debes tener instalado **Docker** y **Docker Compose**.

### Pasos para ejecutar:

1. Clona o descarga este repositorio y ubícate en la carpeta raíz del proyecto.
2. Abre una terminal y ejecuta el siguiente comando para compilar y levantar el entorno:
   ```bash
   docker compose up --build