package main;

import dao.ColaboradorDAO;
import dao.EventoDAO;
import model.Colaborador;
import model.Evento;
import service.RelatorioService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SistemaGestaoEventos {
    private static Scanner scanner = new Scanner(System.in);
    private static ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private static EventoDAO eventoDAO = new EventoDAO();
    private static RelatorioService relatorioService = new RelatorioService();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE EVENTOS ===");
        
        if (!testarConexao()) {
            System.out.println("Não foi possível conectar ao banco de dados. Verifique a conexão MySQL.");
            return;
        }
        
        exibirMenuPrincipal();
    }
    
    private static boolean testarConexao() {
        System.out.println("Testando conexão com o banco de dados...");
        return colaboradorDAO.testConnection();
    }
    
    private static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Colaboradores");
            System.out.println("2. Gerenciar Eventos");
            System.out.println("3. Gerenciar Equipes");
            System.out.println("4. Gerenciar Tarefas");
            System.out.println("5. Relatórios e Estatísticas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }
            
            switch (opcao) {
                case 1:
                    gerenciarColaboradores();
                    break;
                case 2:
                    gerenciarEventos();
                    break;
                case 3:
                    gerenciarEquipes();
                    break;
                case 4:
                    gerenciarTarefas();
                    break;
                case 5:
                    exibirRelatorios();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void gerenciarColaboradores() {
        while (true) {
            System.out.println("\n=== GERENCIAR COLABORADORES ===");
            System.out.println("1. Listar Colaboradores");
            System.out.println("2. Cadastrar Colaborador");
            System.out.println("3. Editar Colaborador");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }
            
            switch (opcao) {
                case 1:
                    listarColaboradores();
                    break;
                case 2:
                    cadastrarColaborador();
                    break;
                case 3:
                    editarColaborador();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void listarColaboradores() {
        System.out.println("\n--- Lista de Colaboradores ---");
        List<Colaborador> colaboradores = colaboradorDAO.listarColaboradores();
        
        if (colaboradores.isEmpty()) {
            System.out.println("Nenhum colaborador cadastrado.");
        } else {
            for (Colaborador colab : colaboradores) {
                System.out.println(colab);
            }
        }
    }
    
    private static void cadastrarColaborador() {
        System.out.println("\n--- Cadastro de Colaborador ---");
        
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("Data de contratação (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        
        Date dataContratacao;
        try {
            dataContratacao = dateFormat.parse(dataStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            return;
        }
        
        Colaborador colaborador = new Colaborador(nome, cpf, email, cargo, departamento, telefone, dataContratacao);
        
        if (colaboradorDAO.criarColaborador(colaborador)) {
            System.out.println("Colaborador cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar colaborador.");
        }
    }
    
    private static void editarColaborador() {
        System.out.println("\n--- Editar Colaborador ---");
        System.out.print("ID do colaborador a editar: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Colaborador colaborador = colaboradorDAO.buscarPorId(id);
        if (colaborador == null) {
            System.out.println("Colaborador não encontrado!");
            return;
        }
        
        System.out.println("Editando: " + colaborador.getNomeCompleto());
        System.out.println("Deixe em branco para manter o valor atual.");
        
        System.out.print("Novo nome completo [" + colaborador.getNomeCompleto() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) colaborador.setNomeCompleto(nome);
        
        System.out.print("Novo CPF [" + colaborador.getCpf() + "]: ");
        String cpf = scanner.nextLine();
        if (!cpf.isEmpty()) colaborador.setCpf(cpf);
        
        System.out.print("Novo email [" + colaborador.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) colaborador.setEmail(email);
        
        System.out.print("Novo cargo [" + colaborador.getCargo() + "]: ");
        String cargo = scanner.nextLine();
        if (!cargo.isEmpty()) colaborador.setCargo(cargo);
        
        System.out.print("Novo departamento [" + colaborador.getDepartamento() + "]: ");
        String departamento = scanner.nextLine();
        if (!departamento.isEmpty()) colaborador.setDepartamento(departamento);
        
        System.out.print("Novo telefone [" + colaborador.getTelefone() + "]: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) colaborador.setTelefone(telefone);
        
        System.out.print("Novo status [" + colaborador.getStatus() + "]: ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) colaborador.setStatus(status);
        
        if (colaboradorDAO.atualizarColaborador(colaborador)) {
            System.out.println("Colaborador atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar colaborador.");
        }
    }
    
    private static void gerenciarEventos() {
        while (true) {
            System.out.println("\n=== GERENCIAR EVENTOS ===");
            System.out.println("1. Listar Eventos");
            System.out.println("2. Cadastrar Evento");
            System.out.println("3. Editar Evento");
            System.out.println("4. Visualizar Detalhes do Evento");
            System.out.println("5. Voltar");
            System.out.print("Escolha: ");
            
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }
            
            switch (opcao) {
                case 1:
                    listarEventos();
                    break;
                case 2:
                    cadastrarEvento();
                    break;
                case 3:
                    editarEvento();
                    break;
                case 4:
                    visualizarDetalhesEvento();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void listarEventos() {
        System.out.println("\n--- Lista de Eventos ---");
        List<Evento> eventos = eventoDAO.listarEventos();
        
        if (eventos.isEmpty()) {
            System.out.println("Nenhum evento cadastrado.");
        } else {
            for (Evento evento : eventos) {
                System.out.println(evento);
            }
        }
    }
    
    private static void cadastrarEvento() {
        System.out.println("\n--- Cadastro de Evento ---");
        
        // Listar gerentes disponíveis
        System.out.println("Gerentes disponíveis:");
        List<Colaborador> gerentes = colaboradorDAO.listarPorCargo("Gerente");
        for (Colaborador gerente : gerentes) {
            System.out.println(gerente.getId() + " - " + gerente.getNomeCompleto());
        }
        
        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Tipo de evento: ");
        String tipo = scanner.nextLine();
        
        System.out.print("Data de início (YYYY-MM-DD): ");
        String dataInicioStr = scanner.nextLine();
        
        System.out.print("Data de término (YYYY-MM-DD): ");
        String dataTerminoStr = scanner.nextLine();
        
        System.out.print("Localização: ");
        String localizacao = scanner.nextLine();
        
        System.out.print("Orçamento: ");
        double orcamento;
        try {
            orcamento = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido para orçamento.");
            return;
        }
        
        System.out.print("ID do gerente responsável: ");
        int gerenteId;
        try {
            gerenteId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Date dataInicio, dataTermino;
        try {
            dataInicio = dateFormat.parse(dataInicioStr);
            dataTermino = dateFormat.parse(dataTerminoStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            return;
        }
        
        Evento evento = new Evento(nome, descricao, tipo, dataInicio, dataTermino, localizacao, orcamento, gerenteId);
        
        if (eventoDAO.criarEvento(evento)) {
            System.out.println("Evento cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar evento.");
        }
    }
    
    private static void editarEvento() {
        System.out.println("\n--- Editar Evento ---");
        System.out.print("ID do evento a editar: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Evento evento = eventoDAO.buscarPorId(id);
        if (evento == null) {
            System.out.println("Evento não encontrado!");
            return;
        }
        
        System.out.println("Editando: " + evento.getNome());
        System.out.println("Deixe em branco para manter o valor atual.");
        
        System.out.print("Novo nome [" + evento.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) evento.setNome(nome);
        
        System.out.print("Nova descrição [" + evento.getDescricao() + "]: ");
        String descricao = scanner.nextLine();
        if (!descricao.isEmpty()) evento.setDescricao(descricao);
        
        System.out.print("Novo tipo [" + evento.getTipoEvento() + "]: ");
        String tipo = scanner.nextLine();
        if (!tipo.isEmpty()) evento.setTipoEvento(tipo);
        
        System.out.print("Nova localização [" + evento.getLocalizacao() + "]: ");
        String localizacao = scanner.nextLine();
        if (!localizacao.isEmpty()) evento.setLocalizacao(localizacao);
        
        System.out.print("Novo status [" + evento.getStatus() + "]: ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) evento.setStatus(status);
        
        if (eventoDAO.atualizarEvento(evento)) {
            System.out.println("Evento atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar evento.");
        }
    }
    
    private static void visualizarDetalhesEvento() {
        System.out.println("\n--- Detalhes do Evento ---");
        System.out.print("ID do evento: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Evento evento = eventoDAO.buscarPorIdCompleto(id);
        if (evento == null) {
            System.out.println("Evento não encontrado!");
            return;
        }
        
        System.out.println("\n=== DETALHES DO EVENTO ===");
        System.out.println("ID: " + evento.getId());
        System.out.println("Nome: " + evento.getNome());
        System.out.println("Descrição: " + evento.getDescricao());
        System.out.println("Tipo: " + evento.getTipoEvento());
        System.out.println("Data Início: " + dateFormat.format(evento.getDataInicio()));
        System.out.println("Data Término: " + dateFormat.format(evento.getDataTermino()));
        System.out.println("Localização: " + evento.getLocalizacao());
        System.out.println("Orçamento: R$ " + evento.getOrcamento());
        System.out.println("Status: " + evento.getStatus());
        System.out.println("Gerente: " + evento.getNomeGerente());
        
        // Mostrar estatísticas de tarefas
        Map<String, Integer> statsTarefas = relatorioService.contarTarefasPorStatus(id);
        System.out.println("\n--- Estatísticas de Tarefas ---");
        for (Map.Entry<String, Integer> entry : statsTarefas.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " tarefas");
        }
    }
    
    private static void gerenciarEquipes() {
        System.out.println("\nFuncionalidade de Gerenciamento de Equipes em desenvolvimento...");
        // Implementação similar às anteriores
    }
    
    private static void gerenciarTarefas() {
        System.out.println("\nFuncionalidade de Gerenciamento de Tarefas em desenvolvimento...");
        // Implementação similar às anteriores
    }
    
    private static void exibirRelatorios() {
        while (true) {
            System.out.println("\n=== RELATÓRIOS E ESTATÍSTICAS ===");
            System.out.println("1. Estatísticas de Eventos");
            System.out.println("2. Orçamento Total");
            System.out.println("3. Custos por Evento");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
                continue;
            }
            
            switch (opcao) {
                case 1:
                    exibirEstatisticasEventos();
                    break;
                case 2:
                    exibirOrcamentoTotal();
                    break;
                case 3:
                    exibirCustosPorEvento();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void exibirEstatisticasEventos() {
        System.out.println("\n--- Estatísticas de Eventos ---");
        Map<String, Integer> stats = relatorioService.contarEventosPorStatus();
        
        System.out.println("Quantidade de eventos por status:");
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " eventos");
        }
        
        int total = stats.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total de eventos: " + total);
    }
    
    private static void exibirOrcamentoTotal() {
        System.out.println("\n--- Orçamento Total ---");
        double total = relatorioService.calcularOrcamentoTotal();
        System.out.println("Orçamento total de todos os eventos: R$ " + total);
    }
    
    private static void exibirCustosPorEvento() {
        System.out.println("\n--- Custos por Evento ---");
        Map<String, Double> custos = relatorioService.calcularCustosPorEvento();
        
        System.out.println("Custos estimados por evento:");
        for (Map.Entry<String, Double> entry : custos.entrySet()) {
            System.out.println(entry.getKey() + ": R$ " + entry.getValue());
        }
    }
}