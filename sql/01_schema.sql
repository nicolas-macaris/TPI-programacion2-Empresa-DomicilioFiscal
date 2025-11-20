-- Crear base de datos
CREATE DATABASE IF NOT EXISTS tpi_empresa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tpi_empresa;

-- ============================================
-- Tabla EMPRESA (A)
-- ============================================
CREATE TABLE empresa (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    razon_social VARCHAR(120) NOT NULL,
    cuit VARCHAR(13) NOT NULL,
    actividad_principal VARCHAR(80),
    email VARCHAR(120),
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_empresa_cuit (cuit)
) ENGINE = InnoDB;

-- ============================================
-- Tabla DOMICILIO_FISCAL (B)
-- ============================================
CREATE TABLE domicilio_fiscal (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    calle VARCHAR(100) NOT NULL,
    numero INT,
    ciudad VARCHAR(80) NOT NULL,
    provincia VARCHAR(80) NOT NULL,
    codigo_postal VARCHAR(10),
    pais VARCHAR(80) NOT NULL,
    
    -- Clave foránea 1→1 hacia EMPRESA
    empresa_id BIGINT UNSIGNED,
    
    PRIMARY KEY (id),
    UNIQUE KEY uk_domiciliofis_empresaid (empresa_id),
    CONSTRAINT fk_domicilio_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES empresa(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;
