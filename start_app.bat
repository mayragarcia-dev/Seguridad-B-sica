@echo off
cd /d "%~dp0"
echo Iniciando aplicación...
set MAVEN_OPTS=-Dspring.datasource.url=jdbc:mysql://localhost:3306/securitydb -Dspring.datasource.username=root
call .\mvnw spring-boot:run