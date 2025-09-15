public enum Perfil {
    ADMINISTRADOR,
    GERENTE,
    COLABORADOR
}

public enum StatusProjeto {
    PLANEJADO,
    EM_ANDAMENTO,
    CONCLUIDO,
    CANCELADO
}

import java.util.Objects;

public class Usuario {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private Perfil perfil;

    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha, Perfil perfil) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Getters e setters

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return cpf.equals(usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", perfil=" + perfil +
                '}';
    }
}

import java.time.LocalDate;
import java.util.Objects;

public class Projeto {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevisto;
    private StatusProjeto status;
    private Usuario gerenteResponsavel;

    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevisto, StatusProjeto status, Usuario gerenteResponsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevisto = dataTerminoPrevisto;
        this.status = status;
        this.gerenteResponsavel = gerenteResponsavel;
    }

    // Getters e setters

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataTerminoPrevisto() { return dataTerminoPrevisto; }
    public void setDataTerminoPrevisto(LocalDate dataTerminoPrevisto) { this.dataTerminoPrevisto = dataTerminoPrevisto; }

    public StatusProjeto getStatus() { return status; }
    public void setStatus(StatusProjeto status) { this.status = status; }

    public Usuario getGerenteResponsavel() { return gerenteResponsavel; }
    public void setGerenteResponsavel(Usuario gerenteResponsavel) { this.gerenteResponsavel = gerenteResponsavel; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Projeto)) return false;
        Projeto projeto = (Projeto) o;
        return nome.equals(projeto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataInicio=" + dataInicio +
                ", dataTerminoPrevisto=" + dataTerminoPrevisto +
                ", status=" + status +
                ", gerenteResponsavel=" + gerenteResponsavel.getNomeCompleto() +
                '}';
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Equipe {
    private String nome;
    private String descricao;
    private List<Usuario> membros = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();

    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e setters

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Usuario> getMembros() { return membros; }
    public void setMembros(List<Usuario> membros) { this.membros = membros; }

    public List<Projeto> getProjetos() { return projetos; }
    public void setProjetos(List<Projeto> projetos) { this.projetos = projetos; }

    // Métodos para adicionar membros e projetos

    public void adicionarMembro(Usuario usuario) {
        if (!membros.contains(usuario)) {
            membros.add(usuario);
        }
    }

    public void adicionarProjeto(Projeto projeto) {
        if (!projetos.contains(projeto)) {
            projetos.add(projeto);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipe)) return false;
        Equipe equipe = (Equipe) o;
        return nome.equals(equipe.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", membros=" + membros.size() +
                ", projetos=" + projetos.size() +
                '}';
    }
}

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Criar usuários
        Usuario gerente = new Usuario("Ana Silva", "12345678901", "ana@email.com", "Gerente de Projetos", "ana", "senha123", Perfil.GERENTE);
        Usuario colaborador1 = new Usuario("João Souza", "10987654321", "joao@email.com", "Desenvolvedor", "joao", "senha456", Perfil.COLABORADOR);
        Usuario colaborador2 = new Usuario("Maria Lima", "19283746500", "maria@email.com", "Analista", "maria", "senha789", Perfil.COLABORADOR);

        // Criar projeto
        Projeto projeto = new Projeto(
                "Sistema de Gestão",
                "Desenvolvimento de sistema para gestão interna",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 12, 31),
                StatusProjeto.PLANEJADO,
                gerente
        );

        // Criar equipe
        Equipe equipe = new Equipe("Equipe Alpha", "Equipe responsável pelo sistema de gestão");
        equipe.adicionarMembro(gerente);
        equipe.adicionarMembro(colaborador1);
        equipe.adicionarMembro(colaborador2);

        equipe.adicionarProjeto(projeto);

        // Exibir informações
        System.out.println("Usuários da equipe:");
        for (Usuario u : equipe.getMembros()) {
            System.out.println("- " + u.getNomeCompleto() + " (" + u.getPerfil() + ")");
        }

        System.out.println("\nProjetos da equipe:");
        for (Projeto p : equipe.getProjetos()) {
            System.out.println("- " + p.getNome() + " (Gerente: " + p.getGerenteResponsavel().getNomeCompleto() + ")");
        }
    }
}