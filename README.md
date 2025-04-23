# 🩺 Amaiku Users - Servicio de Gestión de Usuarios

Este repositorio forma parte del ecosistema **Amaiku**, una plataforma SaaS diseñada para la gestión de turnos y administración de consultorios médicos pequeños y medianos.

El servicio `amaiku-users` se encarga de la **gestión de usuarios, autenticación, roles y permisos** dentro de la plataforma.

---

## 🔧 Tecnologías utilizadas

- Java 1.8
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

🧪 Endpoints
🔐 Autenticación
Método	Ruta	Descripción
POST	/auth/registro	Registro de un nuevo usuario con rol asignado
POST	/auth/login	Login de usuarios con JWT usando mail y cuenta
PUT	/auth/recuperarPass	Solicita recuperación de contraseña (envía mail)
GET	/auth/validar-token-recuperacion	Verifica si el token de recuperación es válido
POST	/auth/resetear-password	Restablece la contraseña con un token válido
👤 Usuarios
Método	Ruta	Descripción
GET	/usuarios/{usuario}	Obtener información de un usuario específico
PUT	/usuarios	Actualizar estado (activo/inactivo) de un usuario
🏢 Cuentas
Método	Ruta	Descripción
POST	/cuenta	Crear una nueva cuenta (requiere rol AMAIKU)
PUT	/cuenta	Actualizar una cuenta (requiere rol AMAIKU o ADMIN_CUENTA)

---

## 🔐 Autenticación

Este servicio utiliza **JWT**. Una vez autenticado, el usuario recibe un token que debe enviarse en cada request a los otros servicios.

Ejemplo de header:

```http
Authorization: Bearer {TOKEN}
