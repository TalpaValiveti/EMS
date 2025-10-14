package dao;

import model.Department;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private Connection conn;

    public DepartmentDAO(Connection conn) {
        this.conn = conn;
    }

    public void addDepartment(Department dept) throws SQLException {
        String sql = "INSERT INTO departments(dept_name) VALUES(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dept.getDeptName());
            ps.executeUpdate();
        }
    }

    public List<Department> getAllDepartments() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Department dept = new Department(
                    rs.getInt("dept_id"),
                    rs.getString("dept_name")
                );
                list.add(dept);
            }
        }
        return list;
    }
}
