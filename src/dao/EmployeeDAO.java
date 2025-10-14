package dao;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection conn;
    public EmployeeDAO(Connection conn) { this.conn = conn; }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT e.emp_id,e.name,r.role_name,d.dept_name,e.salary " +
                     "FROM employees e JOIN roles r ON e.role_id=r.role_id " +
                     "JOIN departments d ON e.dept_id=d.dept_id";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setRoleName(rs.getString("role_name"));
                e.setDeptName(rs.getString("dept_name"));
                e.setSalary(rs.getDouble("salary"));
                list.add(e);
            }
        }
        return list;
    }

    public Employee getEmployeeById(int empId) throws SQLException {
        String sql = "SELECT e.emp_id,e.name,e.role_id,e.dept_id,e.salary,r.role_name,d.dept_name " +
                     "FROM employees e JOIN roles r ON e.role_id=r.role_id JOIN departments d ON e.dept_id=d.dept_id " +
                     "WHERE e.emp_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Employee e = new Employee();
                e.setEmpId(rs.getInt("emp_id"));
                e.setName(rs.getString("name"));
                e.setRoleId(rs.getInt("role_id"));
                e.setDeptId(rs.getInt("dept_id"));
                e.setSalary(rs.getDouble("salary"));
                e.setRoleName(rs.getString("role_name"));
                e.setDeptName(rs.getString("dept_name"));
                return e;
            }
        }
        return null;
    }

    public void addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employees(name,role_id,dept_id,salary) VALUES(?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getRoleId());
            ps.setInt(3, emp.getDeptId());
            ps.setDouble(4, emp.getSalary());
            ps.executeUpdate();
        }
    }
/* 
    public void updateEmployee(Employee emp) throws SQLException {
        String sql = "UPDATE employees SET name=?,role_id=?,dept_id=?,salary=? WHERE emp_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setInt(2, emp.getRoleId());
            ps.setInt(3, emp.getDeptId());
            ps.setDouble(4, emp.getSalary());
            ps.setInt(5, emp.getEmpId());
            ps.executeUpdate();
        }
    }
*/
    public void updateEmployee(Employee e) {
    String sql = "UPDATE employees SET name=?, role_id=?, dept_id=?, salary=? WHERE emp_id=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, e.getName());
        ps.setInt(2, e.getRoleId());
        ps.setInt(3, e.getDeptId());
        ps.setDouble(4, e.getSalary());
        ps.setInt(5, e.getEmpId());
        ps.executeUpdate();
    } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void deleteEmployee(int empId) throws SQLException {
        String sql = "DELETE FROM employees WHERE emp_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.executeUpdate();
        }
    }
}
