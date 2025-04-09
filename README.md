# 🩺 Amaiku Users - Servicio de Gestión de Usuarios

Este repositorio forma parte del ecosistema **Amaiku**, una plataforma SaaS diseñada para la gestión de turnos y administración de consultorios médicos pequeños y medianos.

El servicio `amaiku-users` se encarga de la **gestión de usuarios, autenticación, roles y permisos** dentro de la plataforma.

---

## 🔧 Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Security + JWT
- Docker
- PostgreSQL
- Maven

---

## 📦 Funcionalidades principales

- Registro y autenticación de usuarios
- Login con generación de token JWT
- Asignación y validación de roles (profesional, secretaria, admin)
- Soporte multiclínica (en desarrollo)
- Asociación con otros servicios via REST

---

## 🧪 Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/auth/login` | Login de usuarios con JWT |
| `GET` | `/users/{id}` | Obtener info de un usuario |
| `GET` | `/users` | Listado completo de usuarios |
| `POST` | `/users` | Crear nuevo usuario |
| `GET` | `/roles` | Obtener lista de roles disponibles |

---

## 🔐 Autenticación

Este servicio utiliza **JWT**. Una vez autenticado, el usuario recibe un token que debe enviarse en cada request a los otros servicios.

Ejemplo de header:

```http
Authorization: Bearer {TOKEN}
