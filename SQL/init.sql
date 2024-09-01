-- init.sql

CREATE TABLE Acciones (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    clienteid VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);


-- Create Persona table
CREATE TABLE Persona (
    identificacion VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(10),
    edad INT,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Create Cliente table
CREATE TABLE Cliente (
    clienteid VARCHAR(50) PRIMARY KEY,
    contrasena VARCHAR(100) NOT NULL,
    estado BOOLEAN NOT NULL,
    identificacion VARCHAR(50) NOT NULL,
    FOREIGN KEY (identificacion) REFERENCES Persona(identificacion) ON DELETE CASCADE
);

-- Create Cuenta table
CREATE TABLE Cuenta (
    numero_cuenta  VARCHAR(50) PRIMARY KEY,
    tipo_cuenta VARCHAR(50) NOT NULL,
    saldo_inicial NUMERIC(15, 2) NOT NULL,
    estado BOOLEAN NOT NULL,
    clienteid VARCHAR(50) NOT NULL,
    FOREIGN KEY(clienteid) references Cliente(clienteid) ON DELETE CASCADE
);

-- Create Movimientos table
CREATE TABLE Movimientos (
    id SERIAL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR(50) NOT NULL,
    valor NUMERIC(15, 2) NOT NULL,
    saldo NUMERIC(15, 2) NOT null,
       numero_cuenta VARCHAR(50) NOT NULL,
    FOREIGN KEY (numero_cuenta) REFERENCES Cuenta(numero_cuenta)ON DELETE CASCADE
);

-- init.sql

-- Insert data into Persona table
INSERT INTO Persona (identificacion, nombre, genero, edad, direccion, telefono) VALUES
('ID001', 'Jose Lema', 'M', 35, 'Otavalo sn y principal', '098254785'),
('ID002', 'Marianela Montalvo', 'F', 28, 'Amazonas y NNUU', '097548965'),
('ID003', 'Juan Osorio', 'M', 40, '13 junio y Equinoccial', '098874587');

-- Insert data into Cliente table
INSERT INTO Cliente (clienteid, contrasena, estado, identificacion) VALUES
('CL001', '1234', 'TRUE', 'ID001'),
('CL002', '5678', 'TRUE', 'ID002'),
('CL003', '1245', 'TRUE', 'ID003');



-- Insert data into Cuenta table
INSERT INTO Cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, clienteid) VALUES
('478758', 'Ahorro', 2000.00, 'TRUE', 'CL001'),  -- Jose Lema
('225487','Corriente', 100.00, 'TRUE', 'CL002'), -- Marianela Montalvo
('495878','Ahorros', 0.00, 'TRUE', 'CL003'), -- Juan Osorio
('496825', 'Ahorros', 540.00, 'TRUE', 'CL002'), -- Marianela Montalvo
('585545','Corriente', 1000.00, 'TRUE', 'CL001'); -- Jose Lema



-- Insert data into Movimientos table

-- Jose Lema: Crea su cuenta y deposita (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 2000.00, 2000.00, '478758');  -- saldo_inicial - retiro


-- Marianela Montalvo: Crea su cuenta y deposita (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 100, 100, '225487');  -- saldo_inicial - retiro

-- Juan Osorio: Crea su cuenta y deposita (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 0, 0, '495878');  -- saldo_inicial - retiro

-- Marianela Montalvo: Crea su cuenta y deposita (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 540, 540, '496825');  -- saldo_inicial - retiro


-- Marianela Montalvo: Crea su cuenta y deposita (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 1000.00, 1000.00, '585545');  -- saldo_inicial - retiro

-- Introduce a 5-second delay
SELECT pg_sleep(5);

-- Jose Lema: Retiro de 575 de la cuenta de ahorro (numero_cuenta=1)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Retiro', -575.00, 1425.00, '478758');  -- saldo_inicial - retiro

-- Marianela Montalvo: Deposito de 600 en la cuenta corriente (numero_cuenta=2)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 600.00, 700.00, '225487');  -- saldo_inicial + deposito

-- Juan Osorio: Deposito de 150 en la cuenta de ahorros (numero_cuenta=3)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Deposito', 150.00, 150.00, '495878');  -- saldo_inicial + deposito

-- Marianela Montalvo: Retiro de 540 de la cuenta de ahorros (numero_cuenta=4)
INSERT INTO Movimientos (fecha, tipo_movimiento, valor, saldo, numero_cuenta) VALUES
(clock_timestamp(), 'Retiro', -540.00, 0.00, '496825');  -- saldo_inicial - retiro
select * from cuenta;










