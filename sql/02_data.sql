USE tpi_empresa;

-- EMPRESAS
INSERT INTO empresa (eliminado, razon_social, cuit, actividad_principal, email)
VALUES
(0, 'Tech Solutions SRL', '30-12345678-9', 'Desarrollo de software', 'contacto@techsolutions.com'),
(0, 'Distribuidora El Sol SA', '30-87654321-0', 'Distribución mayorista', 'info@elsol.com');

-- DOMICILIOS FISCALES
INSERT INTO domicilio_fiscal (eliminado, calle, numero, ciudad, provincia, codigo_postal, pais, empresa_id)
VALUES
(0, 'Av. Siempre Viva', 742, 'Buenos Aires', 'Buenos Aires', '1000', 'Argentina', 1),
(0, 'Calle Falsa', 123, 'Córdoba', 'Córdoba', '5000', 'Argentina', 2);
