# Pruebas en Postman — Lab07 Seguridad Básica

Servidor: `http://localhost:8080`
Método: **GET** en todos los casos.
Autenticación: **Basic Auth** (en la pestaña *Authorization* → *Basic Auth*).

## Usuarios y roles

| Usuario  | Contraseña   | Rol          |
|----------|--------------|--------------|
| `user`   | `user123`    | `ROLE_USER`   |
| `admin`  | `admin123`   | `ROLE_ADMIN`  |
| `manager`| `manager123` | `ROLE_MANAGER`|

Las contraseñas se almacenan cifradas con BCrypt; el `DataLoader` las (re)genera en
cada arranque, por eso el cambio de contraseña siempre surte efecto.

## Rutas y permisos

| Ruta anterior     | Ruta nueva              | Regla                       |
|-------------------|-------------------------|-----------------------------|
| `/public/hello`   | `/api/free`             | `permitAll()`               |
| `/user/dashboard` | `/client/home`          | `hasAnyRole("USER","ADMIN")`|
| `/admin/panel`    | `/management/dashboard` | `hasRole("ADMIN")`          |
| —                 | `/manager/reportes`     | `hasRole("MANAGER")`        |

## Matriz de pruebas

| # | Endpoint | Sin auth | `user` | `admin` | `manager` |
|---|----------|----------|--------|---------|-----------|
| 1 | `/api/free`             | **200** | 200 | 200 | 200 |
| 2 | `/client/home`          | 401 | **200** | 200 | 403 |
| 3 | `/management/dashboard` | 401 | 403 | **200** | 403 |
| 4 | `/manager/reportes`     | 401 | 403 | 403 | **200** |

Resultados obtenidos ejecutando la aplicación (MySQL `securitydb` activa):

```text
200 | /api/free             | (sin auth) | Endpoint público funcionando
200 | /api/free             | user       | Endpoint público funcionando
200 | /api/free             | admin      | Endpoint público funcionando
200 | /api/free             | manager    | Endpoint público funcionando

401 | /client/home          | (sin auth) | ACCESO DENEGADO
200 | /client/home          | user       | Bienvenido USER
200 | /client/home          | admin      | Bienvenido USER
403 | /client/home          | manager    | ACCESO DENEGADO

401 | /management/dashboard | (sin auth) | ACCESO DENEGADO
403 | /management/dashboard | user       | ACCESO DENEGADO
200 | /management/dashboard | admin      | Bienvenido ADMIN
403 | /management/dashboard | manager    | ACCESO DENEGADO

401 | /manager/reportes     | (sin auth) | ACCESO DENEGADO
403 | /manager/reportes     | user       | ACCESO DENEGADO
403 | /manager/reportes     | admin      | ACCESO DENEGADO
200 | /manager/reportes     | manager    | Bienvenido MANAGER
```

- `401 Unauthorized`: no se enviaron credenciales.
- `403 Forbidden`: el usuario se autenticó pero su rol no autoriza la ruta.

## Colección Postman mínima

1. `GET http://localhost:8080/api/free` — sin autenticación → `200`
2. `GET http://localhost:8080/client/home` — Basic Auth `user` / `user123` → `200`
3. `GET http://localhost:8080/management/dashboard` — Basic Auth `admin` / `admin123` → `200`
4. `GET http://localhost:8080/manager/reportes` — Basic Auth `manager` / `manager123` → `200`
5. Casos negativos: repetir cada ruta con otro usuario o sin credenciales → `401` / `403`