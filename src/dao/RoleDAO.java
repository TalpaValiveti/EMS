package dao;

import model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private Connection conn;

    public RoleDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                list.add(role);
            }
        }
        return list;
    }
}
