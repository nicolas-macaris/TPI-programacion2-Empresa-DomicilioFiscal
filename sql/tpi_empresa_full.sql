CREATE DATABASE IF NOT EXISTS tpi_empresa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tpi_empresa;

DROP TABLE IF EXISTS empresa;

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

DROP TABLE IF EXISTS domicilio_fiscal;

CREATE TABLE domicilio_fiscal (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    eliminado TINYINT(1) NOT NULL DEFAULT 0,
    calle VARCHAR(100) NOT NULL,
    numero INT,
    ciudad VARCHAR(80) NOT NULL,
    provincia VARCHAR(80) NOT NULL,
    codigo_postal VARCHAR(10),
    pais VARCHAR(80) NOT NULL,

    empresa_id BIGINT UNSIGNED,

    PRIMARY KEY (id),
    
    UNIQUE KEY uk_domiciliofis_empresaid (empresa_id),

    CONSTRAINT fk_domicilio_empresa
        FOREIGN KEY (empresa_id)
        REFERENCES empresa(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE = InnoDB;

USE tpi_empresa;

INSERT INTO empresa (eliminado, razon_social, cuit, actividad_principal, email)
VALUES
(0, 'Empresa SRL', '30123456789', 'Desarrollo de software', 'contacto@empresa.com'),
(0, 'Distribuidora SA', '30876543210', 'Distribución mayorista', 'info@distribuidora.com');

INSERT INTO domicilio_fiscal (eliminado, calle, numero, ciudad, provincia, codigo_postal, pais, empresa_id)
VALUES
(0, 'Av. Siempre Viva', 742, 'Buenos Aires', 'Buenos Aires', '1000', 'Argentina', 1),
(0, 'Calle Falsa', 123, 'Córdoba', 'Córdoba', '5000', 'Argentina', 2);
