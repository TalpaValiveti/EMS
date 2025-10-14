package dao;

import model.AuditLog;
import java.sql.*;

public class AuditDAO {
    private Connection conn;

    public AuditDAO(Connection conn) {
        this.conn = conn;
    }

    public void logAction(String action, String username) throws SQLException {
        String sql = "INSERT INTO audit_log(action, username) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, action);
            ps.setString(2, username);
            ps.executeUpdate();
        }
    }
}
