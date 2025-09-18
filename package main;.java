package main;

import dao.UsuarioDAO;
import model.Usuario;
import java.util.Scanner;

public class SistemaGestaoProjetos {
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static Usuario usuarioLogado = null;
    
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTÃO DE PROJETOS E EQUIPES ===");
        
        // Tela de login
        if (!fazerLogin()) {
            System.out.println("Falha no login. Encerrando sistema.");
            return;
        }
        
        exibirMenuPrincipal();
    }
    
    private static boolean fazerLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Usuário: ");
        String login = scanner.nextLine();
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        usuarioLogado = usuarioDAO.autenticar(login, senha);
        
        if (usuarioLogado != null) {
            System.out.println("Bem-vindo, " + usuarioLogado.getNomeCompleto() + "!");
            return true;
        } else {
            System.out.println("Usuário ou senha inválidos!");
            return false;
        }
    }
    
    private static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Projetos");
            System.out.println("3. Gerenciar Equipes");
            System.out.println("4. Gerenciar Tarefas");
            System.out.println("5. Relatórios");
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
                    gerenciarUsuarios();
                    break;
                case 2:
                    gerenciarProjetos();
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
    
    private static void gerenciarUsuarios() {
        while (true) {
            System.out.println("\n=== GERENCIAR USUÁRIOS ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Editar Usuário");
            System.out.println("4. Excluir Usuário");
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
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    editarUsuario();
                    break;
                case 4:
                    excluirUsuario();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
    
    private static void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de Usuário ---");
        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();
        
        System.out.print("Login: ");
        String login = scanner.nextLine();
        
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        
        System.out.print("Perfil (administrador/gerente/colaborador): ");
        String perfil = scanner.nextLine();
        
        if (!perfil.equals("administrador") && !perfil.equals("gerente") && !perfil.equals("colaborador")) {
            System.out.println("Perfil inválido! Use: administrador, gerente ou colaborador.");
            return;
        }
        
        Usuario usuario = new Usuario(nome, cpf, email, cargo, login, senha, perfil);
        
        if (usuarioDAO.criarUsuario(usuario)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário. Verifique os dados e tente novamente.");
        }
    }
    
    private static void listarUsuarios() {
        System.out.println("\n--- Lista de Usuários ---");
        for (Usuario usuario : usuarioDAO.listarUsuarios()) {
            System.out.println(usuario);
        }
    }
    
    private static void editarUsuario() {
        System.out.println("\n--- Editar Usuário ---");
        System.out.print("ID do usuário a editar: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        Usuario usuario = usuarioDAO.buscarPorId(id);
        if (usuario == null) {
            System.out.println("Usuário não encontrado!");
            return;
        }
        
        System.out.println("Editando usuário: " + usuario.getNomeCompleto());
        System.out.println("Deixe em branco para manter o valor atual.");
        
        System.out.print("Novo nome completo [" + usuario.getNomeCompleto() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) usuario.setNomeCompleto(nome);
        
        System.out.print("Novo CPF [" + usuario.getCpf() + "]: ");
        String cpf = scanner.nextLine();
        if (!cpf.isEmpty()) usuario.setCpf(cpf);
        
        System.out.print("Novo email [" + usuario.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) usuario.setEmail(email);
        
        System.out.print("Novo cargo [" + usuario.getCargo() + "]: ");
        String cargo = scanner.nextLine();
        if (!cargo.isEmpty()) usuario.setCargo(cargo);
        
        System.out.print("Novo login [" + usuario.getLogin() + "]: ");
        String login = scanner.nextLine();
        if (!login.isEmpty()) usuario.setLogin(login);
        
        System.out.print("Nova senha [******]: ");
        String senha = scanner.nextLine();
        if (!senha.isEmpty()) usuario.setSenha(senha);
        
        System.out.print("Novo perfil [" + usuario.getPerfil() + "]: ");
        String perfil = scanner.nextLine();
        if (!perfil.isEmpty()) usuario.setPerfil(perfil);
        
        if (usuarioDAO.atualizarUsuario(usuario)) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar usuário.");
        }
    }
    
    private static void excluirUsuario() {
        System.out.println("\n--- Excluir Usuário ---");
        System.out.print("ID do usuário a excluir: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }
        
        System.out.print("Tem certeza que deseja excluir este usuário? (s/n): ");
        String confirmacao = scanner.nextLine();
        
        if (confirmacao.equalsIgnoreCase("s")) {
            if (usuarioDAO.deletarUsuario(id)) {
                System.out.println("Usuário excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir usuário.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    
    private static void gerenciarProjetos() {
        System.out.println("\nFuncionalidade de Gerenciamento de Projetos em desenvolvimento...");
        // Implementar similar ao gerenciamento de usuários
    }
    
    private static void gerenciarEquipes() {
        System.out.println("\nFuncionalidade de Gerenciamento de Equipes em desenvolvimento...");
        // Implementar similar ao gerenciamento de usuários
    }
    
    private static void gerenciarTarefas() {
        System.out.println("\nFuncionalidade de Gerenciamento de Tarefas em desenvolvimento...");
        // Implementar similar ao gerenciamento de usuários
    }
    
    private static void exibirRelatorios() {
        System.out.println("\nFuncionalidade de Relatórios em desenvolvimento...");
        // Implementar relatórios
    }
}