package ui;

import dao.EmployeeDAO;
import dao.PayrollDAO;
import model.Employee;
import model.Payroll;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class EmployeeSelfUI extends JFrame {
    private Connection conn;
    private User user;
    private JTable table;
    private DefaultTableModel model;
    private JTextArea empDetails;

    public EmployeeSelfUI(Connection conn, User user) {
        this.conn = conn;
        this.user = user;

        setTitle("Employee Dashboard");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        empDetails = new JTextArea();
        empDetails.setEditable(false);
        add(empDetails, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Payroll ID","Base Salary","Bonus","Deductions","Date"},0);
        table = new JTable(model);
        add(new JScrollPane(table),BorderLayout.CENTER);

        loadEmployeeDetails();
        loadEmployeePayroll();
    }

    private void loadEmployeeDetails() {
        try {
            EmployeeDAO dao = new EmployeeDAO(conn);
            Employee emp = dao.getEmployeeById(user.getUserId());
            if(emp != null) {
                empDetails.setText("Name: " + emp.getName() + "\n" +
                                   "Role: " + emp.getRoleName() + "\n" +
                                   "Department: " + emp.getDeptName() + "\n" +
                                   "Salary: " + emp.getSalary());
            }
        } catch(Exception e) { e.printStackTrace(); }
    }

    private void loadEmployeePayroll() {
        try {
            model.setRowCount(0);
            PayrollDAO dao = new PayrollDAO(conn);
            List<Payroll> list = dao.getPayrollByEmployee(user.getUserId());
            for(Payroll p : list) {
                model.addRow(new Object[]{p.getPayrollId(),p.getBaseSalary(),p.getBonus(),p.getDeductions(),p.getPayDate()});
            }
        } catch(Exception e) { e.printStackTrace(); }
    }
}
