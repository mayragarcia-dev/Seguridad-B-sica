# Seguridad Básica - Lab07

## Descripción
Aplicación Spring Boot que implementa seguridad básica con autenticación Basic Auth y roles de usuario (USER, ADMIN, MANAGER).

## Características Implementadas

### 1. Rutas Actualizadas
- `/management/dashboard` - Panel de administracion (requiere rol ADMIN)
- `/client/home` - Area del usuario (requiere rol USER o ADMIN)
- `/api/free` - Endpoint publico (sin autenticacion)
- `/manager/reportes` - Reportes del gerente (requiere rol MANAGER)

### 2. Roles del Sistema
- `ROLE_USER` - Usuario estandar
- `ROLE_ADMIN` - Administrador del sistema
- `ROLE_MANAGER` - Gerente (nuevo rol agregado)

### 3. Usuarios Creados
| Usuario | Contrasena | Rol |
|---------|------------|-----|
| user | user123 | ROLE_USER |
| admin | admin123 | ROLE_ADMIN |
| manager | manager123 | ROLE_MANAGER |

### 4. Endpoints Principales
- `GET /api/free` - Acceso publico sin autenticacion
- `GET /client/home` - Area del usuario autenticado
- `GET /management/dashboard` - Panel de administracion (solo ADMIN)
- `GET /manager/reportes` - Reportes del gerente (solo MANAGER)

### 5. Seguridad
- Contraseñas cifradas con BCrypt
- Autenticacion Basic Auth
- Reglas de autorizacion por ruta

### 6. Pruebas en Postman
Coleccion minima de pruebas:

1. `GET http://localhost:8080/api/free` - sin autenticacion -> 200
2. `GET http://localhost:8080/client/home` - Basic Auth user/user123 -> 200
3. `GET http://localhost:8080/management/dashboard` - Basic Auth admin/admin123 -> 200
4. `GET http://localhost:8080/manager/reportes` - Basic Auth manager/manager123 -> 200
5. Casos negativos: repetir con distinto usuario o sin credenciales -> 401 / 403

## Tecnologías
- Spring Boot 3.x
- Spring Security
- BCrypt Password Encoding
- Maven
- MySQL / H2 Database