-- Banco de dados para Gestão de Eventos
CREATE DATABASE gestao_eventos;
USE gestao_eventos;

-- Tabela de Colaboradores
CREATE TABLE colaboradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    departamento VARCHAR(50),
    telefone VARCHAR(20),
    data_contratacao DATE,
    status ENUM('ativo', 'inativo') DEFAULT 'ativo',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Eventos
CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    tipo_evento VARCHAR(50),
    data_inicio DATE NOT NULL,
    data_termino DATE NOT NULL,
    localizacao VARCHAR(200),
    orcamento DECIMAL(15,2),
    status ENUM('planejado', 'em_andamento', 'concluido', 'cancelado') DEFAULT 'planejado',
    gerente_id INT NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (gerente_id) REFERENCES colaboradores(id)
);

-- Tabela de Equipes
CREATE TABLE equipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    especialidade VARCHAR(100),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Membros das Equipes
CREATE TABLE equipe_membros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    equipe_id INT NOT NULL,
    colaborador_id INT NOT NULL,
    funcao VARCHAR(50),
    data_vinculo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    FOREIGN KEY (colaborador_id) REFERENCES colaboradores(id),
    UNIQUE KEY unique_membro (equipe_id, colaborador_id)
);

-- Tabela de Alocação de Equipes em Eventos
CREATE TABLE evento_equipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento_id INT NOT NULL,
    equipe_id INT NOT NULL,
    responsabilidade TEXT,
    data_alocacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    FOREIGN KEY (equipe_id) REFERENCES equipes(id),
    UNIQUE KEY unique_alocacao (evento_id, equipe_id)
);

-- Tabela de Tarefas
CREATE TABLE tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento_id INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    status ENUM('pendente', 'em_andamento', 'concluida', 'cancelada') DEFAULT 'pendente',
    data_inicio_prevista DATE,
    data_termino_prevista DATE,
    data_conclusao DATE,
    responsavel_id INT,
    prioridade ENUM('baixa', 'media', 'alta') DEFAULT 'media',
    categoria VARCHAR(50),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    FOREIGN KEY (responsavel_id) REFERENCES colaboradores(id)
);

-- Tabela de Fornecedores
CREATE TABLE fornecedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_empresa VARCHAR(100) NOT NULL,
    contato_principal VARCHAR(100),
    email VARCHAR(100),
    telefone VARCHAR(20),
    servico_prestado VARCHAR(100),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Recursos
CREATE TABLE recursos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    evento_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    tipo ENUM('equipamento', 'material', 'servico') NOT NULL,
    quantidade INT,
    custo_unitario DECIMAL(10,2),
    fornecedor_id INT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
);

-- Inserir dados fictícios
INSERT INTO colaboradores (nome_completo, cpf, email, cargo, departamento, telefone, data_contratacao) VALUES
('Carlos Silva', '123.456.789-00', 'carlos.silva@empresa.com', 'Gerente de Projetos', 'Gestão', '(11) 99999-1111', '2020-01-15'),
('Ana Santos', '987.654.321-00', 'ana.santos@empresa.com', 'Coordenadora de Eventos', 'Produção', '(11) 99999-2222', '2020-03-20'),
('Pedro Costa', '111.222.333-44', 'pedro.costa@empresa.com', 'Analista de Marketing', 'Marketing', '(11) 99999-3333', '2021-05-10'),
('Mariana Oliveira', '555.666.777-88', 'mariana.oliveira@empresa.com', 'Designer', 'Criativo', '(11) 99999-4444', '2021-07-15'),
('Ricardo Almeida', '999.888.777-66', 'ricardo.almeida@empresa.com', 'Produtor', 'Produção', '(11) 99999-5555', '2019-11-30');

