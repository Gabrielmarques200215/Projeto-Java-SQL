package dao;

import model.Evento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    
    public boolean criarEvento(Evento evento) {
        String sql = "INSERT INTO eventos (nome, descricao, tipo_evento, data_inicio, data_termino, " +
                    "localizacao, orcamento, status, gerente_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getTipoEvento());
            stmt.setDate(4, new java.sql.Date(evento.getDataInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(evento.getDataTermino().getTime()));
            stmt.setString(6, evento.getLocalizacao());
            stmt.setDouble(7, evento.getOrcamento());
            stmt.setString(8, evento.getStatus());
            stmt.setInt(9, evento.getGerenteId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar evento: " + e.getMessage());
            return false;
        }
    }
    
    public List<Evento> listarEventos() {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, c.nome_completo as nome_gerente FROM eventos e " +
                    "LEFT JOIN colaboradores c ON e.gerente_id = c.id ORDER BY e.data_inicio";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setTipoEvento(rs.getString("tipo_evento"));
                evento.setDataInicio(rs.getDate("data_inicio"));
                evento.setDataTermino(rs.getDate("data_termino"));
                evento.setLocalizacao(rs.getString("localizacao"));
                evento.setOrcamento(rs.getDouble("orcamento"));
                evento.setStatus(rs.getString("status"));
                evento.setGerenteId(rs.getInt("gerente_id"));
                evento.setDataCriacao(rs.getTimestamp("data_criacao"));
                evento.setNomeGerente(rs.getString("nome_gerente"));
                
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar eventos: " + e.getMessage());
        }
        return eventos;
    }
    
    public Evento buscarPorId(int id) {
        String sql = "SELECT e.*, c.nome_completo as nome_gerente FROM eventos e " +
                    "LEFT JOIN colaboradores c ON e.gerente_id = c.id WHERE e.id = ?";
        Evento evento = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setTipoEvento(rs.getString("tipo_evento"));
                evento.setDataInicio(rs.getDate("data_inicio"));
                evento.setDataTermino(rs.getDate("data_termino"));
                evento.setLocalizacao(rs.getString("localizacao"));
                evento.setOrcamento(rs.getDouble("orcamento"));
                evento.setStatus(rs.getString("status"));
                evento.setGerenteId(rs.getInt("gerente_id"));
                evento.setDataCriacao(rs.getTimestamp("data_criacao"));
                evento.setNomeGerente(rs.getString("nome_gerente"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar evento: " + e.getMessage());
        }
        return evento;
    }
    
    public Evento buscarPorIdCompleto(int id) {
        // Informações Adicionais
        String sql = "SELECT e.*, c.nome_completo as nome_gerente, " +
                    "(SELECT COUNT(*) FROM tarefas t WHERE t.evento_id = e.id) as total_tarefas, " +
                    "(SELECT COUNT(*) FROM tarefas t WHERE t.evento_id = e.id AND t.status = 'concluida') as tarefas_concluidas " +
                    "FROM eventos e LEFT JOIN colaboradores c ON e.gerente_id = c.id WHERE e.id = ?";
        
        Evento evento = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setTipoEvento(rs.getString("tipo_evento"));
                evento.setDataInicio(rs.getDate("data_inicio"));
                evento.setDataTermino(rs.getDate("data_termino"));
                evento.setLocalizacao(rs.getString("localizacao"));
                evento.setOrcamento(rs.getDouble("orcamento"));
                evento.setStatus(rs.getString("status"));
                evento.setGerenteId(rs.getInt("gerente_id"));
                evento.setDataCriacao(rs.getTimestamp("data_criacao"));
                evento.setNomeGerente(rs.getString("nome_gerente"));
                
                // Informações adicionais para relatórios
                int totalTarefas = rs.getInt("total_tarefas");
                int tarefasConcluidas = rs.getInt("tarefas_concluidas");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar evento completo: " + e.getMessage());
        }
        return evento;
    }
    
    public List<Evento> listarPorStatus(String status) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, c.nome_completo as nome_gerente FROM eventos e " +
                    "LEFT JOIN colaboradores c ON e.gerente_id = c.id WHERE e.status = ? ORDER BY e.data_inicio";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setTipoEvento(rs.getString("tipo_evento"));
                evento.setDataInicio(rs.getDate("data_inicio"));
                evento.setDataTermino(rs.getDate("data_termino"));
                evento.setLocalizacao(rs.getString("localizacao"));
                evento.setOrcamento(rs.getDouble("orcamento"));
                evento.setStatus(rs.getString("status"));
                evento.setGerenteId(rs.getInt("gerente_id"));
                evento.setDataCriacao(rs.getTimestamp("data_criacao"));
                evento.setNomeGerente(rs.getString("nome_gerente"));
                
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar eventos por status: " + e.getMessage());
        }
        return eventos;
    }
    
    public boolean atualizarEvento(Evento evento) {
        String sql = "UPDATE eventos SET nome = ?, descricao = ?, tipo_evento = ?, data_inicio = ?, " +
                    "data_termino = ?, localizacao = ?, orcamento = ?, status = ?, gerente_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getTipoEvento());
            stmt.setDate(4, new java.sql.Date(evento.getDataInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(evento.getDataTermino().getTime()));
            stmt.setString(6, evento.getLocalizacao());
            stmt.setDouble(7, evento.getOrcamento());
            stmt.setString(8, evento.getStatus());
            stmt.setInt(9, evento.getGerenteId());
            stmt.setInt(10, evento.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar evento: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletarEvento(int id) {
        String sql = "DELETE FROM eventos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar evento: " + e.getMessage());
            return false;
        }
    }
    
    public List<Evento> listarProximosEventos(int dias) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.*, c.nome_completo as nome_gerente FROM eventos e " +
                    "LEFT JOIN colaboradores c ON e.gerente_id = c.id " +
                    "WHERE e.data_inicio BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL ? DAY) " +
                    "ORDER BY e.data_inicio";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, dias);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNome(rs.getString("nome"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setTipoEvento(rs.getString("tipo_evento"));
                evento.setDataInicio(rs.getDate("data_inicio"));
                evento.setDataTermino(rs.getDate("data_termino"));
                evento.setLocalizacao(rs.getString("localizacao"));
                evento.setOrcamento(rs.getDouble("orcamento"));
                evento.setStatus(rs.getString("status"));
                evento.setGerenteId(rs.getInt("gerente_id"));
                evento.setDataCriacao(rs.getTimestamp("data_criacao"));
                evento.setNomeGerente(rs.getString("nome_gerente"));
                
                eventos.add(evento);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar próximos eventos: " + e.getMessage());
        }
        return eventos;
    }
}