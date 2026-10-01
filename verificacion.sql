-- ============================================================
-- verificacion.sql - Lab07 "Implementacion de Seguridad Basica"
-- Proyecto: demoSeguridad01
-- Uso: C:\xampp\mysql\bin\mysql.exe -uroot < verificacion.sql
--   o  C:\xampp\mysql\bin\mysql.exe -uroot securitydb < verificacion.sql
-- ============================================================

-- 1) Bases de datos visibles
SHOW DATABASES;

-- 2) Tablas de securitydb (si la BD no existe, esta linea da error)
USE securitydb;
SHOW TABLES;

-- 3) Estructura de las tablas principales
SHOW CREATE TABLE roles;
SHOW CREATE TABLE users;
SHOW CREATE TABLE user_roles;

-- Alternativa: descripcion de columnas
DESCRIBE roles;
DESCRIBE users;
DESCRIBE user_roles;

-- 4) Contenido de roles
SELECT id, name FROM roles ORDER BY id;

-- 5) Usuarios: username y si la contrasena esta hasheada con BCrypt ($2a$ / $2b$ / $2y$)
SELECT id,
       username,
       LEFT(password, 7)                     AS password_prefijo,
       CASE
         WHEN password LIKE '$2a$%' THEN 'BCrypt ($2a$)'
         WHEN password LIKE '$2b$%' THEN 'BCrypt ($2b$)'
         WHEN password LIKE '$2y$%' THEN 'BCrypt ($2y$)'
         WHEN password IS NULL OR password = '' THEN '(vacia)'
         ELSE 'NO es BCrypt'
       END                                   AS tipo_password,
       LENGTH(password)                      AS longitud
FROM users
ORDER BY id;

-- 6) Cruce usuarios <-> roles (INNER JOIN sobre user_roles)
SELECT u.id           AS user_id,
       u.username,
       r.id           AS role_id,
       r.name         AS role_name
FROM users u
         INNER JOIN user_roles ur ON ur.user_id = u.id
         INNER JOIN roles r       ON r.id = ur.roles_id
ORDER BY u.username, r.name;

-- 7) Cruce completo (LEFT JOIN: incluye usuarios sin rol asignado)
SELECT u.username,
       COALESCE(GROUP_CONCAT(r.name ORDER BY r.name SEPARATOR ', '), '(sin rol)') AS roles
FROM users u
         LEFT JOIN user_roles ur ON ur.user_id = u.id
         LEFT JOIN roles r       ON r.id = ur.roles_id
GROUP BY u.username
ORDER BY u.username;

-- 8) Resumen rapido
SELECT (SELECT COUNT(*) FROM users)      AS total_usuarios,
       (SELECT COUNT(*) FROM roles)      AS total_roles,
       (SELECT COUNT(*) FROM user_roles) AS total_asignaciones;
