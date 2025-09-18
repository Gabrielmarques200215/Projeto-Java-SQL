package model;

import java.util.Date;

public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private String tipoEvento;
    private Date dataInicio;
    private Date dataTermino;
    private String localizacao;
    private double orcamento;
    private String status;
    private int gerenteId;
    private Date dataCriacao;
    private String nomeGerente; // Para exibição
    
    public Evento() {}
    
    public Evento(String nome, String descricao, String tipoEvento, Date dataInicio, 
                 Date dataTermino, String localizacao, double orcamento, int gerenteId) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoEvento = tipoEvento;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.localizacao = localizacao;
        this.orcamento = orcamento;
        this.gerenteId = gerenteId;
        this.status = "planejado";
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    
    public Date getDataTermino() { return dataTermino; }
    public void setDataTermino(Date dataTermino) { this.dataTermino = dataTermino; }
    
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    
    public double getOrcamento() { return orcamento; }
    public void setOrcamento(double orcamento) { this.orcamento = orcamento; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getGerenteId() { return gerenteId; }
    public void setGerenteId(int gerenteId) { this.gerenteId = gerenteId; }
    
    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public String getNomeGerente() { return nomeGerente; }
    public void setNomeGerente(String nomeGerente) { this.nomeGerente = nomeGerente; }
    
    @Override
    public String toString() {
        return "Evento [id=" + id + ", nome=" + nome + ", tipo=" + tipoEvento + 
               ", dataInicio=" + dataInicio + ", status=" + status + "]";
    }
}