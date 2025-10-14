package ui;

import dao.EmployeeDAO;
import dao.UserDAO;
import model.Employee;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private Connection conn;
    private JTable empTable, userTable;
    private DefaultTableModel empModel, userModel;

    public EmployeeManagementUI(Connection conn) {
        this.conn = conn;

        setTitle("Admin Dashboard - Manage Employees & HR Users");
        setSize(900,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // ================= Employee Tab =================
        JPanel empPanel = new JPanel(new BorderLayout());
        empModel = new DefaultTableModel(new String[]{"ID","Name","Role","Department","Salary"},0);
        empTable = new JTable(empModel);
        empPanel.add(new JScrollPane(empTable), BorderLayout.CENTER);

        JPanel empBtnPanel = new JPanel();
        JButton btnEmpRefresh = new JButton("Refresh");
        JButton btnAddEmp = new JButton("Add Employee");
        JButton btnDelEmp = new JButton("Delete Employee");
        JButton btnEditEmp = new JButton("Edit Employee");
        empBtnPanel.add(btnEmpRefresh); empBtnPanel.add(btnAddEmp);
        empBtnPanel.add(btnDelEmp); empBtnPanel.add(btnEditEmp);
        empPanel.add(empBtnPanel, BorderLayout.SOUTH);

        btnEmpRefresh.addActionListener(e -> loadEmployees());
        btnAddEmp.addActionListener(e -> addEmployee());
        btnDelEmp.addActionListener(e -> deleteEmployee());
        btnEditEmp.addActionListener(e -> editEmployee());

        tabs.add("Employees", empPanel);

        // ================= HR Users Tab =================
        JPanel userPanel = new JPanel(new BorderLayout());
        userModel = new DefaultTableModel(new String[]{"User ID","Username","Role ID"},0);
        userTable = new JTable(userModel);
        userPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel userBtnPanel = new JPanel();
        JButton btnUserRefresh = new JButton("Refresh");
        JButton btnAddUser = new JButton("Add HR User");
        JButton btnDelUser = new JButton("Delete User");
        JButton btnEditUser = new JButton("Edit HR User");
        userBtnPanel.add(btnUserRefresh); userBtnPanel.add(btnAddUser);
        userBtnPanel.add(btnDelUser); userBtnPanel.add(btnEditUser);
        userPanel.add(userBtnPanel, BorderLayout.SOUTH);

        btnUserRefresh.addActionListener(e -> loadUsers());
        btnAddUser.addActionListener(e -> addHRUser());
        btnDelUser.addActionListener(e -> deleteUser());
        btnEditUser.addActionListener(e -> editHRUser());

        tabs.add("HR Users", userPanel);

        add(tabs);

        loadEmployees();
        loadUsers();
    }

    // ================= Employee Methods =================
    private void loadEmployees() {
        try {
            empModel.setRowCount(0);
            EmployeeDAO dao = new EmployeeDAO(conn);
            List<Employee> list = dao.getAllEmployees();
            for(Employee e : list)
                empModel.addRow(new Object[]{e.getEmpId(), e.getName(), e.getRoleName(), e.getDeptName(), e.getSalary()});
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void addEmployee() {
        try {
            String name = JOptionPane.showInputDialog(this,"Enter Name:");
            int roleId = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter Role ID:"));
            int deptId = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter Dept ID:"));
            double salary = Double.parseDouble(JOptionPane.showInputDialog(this,"Enter Salary:"));

            Employee e = new Employee();
            e.setName(name); e.setRoleId(roleId); e.setDeptId(deptId); e.setSalary(salary);

            new EmployeeDAO(conn).addEmployee(e);
            loadEmployees();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void deleteEmployee() {
        try {
            int row = empTable.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select an employee"); return; }
            int empId = (int) empTable.getValueAt(row,0);
            new EmployeeDAO(conn).deleteEmployee(empId);
            loadEmployees();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void editEmployee() {
        try {
            int row = empTable.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select an employee"); return; }
            int empId = (int) empTable.getValueAt(row,0);

            String name = JOptionPane.showInputDialog(this,"Enter Name:", empTable.getValueAt(row,1));
            int roleId = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter Role ID:", empTable.getValueAt(row,2)));
            int deptId = Integer.parseInt(JOptionPane.showInputDialog(this,"Enter Dept ID:", empTable.getValueAt(row,3)));
            double salary = Double.parseDouble(JOptionPane.showInputDialog(this,"Enter Salary:", empTable.getValueAt(row,4)));

            Employee e = new Employee();
            e.setEmpId(empId); e.setName(name); e.setRoleId(roleId); e.setDeptId(deptId); e.setSalary(salary);

            new EmployeeDAO(conn).updateEmployee(e);
            loadEmployees();
        } catch(Exception ex){ ex.printStackTrace(); }
    }

    // ================= HR User Methods =================
    private void loadUsers() {
        try {
            userModel.setRowCount(0);
            UserDAO dao = new UserDAO(conn);
            List<User> list = dao.getAllUsers();
            for(User u : list)
                userModel.addRow(new Object[]{u.getUserId(), u.getUsername(), u.getRoleId()});
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void addHRUser() {
        try {
            String username = JOptionPane.showInputDialog(this,"Enter HR Username:");
            String password = JOptionPane.showInputDialog(this,"Enter HR Password:");
            if(username!=null && password!=null){
                new UserDAO(conn).addHRUser(username,password);
                loadUsers();
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void deleteUser() {
        try {
            int row = userTable.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select a user"); return; }
            int userId = (int) userTable.getValueAt(row,0);
            new UserDAO(conn).deleteUser(userId);
            loadUsers();
        } catch(Exception e){ e.printStackTrace(); }
    }

    private void editHRUser() {
        try {
            int row = userTable.getSelectedRow();
            if(row==-1){ JOptionPane.showMessageDialog(this,"Select a user"); return; }
            int userId = (int) userTable.getValueAt(row,0);

            String username = JOptionPane.showInputDialog(this,"Enter Username:", userTable.getValueAt(row,1));
            String password = JOptionPane.showInputDialog(this,"Enter Password:", "");
            new UserDAO(conn).updateUser(userId, username, password);
            loadUsers();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
