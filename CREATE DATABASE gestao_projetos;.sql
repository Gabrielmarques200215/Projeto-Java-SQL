CREATE DATABASE gestao_projetos;
USE gestao_projetos;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('administrador', 'gerente', 'colaborador') NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projetos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_inicio DATE NOT NULL,
    data_termino_prevista DATE NOT NULL,
    status ENUM('planejado', 'em andamento', 'concluído', 'cancelado') DEFAULT 'planejado',
    gerente_id INT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gerente_id) REFERENCES usuarios(id)
);

CREATE TABLE equipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE equipe_membros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipe_id INT NOT NULL,
    usuario_id INT NOT NULL,
    data_vinculo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    UNIQUE KEY unique_membro (equipe_id, usuario_id)
);

CREATE TABLE projeto_equipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    projeto_id INT NOT NULL,
    equipe_id INT NOT NULL,
    data_alocacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (projeto_id) REFERENCES projetos(id),
    FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    UNIQUE KEY unique_alocacao (projeto_id, equipe_id)
);

CREATE TABLE tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    projeto_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    status ENUM('pendente', 'em andamento', 'concluída', 'cancelada') DEFAULT 'pendente',
    data_inicio_prevista DATE,
    data_termino_prevista DATE,
    data_conclusao DATE,
    responsavel_id INT,
    prioridade ENUM('baixa', 'media', 'alta') DEFAULT 'media',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (projeto_id) REFERENCES projetos(id),
    FOREIGN KEY (responsavel_id) REFERENCES usuarios(id)
);