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
import java.sql.Date;
import java.util.List;

public class PayrollUI extends JFrame {
    private Connection conn;
    private User user;
    private JTable table;
    private DefaultTableModel model;

    public PayrollUI(Connection conn, User user) {
        this.conn = conn;
        this.user = user;

        setTitle("HR Dashboard - Employee & Payroll");
        setSize(800,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new String[]{"Emp ID","Name","Salary","Bonus","Deductions","Date"},0);
        table = new JTable(model);
        add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnRefresh = new JButton("Refresh");
        JButton btnAddPayroll = new JButton("Add Payroll");
        JButton btnDeletePayroll = new JButton("Delete Payroll");
        JButton btnEditPayroll = new JButton("Edit Payroll");

        btnPanel.add(btnRefresh); btnPanel.add(btnAddPayroll);
        btnPanel.add(btnDeletePayroll); btnPanel.add(btnEditPayroll);
        add(btnPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadPayrolls());
        btnAddPayroll.addActionListener(e -> addPayroll());
        btnDeletePayroll.addActionListener(e -> deletePayroll());
        btnEditPayroll.addActionListener(e -> editPayroll());

        loadPayrolls();
    }

    private void loadPayrolls() {
        try {
            model.setRowCount(0);
            PayrollDAO dao = new PayrollDAO(conn);
            List<Payroll> list = dao.getAllPayrolls();
            EmployeeDAO empDao = new EmployeeDAO(conn);
            for(Payroll p : list){
                Employee e = empDao.getEmployeeById(p.getEmpId());
                model.addRow(new Object[]{e.getEmpId(), e.getName(), p.getBaseSalary(), p.getBonus(), p.getDeductions(), p.getPayDate()});
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void addPayroll() {
        try {
            int empId = Integer.parseInt(JOptionPane.showInputDialog(this,"Employee ID:"));
            double salary = Double.parseDouble(JOptionPane.showInputDialog(this,"Base Salary:"));
            double bonus = Double.parseDouble(JOptionPane.showInputDialog(this,"Bonus:"));
            double deductions = Double.parseDouble(JOptionPane.showInputDialog(this,"Deductions:"));
            Date date = new Date(System.currentTimeMillis());

            Payroll p = new Payroll();
            p.setEmpId(empId); p.setBaseSalary(salary); p.setBonus(bonus); p.setDeductions(deductions); p.setPayDate(date);

            new PayrollDAO(conn).addPayroll(p);
            loadPayrolls();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void deletePayroll() {
        try {
            int row = table.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select a row"); return; }
            int empId = (int) table.getValueAt(row,0);
            List<Payroll> payrolls = new PayrollDAO(conn).getPayrollByEmployee(empId);
            if(payrolls.isEmpty()){ JOptionPane.showMessageDialog(this,"No payroll found"); return; }
            int payrollId = payrolls.get(0).getPayrollId();
            new PayrollDAO(conn).deletePayroll(payrollId);
            loadPayrolls();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void editPayroll() {
        try {
            int row = table.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select a payroll"); return; }

            int empId = (int) table.getValueAt(row,0);
            List<Payroll> list = new PayrollDAO(conn).getPayrollByEmployee(empId);
            if(list.isEmpty()){ JOptionPane.showMessageDialog(this,"No payroll found"); return; }

            Payroll p = list.get(0);
            double baseSalary = Double.parseDouble(JOptionPane.showInputDialog(this,"Base Salary:", p.getBaseSalary()));
            double bonus = Double.parseDouble(JOptionPane.showInputDialog(this,"Bonus:", p.getBonus()));
            double deductions = Double.parseDouble(JOptionPane.showInputDialog(this,"Deductions:", p.getDeductions()));
            p.setBaseSalary(baseSalary); p.setBonus(bonus); p.setDeductions(deductions);

            new PayrollDAO(conn).updatePayroll(p);
            loadPayrolls();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
