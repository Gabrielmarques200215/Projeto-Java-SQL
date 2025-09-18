package dao;

import model.Colaborador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {
    
    public boolean testConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.out.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }
    
    public boolean criarColaborador(Colaborador colaborador) {
        String sql = "INSERT INTO colaboradores (nome_completo, cpf, email, cargo, departamento, telefone, data_contratacao, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, colaborador.getNomeCompleto());
            stmt.setString(2, colaborador.getCpf());
            stmt.setString(3, colaborador.getEmail());
            stmt.setString(4, colaborador.getCargo());
            stmt.setString(5, colaborador.getDepartamento());
            stmt.setString(6, colaborador.getTelefone());
            stmt.setDate(7, new java.sql.Date(colaborador.getDataContratacao().getTime()));
            stmt.setString(8, colaborador.getStatus());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao criar colaborador: " + e.getMessage());
            return false;
        }
    }
    
    public List<Colaborador> listarColaboradores() {
        List<Colaborador> colaboradores = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores ORDER BY nome_completo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Colaborador colaborador = new Colaborador();
                colaborador.setId(rs.getInt("id"));
                colaborador.setNomeCompleto(rs.getString("nome_completo"));
                colaborador.setCpf(rs.getString("cpf"));
                colaborador.setEmail(rs.getString("email"));
                colaborador.setCargo(rs.getString("cargo"));
                colaborador.setDepartamento(rs.getString("departamento"));
                colaborador.setTelefone(rs.getString("telefone"));
                colaborador.setDataContratacao(rs.getDate("data_contratacao"));
                colaborador.setStatus(rs.getString("status"));
                colaborador.setDataCriacao(rs.getTimestamp("data_criacao"));
                
                colaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar colaboradores: " + e.getMessage());
        }
        return colaboradores;
    }
    
    public Colaborador buscarPorId(int id) {
        String sql = "SELECT * FROM colaboradores WHERE id = ?";
        Colaborador colaborador = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                colaborador = new Colaborador();
                colaborador.setId(rs.getInt("id"));
                colaborador.setNomeCompleto(rs.getString("nome_completo"));
                colaborador.setCpf(rs.getString("cpf"));
                colaborador.setEmail(rs.getString("email"));
                colaborador.setCargo(rs.getString("cargo"));
                colaborador.setDepartamento(rs.getString("departamento"));
                colaborador.setTelefone(rs.getString("telefone"));
                colaborador.setDataContratacao(rs.getDate("data_contratacao"));
                colaborador.setStatus(rs.getString("status"));
                colaborador.setDataCriacao(rs.getTimestamp("data_criacao"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar colaborador: " + e.getMessage());
        }
        return colaborador;
    }
    
    public List<Colaborador> listarPorCargo(String cargo) {
        List<Colaborador> colaboradores = new ArrayList<>();
        String sql = "SELECT * FROM colaboradores WHERE cargo LIKE ? ORDER BY nome_completo";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + cargo + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Colaborador colaborador = new Colaborador();
                colaborador.setId(rs.getInt("id"));
                colaborador.setNomeCompleto(rs.getString("nome_completo"));
                colaborador.setCpf(rs.getString("cpf"));
                colaborador.setEmail(rs.getString("email"));
                colaborador.setCargo(rs.getString("cargo"));
                colaborador.setDepartamento(rs.getString("departamento"));
                colaborador.setTelefone(rs.getString("telefone"));
                colaborador.setDataContratacao(rs.getDate("data_contratacao"));
                colaborador.setStatus(rs.getString("status"));
                colaborador.setDataCriacao(rs.getTimestamp("data_criacao"));
                
                colaboradores.add(colaborador);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar colaboradores por cargo: " + e.getMessage());
        }
        return colaboradores;
    }
    
    public boolean atualizarColaborador(Colaborador colaborador) {
        String sql = "UPDATE colaboradores SET nome_completo = ?, cpf = ?, email = ?, cargo = ?, " +
                    "departamento = ?, telefone = ?, data_contratacao = ?, status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, colaborador.getNomeCompleto());
            stmt.setString(2, colaborador.getCpf());
            stmt.setString(3, colaborador.getEmail());
            stmt.setString(4, colaborador.getCargo());
            stmt.setString(5, colaborador.getDepartamento());
            stmt.setString(6, colaborador.getTelefone());
            stmt.setDate(7, new java.sql.Date(colaborador.getDataContratacao().getTime()));
            stmt.setString(8, colaborador.getStatus());
            stmt.setInt(9, colaborador.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar colaborador: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deletarColaborador(int id) {
        String sql = "DELETE FROM colaboradores WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.out.println("Erro ao deletar colaborador: " + e.getMessage());
            return false;
        }
    }
    
    public Colaborador autenticar(String email, String senha) {
        // Este método é um exemplo - você precisaria adicionar campo de senha na tabela
        String sql = "SELECT * FROM colaboradores WHERE email = ?";
        Colaborador colaborador = null;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                colaborador = new Colaborador();
                colaborador.setId(rs.getInt("id"));
                colaborador.setNomeCompleto(rs.getString("nome_completo"));
                colaborador.setCpf(rs.getString("cpf"));
                colaborador.setEmail(rs.getString("email"));
                colaborador.setCargo(rs.getString("cargo"));
                colaborador.setDepartamento(rs.getString("departamento"));
                colaborador.setTelefone(rs.getString("telefone"));
                colaborador.setDataContratacao(rs.getDate("data_contratacao"));
                colaborador.setStatus(rs.getString("status"));
                colaborador.setDataCriacao(rs.getTimestamp("data_criacao"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro na autenticação: " + e.getMessage());
        }
        return colaborador;
    }
}