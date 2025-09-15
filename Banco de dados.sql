-- Tabela usuario
CREATE TABLE usuario (
    cpf VARCHAR(11) PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cargo VARCHAR(50),
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('ADMINISTRADOR', 'GERENTE', 'COLABORADOR') NOT NULL
);

-- Tabela projeto
CREATE TABLE projeto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_inicio DATE NOT NULL,
    data_termino_previsto DATE NOT NULL,
    status ENUM('PLANEJADO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO') NOT NULL,
    gerente_responsavel_cpf VARCHAR(11) NOT NULL,
    CONSTRAINT fk_gerente FOREIGN KEY (gerente_responsavel_cpf) REFERENCES usuario(cpf)
);

-- Tabela equipe
CREATE TABLE equipe (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao TEXT
);

-- Tabela associativa equipe_usuario (membros da equipe)
CREATE TABLE equipe_usuario (
    equipe_id BIGINT NOT NULL,
    usuario_cpf VARCHAR(11) NOT NULL,
    PRIMARY KEY (equipe_id, usuario_cpf),
    CONSTRAINT fk_equipe FOREIGN KEY (equipe_id) REFERENCES equipe(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_cpf) REFERENCES usuario(cpf) ON DELETE CASCADE
);

-- Tabela associativa equipe_projeto (projetos da equipe)
CREATE TABLE equipe_projeto (
    equipe_id BIGINT NOT NULL,
    projeto_id BIGINT NOT NULL,
    PRIMARY KEY (equipe_id, projeto_id),
    CONSTRAINT fk_equipe_proj FOREIGN KEY (equipe_id) REFERENCES equipe(id) ON DELETE CASCADE,
    CONSTRAINT fk_projeto FOREIGN KEY (projeto_id) REFERENCES projeto(id) ON DELETE CASCADE
);