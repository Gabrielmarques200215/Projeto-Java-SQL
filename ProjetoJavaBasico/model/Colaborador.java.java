package model;

import java.util.Date;

public class Colaborador {
    private int id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String departamento;
    private String telefone;
    private Date dataContratacao;
    private String status;
    private Date dataCriacao;
    
    public Colaborador() {}
    
    public Colaborador(String nomeCompleto, String cpf, String email, String cargo, 
                      String departamento, String telefone, Date dataContratacao) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.departamento = departamento;
        this.telefone = telefone;
        this.dataContratacao = dataContratacao;
        this.status = "ativo";
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public Date getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(Date dataContratacao) { this.dataContratacao = dataContratacao; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
    
    @Override
    public String toString() {
        return "Colaborador [id=" + id + ", nome=" + nomeCompleto + ", cargo=" + cargo + 
               ", departamento=" + departamento + ", status=" + status + "]";
    }
}