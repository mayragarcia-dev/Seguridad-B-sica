=== POSTMAN TESTS CONFIGURATION ===

Collection: Lab07 Security Tests
Base URL: http://localhost:8080

Endpoints and expected results:

1. GET /api/free
   - Usuario: ninguno (público)
   - Rol: público
   - Resultado esperado: 200 OK
   - Cuerpo: "Endpoint público funcionando"

2. GET /client/home
   - Usuario: user | Rol: USER
   - Resultado esperado: 200 OK
   - Cuerpo: "Bienvenido USER"

3. GET /client/home
   - Usuario: admin | Rol: ADMIN
   - Resultado esperado: 200 OK
   - Cuerpo: "Bienvenido USER" (mismo endpoint comparte acceso)

4. GET /management/dashboard
   - Usuario: admin | Rol: ADMIN
   - Resultado esperado: 200 OK
   - Cuerpo: "Bienvenido ADMIN"

5. GET /management/dashboard
   - Usuario: user | Rol: USER
   - Resultado esperado: 403 Forbidden

6. GET /manager/reportes
   - Usuario: manager | Rol: MANAGER
   - Resultado esperado: 200 OK
   - Cuerpo: "Bienvenido MANAGER"

7. GET /manager/reportes
   - Usuario: user | Rol: USER
   - Resultado esperado: 403 Forbidden

8. GET /management/dashboard (sin autenticar)
   - Usuario: ninguno
   - Rol: —
   - Resultado esperado: 401 Unauthorized

=== POSTMAN TEST SCRIPTS ===

Para cada request en Postman, agregar el script de pruebas:

```javascript
// Para pruebas de autenticación
pm.test("Status code is 200", function () {
    pm.expect(pm.response.code).to.equal(200);
});

pm.test("Status code is 401", function () {
    pm.expect(pm.response.code).to.equal(401);
});

pm.test("Status code is 403", function () {
    pm.expect(pm.response.code).to.equal(403);
});

// Para pruebas con autenticación Basic Auth
pm.toHaveStatus(200);
```

=== TABLE DE VERIFICACIÓN ===

| Endpoint                | Usuario | Rol     | Resultado esperado |
| ----------------------- | ------- | ------- | ------------------ |
| /api/free               | ninguno | público | 200                |
| /client/home            | user    | USER    | 200                |
| /client/home            | admin   | ADMIN   | 200                |
| /management/dashboard   | admin   | ADMIN   | 200                |
| /management/dashboard   | user    | USER    | 403                |
| /manager/reportes       | manager | MANAGER | 200                |
| /manager/reportes       | user    | USER    | 403                |
| /management/dashboard   | ninguno | —       | 401                |

=== RESUMEN DE ROLES Y USUARIOS ===

Roles creados:
- ROLE_USER (id: 1) asignado a user
- ROLE_ADMIN (id: 2) asignado a admin
- ROLE_MANAGER (id: 3) asignado a manager

Usuarios con contraseñas BCrypt:
- user / password: $2a$10$... (user123)
- admin / password: $2a$10$... (admin123)
- manager / password: $2a$10$... (manager123)

=== CONFIGURACIÓN BASIC AUTH ===

Usar Basic Authentication en Postman con:
- Username: user, admin, o manager
- Password: user123, admin123, o manager123

Las contraseñas están cifradas con BCrypt en la base de datos, pero Spring Security las compara automáticamente durante la autenticación Basic Auth.