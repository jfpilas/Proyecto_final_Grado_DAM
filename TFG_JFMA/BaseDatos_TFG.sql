-- 1️⃣ Eliminar la base de datos si existe y luego crearla
DROP DATABASE IF EXISTS GESTIONPARKING;
CREATE DATABASE IF NOT EXISTS GESTIONPARKING;
USE GESTIONPARKING;

-- Tabla usuario
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    ID_Usuario INTEGER PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Telefono VARCHAR(15) NOT NULL,
    Contraseña VARCHAR(255) NOT NULL,
    Rol ENUM('Administrador', 'Recepcionista', 'Cliente'),
    Nombre_usuario VARCHAR(50) NOT NULL
);

-- Tabla cliente
DROP TABLE IF EXISTS cliente;
CREATE TABLE cliente (
    ID_Cliente INTEGER PRIMARY KEY AUTO_INCREMENT, 
    Direccion VARCHAR(50) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    Dni VARCHAR(15) UNIQUE NOT NULL,
    Fecha_Nacimiento DATE NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
    Telefono VARCHAR(15) NOT NULL
);

-- Tabla plaza
DROP TABLE IF EXISTS plaza;
CREATE TABLE plaza (
    ID_Plaza INTEGER PRIMARY KEY AUTO_INCREMENT,
    Numero_Plaza VARCHAR(10) UNIQUE NOT NULL,
    Tipo ENUM('Normal', 'VIP', 'Carga eléctrica') NOT NULL,
    Estado ENUM('Libre', 'Ocupada', 'Reservada') DEFAULT 'Libre',
    Tarifa DECIMAL(10,2) NOT NULL
);

-- Tabla coche
DROP TABLE IF EXISTS coche;
CREATE TABLE coche (
    ID_Coche INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_Cliente INTEGER,
    Matricula VARCHAR(20) UNIQUE NOT NULL,
    Marca VARCHAR(50),
    Modelo VARCHAR(50),
    Color VARCHAR(20),
    Tipo ENUM('Normal', 'VIP', 'Carga eléctrica'),
    FOREIGN KEY (ID_Cliente) REFERENCES cliente(ID_Cliente) ON DELETE CASCADE
);

-- Tabla reserva
DROP TABLE IF EXISTS reserva;
CREATE TABLE reserva (
    ID_Reserva INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_Cliente INTEGER NOT NULL,
    ID_Coche INTEGER NOT NULL,
    ID_Plaza INTEGER NULL,
    Fecha_Entrada DATETIME NOT NULL,
    Fecha_Salida DATETIME NOT NULL,
    Estado ENUM('Activa', 'Finalizada', 'Cancelada') DEFAULT 'Activa',
    FOREIGN KEY (ID_Cliente) REFERENCES cliente(ID_Cliente) ON DELETE CASCADE,
    FOREIGN KEY (ID_Coche) REFERENCES coche(ID_Coche) ON DELETE CASCADE,
    FOREIGN KEY (ID_Plaza) REFERENCES plaza(ID_Plaza) ON DELETE SET NULL
);

-- Tabla factura
DROP TABLE IF EXISTS factura;
CREATE TABLE factura (
    ID_Factura INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_Cliente INTEGER NOT NULL,
    ID_Reserva INTEGER UNIQUE NOT NULL,
    Monto_Total DECIMAL(10,2) NOT NULL,
    Fecha_Emision DATETIME DEFAULT CURRENT_TIMESTAMP,
    Estado ENUM('Pagada', 'Pendiente') DEFAULT 'Pendiente',
    FOREIGN KEY (ID_Cliente) REFERENCES cliente(ID_Cliente) ON DELETE CASCADE,
    FOREIGN KEY (ID_Reserva) REFERENCES reserva(ID_Reserva) ON DELETE CASCADE
);

-- Tabla pago
DROP TABLE IF EXISTS pago;
CREATE TABLE pago (
    ID_Pago INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_Factura INTEGER NOT NULL,
    Monto_Pagado DECIMAL(10,2) NOT NULL,
    Fecha_Pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    Metodo_Pago ENUM('Tarjeta', 'Efectivo', 'Transferencia'),
    FOREIGN KEY (ID_Factura) REFERENCES factura(ID_Factura) ON DELETE CASCADE
);

-- Tabla notificacion
DROP TABLE IF EXISTS notificacion;
CREATE TABLE notificacion (
    ID_Notificacion INTEGER PRIMARY KEY AUTO_INCREMENT,
    ID_Cliente INTEGER NOT NULL,
    Mensaje TEXT NOT NULL,
    Fecha_Envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    Estado ENUM('Enviada', 'Pendiente') DEFAULT 'Pendiente',
    FOREIGN KEY (ID_Cliente) REFERENCES cliente(ID_Cliente) ON DELETE CASCADE
);

-- Tabla reporte
DROP TABLE IF EXISTS reporte;
CREATE TABLE reporte (
    ID_Reporte INTEGER PRIMARY KEY AUTO_INCREMENT,
    Tipo_Reporte VARCHAR(50) NOT NULL,
    Fecha_Generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Datos_Reporte TEXT NOT NULL
);


INSERT INTO usuario (Nombre,Apellido ,Email,Telefono,Contraseña ,Rol,Nombre_usuario) VALUES("Juanfran","Marque","asdasdas@gmail.com","6543216789","1234","Administrador","jfpilas");
INSERT INTO plaza (Numero_Plaza, Tipo, Estado, Tarifa) VALUES
('P001', 'Normal', 'Libre', 5.00),
('P002', 'Normal', 'Libre', 5.00),
('P003', 'VIP', 'Ocupada', 10.00),
('P004', 'VIP', 'Libre', 10.00),
('P005', 'Carga eléctrica', 'Libre', 8.00),
('P006', 'Carga eléctrica', 'Ocupada', 8.00),
('P007', 'Normal', 'Libre', 5.00),
('P008', 'Normal', 'Libre', 5.00),
('P009', 'VIP', 'Reservada', 10.00),
('P010', 'VIP', 'Libre', 10.00),
('P011', 'Carga eléctrica', 'Libre', 8.00),
('P012', 'Carga eléctrica', 'Libre', 8.00);