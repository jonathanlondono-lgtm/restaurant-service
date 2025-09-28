-- ============================
-- CREACIÓN DE TABLAS: PLAZOLETA
-- ============================

-- Tabla: restaurantes
CREATE TABLE restaurantes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nit VARCHAR(50) UNIQUE NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    url_logo VARCHAR(500),
    fecha_creacion TIMESTAMP DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP DEFAULT NOW()
);

-- Tabla: platos
CREATE TABLE platos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10,2) NOT NULL CHECK (precio > 0),
    descripcion TEXT,
    url_imagen VARCHAR(500),
    categoria VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    restaurante_id BIGINT NOT NULL,
    CONSTRAINT fk_platos_restaurante FOREIGN KEY (restaurante_id)
        REFERENCES restaurantes(id) ON DELETE CASCADE
);

-- Tabla: pedidos
CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL, -- FK hacia microservicio usuarios
    restaurante_id BIGINT NOT NULL,
    estado VARCHAR(20) NOT NULL CHECK (estado IN ('PENDIENTE','EN_PREPARACION','LISTO','ENTREGADO','CANCELADO')) DEFAULT 'PENDIENTE',
    empleado_asignado_id BIGINT NULL, -- FK hacia microservicio usuarios
    fecha_creacion TIMESTAMP DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP DEFAULT NOW(),
    pin_seguridad VARCHAR(10),
    CONSTRAINT fk_pedidos_restaurante FOREIGN KEY (restaurante_id)
        REFERENCES restaurantes(id) ON DELETE CASCADE
);

-- Tabla: pedido_detalles
CREATE TABLE pedido_detalles (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    plato_id BIGINT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    CONSTRAINT fk_detalles_pedido FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id) ON DELETE CASCADE,
    CONSTRAINT fk_detalles_plato FOREIGN KEY (plato_id)
        REFERENCES platos(id) ON DELETE CASCADE
);

-- Tabla intermedia: restaurante_owner
CREATE TABLE restaurante_owner (
    id BIGSERIAL PRIMARY KEY,
    restaurante_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL, -- FK hacia microservicio usuarios
    rol VARCHAR(50) DEFAULT 'PROPIETARIO',
    fecha_asignacion TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_owner_restaurante FOREIGN KEY (restaurante_id)
        REFERENCES restaurantes(id) ON DELETE CASCADE
);

-- ============================
-- ÍNDICES PARA OPTIMIZAR QUERIES
-- ============================
CREATE INDEX idx_restaurantes_nombre ON restaurantes(nombre);
CREATE INDEX idx_platos_restaurante ON platos(restaurante_id);
CREATE INDEX idx_pedidos_cliente ON pedidos(cliente_id);
CREATE INDEX idx_pedidos_restaurante ON pedidos(restaurante_id);
CREATE INDEX idx_pedido_detalles_pedido ON pedido_detalles(pedido_id);
CREATE INDEX idx_restaurante_owner_restaurante ON restaurante_owner(restaurante_id);