INSERT INTO eventos (nome, descricao, tipo_evento, data_inicio, data_termino, localizacao, orcamento, status, gerente_id) VALUES
('Conferência de Tecnologia 2024', 'Maior evento de tecnologia da região com palestras e workshops', 'Conferência', '2024-05-15', '2024-05-17', 'Centro de Convenções São Paulo', 150000.00, 'planejado', 1),
('Workshop de Inovação', 'Workshop prático sobre metodologias ágeis e inovação', 'Workshop', '2024-04-10', '2024-04-11', 'Hotel Business Paulista', 35000.00, 'em_andamento', 2),
('Feira de Carreiras', 'Evento de recrutamento com diversas empresas participantes', 'Feira', '2024-06-20', '2024-06-21', 'Expo Center Norte', 80000.00, 'planejado', 1);

INSERT INTO equipes (nome, descricao, especialidade) VALUES
('Equipe de Logística', 'Responsável por toda a logística dos eventos', 'Logística e Operações'),
('Equipe de Marketing', 'Cuida da divulgação e comunicação dos eventos', 'Marketing Digital'),
('Equipe de Produção', 'Responsável pela execução técnica dos eventos', 'Produção de Eventos');

INSERT INTO equipe_membros (equipe_id, colaborador_id, funcao) VALUES
(1, 3, 'Coordenador de Logística'),
(1, 5, 'Assistente de Operações'),
(2, 3, 'Gerente de Mídias'),
(2, 4, 'Designer de Materiais'),
(3, 2, 'Coordenadora de Produção'),
(3, 5, 'Produtor Executivo');

INSERT INTO evento_equipes (evento_id, equipe_id, responsabilidade) VALUES
(1, 1, 'Logística completa do evento'),
(1, 2, 'Divulgação e marketing do evento'),
(1, 3, 'Produção e execução do evento'),
(2, 2, 'Marketing digital e redes sociais'),
(2, 3, 'Produção do workshop'),
(3, 1, 'Logística da feira'),
(3, 2, 'Atração de participantes e empresas');

INSERT INTO tarefas (evento_id, titulo, descricao, status, data_inicio_prevista, data_termino_prevista, responsavel_id, prioridade, categoria) VALUES
(1, 'Contratação de palestrantes', 'Selecionar e contratar os palestrantes principais', 'em_andamento', '2024-01-15', '2024-02-28', 1, 'alta', 'Palestras'),
(1, 'Reserva do local', 'Negociar e reservar o centro de convenções', 'concluida', '2024-01-10', '2024-01-31', 2, 'alta', 'Local'),
(1, 'Divulgação inicial', 'Campanha inicial de divulgação nas redes sociais', 'pendente', '2024-02-01', '2024-03-15', 3, 'media', 'Marketing'),
(2, 'Preparação de material', 'Preparar material didático para os participantes', 'em_andamento', '2024-03-01', '2024-03-20', 4, 'media', 'Produção'),
(3, 'Captação de patrocínios', 'Buscar empresas patrocinadoras para o evento', 'pendente', '2024-02-15', '2024-04-30', 1, 'alta', 'Financeiro');

INSERT INTO fornecedores (nome_empresa, contato_principal, email, telefone, servico_prestado) VALUES
('TechSound Ltda', 'João Mendes', 'joao@techsound.com', '(11) 3333-4444', 'Sonorização e Iluminação'),
('Catering Premium', 'Maria Santos', 'maria@cateringpremium.com', '(11) 3333-5555', 'Alimentação e Coffee Break'),
('PrintExpress', 'Carlos Lima', 'carlos@printexpress.com', '(11) 3333-6666', 'Materiais Gráficos');

INSERT INTO recursos (evento_id, nome, tipo, quantidade, custo_unitario, fornecedor_id) VALUES
(1, 'Sistema de som profissional', 'equipamento', 2, 5000.00, 1),
(1, 'Coffee break executivo', 'servico', 200, 85.00, 2),
(1, 'Banners promocionais', 'material', 20, 250.00, 3),
(2, 'Projetor multimídia', 'equipamento', 3, 800.00, 1),
(2, 'Material de escritório', 'material', 1, 1200.00, NULL);