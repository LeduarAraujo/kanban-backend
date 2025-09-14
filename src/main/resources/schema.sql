CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE secretaria (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nmSecretaria VARCHAR(100) NOT NULL,
    descricao VARCHAR(100)
);

CREATE TABLE cargo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE responsavel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    secretaria_id BIGINT NOT NULL,
    cargo_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (secretaria_id) REFERENCES secretaria(id),
    FOREIGN KEY (cargo_id) REFERENCES cargo(id)
);

CREATE TABLE projeto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    status ENUM('A_INICIAR', 'EM_ANDAMENTO', 'ATRASADO', 'CONCLUIDO') NOT NULL,
    inicio_previsto DATE DEFAULT CURRENT_DATE,
    termino_previsto DATE DEFAULT CURRENT_DATE,
    inicio_realizado DATE DEFAULT CURRENT_DATE,
    termino_realizado DATE DEFAULT CURRENT_DATE,
    dias_atraso INT DEFAULT 0,
    percentual_tempo_restante INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE projeto_responsavel (
    projeto_id BIGINT NOT NULL,
    responsavel_id BIGINT NOT NULL,
    FOREIGN KEY (projeto_id) REFERENCES projeto(id),
    FOREIGN KEY (responsavel_id) REFERENCES responsavel(id),
    PRIMARY KEY (projeto_id, responsavel_id)
);

CREATE TABLE status_item_projeto  (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE item_projeto (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    projeto_id BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    responsavel_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    prioridade ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (projeto_id) REFERENCES projeto(id),
    FOREIGN KEY (status_id) REFERENCES status_item_projeto(id),
    FOREIGN KEY (responsavel_id) REFERENCES responsavel(id)
);

CREATE TABLE historico_item_projeto (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    projeto_id BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    responsavel_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    prioridade ENUM('BAIXA', 'MEDIA', 'ALTA') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (projeto_id) REFERENCES projeto(id),
    FOREIGN KEY (status_id) REFERENCES status_item_projeto(id),
    FOREIGN KEY (responsavel_id) REFERENCES responsavel(id)
);
