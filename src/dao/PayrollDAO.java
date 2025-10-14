package dao;

import model.Payroll;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    private Connection conn;
    public PayrollDAO(Connection conn) { this.conn = conn; }

    public List<Payroll> getAllPayrolls() throws SQLException {
        List<Payroll> list = new ArrayList<>();
        String sql = "SELECT * FROM payroll";
        try(Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                Payroll p = new Payroll(rs.getInt("payroll_id"), rs.getInt("emp_id"),
                        rs.getDouble("base_salary"), rs.getDouble("bonus"),
                        rs.getDouble("deductions"), rs.getDate("pay_date"));
                list.add(p);
            }
        }
        return list;
    }

    public List<Payroll> getPayrollByEmployee(int empId) throws SQLException {
        List<Payroll> list = new ArrayList<>();
        String sql = "SELECT * FROM payroll WHERE emp_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Payroll p = new Payroll(rs.getInt("payroll_id"), rs.getInt("emp_id"),
                        rs.getDouble("base_salary"), rs.getDouble("bonus"),
                        rs.getDouble("deductions"), rs.getDate("pay_date"));
                list.add(p);
            }
        }
        return list;
    }

    public void addPayroll(Payroll p) throws SQLException {
        String sql = "INSERT INTO payroll(emp_id,base_salary,bonus,deductions,pay_date) VALUES(?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getEmpId());
            ps.setDouble(2, p.getBaseSalary());
            ps.setDouble(3, p.getBonus());
            ps.setDouble(4, p.getDeductions());
            ps.setDate(5, p.getPayDate());
            ps.executeUpdate();
        }
    }
/* 
    public void updatePayroll(Payroll p) throws SQLException {
        String sql = "UPDATE payroll SET base_salary=?,bonus=?,deductions=?,pay_date=? WHERE payroll_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1,p.getBaseSalary());
            ps.setDouble(2,p.getBonus());
            ps.setDouble(3,p.getDeductions());
            ps.setDate(4,p.getPayDate());
            ps.setInt(5,p.getPayrollId());
            ps.executeUpdate();
        }
    }
*/
    public void updatePayroll(Payroll p) {
    String sql = "UPDATE payroll SET base_salary=?, bonus=?, deductions=?, pay_date=? WHERE payroll_id=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setDouble(1, p.getBaseSalary());
        ps.setDouble(2, p.getBonus());
        ps.setDouble(3, p.getDeductions());
        ps.setDate(4, p.getPayDate());
        ps.setInt(5, p.getPayrollId());
        ps.executeUpdate();
    } catch(SQLException e){ e.printStackTrace(); }
    }

    public void deletePayroll(int payrollId) throws SQLException {
        String sql = "DELETE FROM payroll WHERE payroll_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,payrollId);
            ps.executeUpdate();
        }
    }
}
