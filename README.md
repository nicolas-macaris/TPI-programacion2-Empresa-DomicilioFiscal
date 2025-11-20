# Trabajo Práctico Integrador – Programación 2  
## Empresa → Domicilio Fiscal (Relación 1→1 unidireccional)

Este proyecto implementa una aplicación Java que modela una relación **1→1 unidireccional** entre:
- **Empresa (A)**  
- **DomicilioFiscal (B)**  

La aplicación utiliza JDBC, MySQL, patrón DAO + Service, transacciones (commit/rollback) y un menú de consola para gestionar el CRUD completo.

---

## 🏗️ Tecnologías utilizadas

- Java 21 (o compatible)
- MySQL 8.x (o MariaDB via XAMPP)
- JDBC (sin ORM)
- NetBeans
- MySQL Connector/J (driver)
- PlantUML (para diagramas)

---

## 📌 Estructura del proyecto (paquetes)

```
src/
 ├── config/
 │    └── DatabaseConnection.java
 ├── entities/
 │    ├── Empresa.java
 │    └── DomicilioFiscal.java
 ├── dao/
 │    ├── GenericDao.java
 │    ├── EmpresaDao.java
 │    └── DomicilioFiscalDao.java
 ├── service/
 │    ├── GenericService.java
 │    ├── EmpresaService.java
 │    └── DomicilioFiscalService.java
 └── main/
      ├── AppMenu.java
      └── MainApp.java
```

---

## 📌 Relación del dominio

La relación implementada es:

```
Empresa (A) 1 ---- 1 DomicilioFiscal (B)
(unidireccional: Empresa referencia a DomicilioFiscal)
```

Se garantiza mediante una **clave foránea única** en la tabla `domicilio_fiscal`.

---

## 🗄️ Base de datos (MySQL)

### Crear la base y las tablas

Ejecutar el archivo:
```
sql/01_schema.sql
```
Este script crea:

- Base `tpi_empresa`
- Tabla `empresa`
- Tabla `domicilio_fiscal`
- Relación 1→1 por `empresa_id` UNIQUE en `domicilio_fiscal`

### Insertar datos de prueba

Ejecutar:
```
sql/02_data.sql
```

---

## ⚙️ Configuración de la conexión

Editar el archivo:

```
src/db.properties
```

Ejemplo:

```properties
db.url=jdbc:mysql://localhost:3306/tpi_empresa?useSSL=false&serverTimezone=UTC
db.username=TU_USUARIO
db.password=TU_PASSWORD
```

---

## 🧩 Cómo ejecutar el proyecto

1. Abrí NetBeans  
2. Cargá el proyecto  
3. Asegurate de tener MySQL iniciado  
4. Ejecutá la clase:

```
main.MainApp
```

---

## 🖥️ Funcionalidades implementadas (menú)

✔ Crear Empresa (con o sin domicilio)  
✔ Listar Empresas  
✔ Buscar por ID  
✔ Actualizar Empresa  
✔ Eliminar (baja lógica)  
✔ Búsqueda por CUIT  
✔ Operación transaccional (commit/rollback)  

---

## ⚡ Función especial para mostrar rollback

Si el CUIT ingresado comienza con **“999”**, el sistema fuerza un error:

```
throw new RuntimeException("Error forzado para demostrar rollback");
```

---

## 👥 Integrantes del equipo

Nicolas Macaris
Maria Sol Couchot

---

## 🎥 Video demostrativo

🔗 Enlace al video 
TODO

---
