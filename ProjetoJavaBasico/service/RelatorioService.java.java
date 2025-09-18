package service;

import dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RelatorioService {
    
    public Map<String, Integer> contarEventosPorStatus() {
        Map<String, Integer> resultado = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as quantidade FROM eventos GROUP BY status";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                resultado.put(rs.getString("status"), rs.getInt("quantidade"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao contar eventos por status: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public Map<String, Integer> contarTarefasPorStatus(int eventoId) {
        Map<String, Integer> resultado = new HashMap<>();
        String sql = "SELECT status, COUNT(*) as quantidade FROM tarefas WHERE evento_id = ? GROUP BY status";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                resultado.put(rs.getString("status"), rs.getInt("quantidade"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao contar tarefas por status: " + e.getMessage());
        }
        
        return resultado;
    }
    
    public double calcularOrcamentoTotal() {
        double total = 0;
        String sql = "SELECT SUM(orcamento) as total FROM eventos WHERE status != 'cancelado'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao calcular orçamento total: " + e.getMessage());
        }
        
        return total;
    }
    
    public Map<String, Double> calcularCustosPorEvento() {
        Map<String, Double> resultado = new HashMap<>();
        String sql = "SELECT e.nome, SUM(r.quantidade * r.custo_unitario) as total " +
                    "FROM eventos e LEFT JOIN recursos r ON e.id = r.evento_id " +
                    "GROUP BY e.id, e.nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                resultado.put(rs.getString("nome"), rs.getDouble("total"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao calcular custos por evento: " + e.getMessage());
        }
        
        return resultado;
    }
}