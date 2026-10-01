# Actividad Lab07 - Mapeo de Puntos

## Estado: Todos los puntos implementados y compilación exitosa

### Punto 1: Cambiar las rutas actuales
- `/admin/panel` → `/management/dashboard` en `AdminController.java:13`
- `/user/dashboard` → `/client/home` en `UserController.java:13`
- `/public/hello` → `/api/free` en `PublicController.java:13`
- Reglas de seguridad en `SecurityConfig.java:46-52`

### Punto 2: Crear nuevo rol: ROLE_MANAGER
- Definido en `model/Role.java` (línea 12: `ROLE_MANAGER`)
- Cargado en `DataLoader.java:28`: `getOrCreateRole(roleRepo, "ROLE_MANAGER")`

### Punto 3: Crear un usuario: manager
- Creado en `DataLoader.java:33`: `saveUser(userRepo, encoder, "manager", "manager123", managerRole)`
- Usuario con credenciales: `manager` / `manager123`

### Punto 4: Crear endpoint: /manager/reportes
- Controlador: `ManagerController.java:16-18`
- `@RequestMapping("/manager")` + `@GetMapping("/reportes")`

### Punto 5: Cambiar contraseña a cada usuario
- BCrypt password encoding en `DataLoader.java:61`
- Todos los usuarios (user, admin, manager) tienen contraseñas hasheadas

### Punto 6: Realizar Pruebas en Postman
- Documentación en `PRUEBAS_POSTMAN.md:63-68`
- 5 casos de prueba completos en la matriz

## Compilación
- **Error arreglado**: `Cannot resolve method 'getRoles'` etc.
- **Causa**: `lombok` dependency tenía `<optional>true</optional>` en `pom.xml:59`
- **Solución**: Cambiado a `<optional>false</optional>` en `pom.xml:59`
- **Resultado**: `BUILD SUCCESS` con `clean compile`