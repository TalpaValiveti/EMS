package ui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginUI extends JFrame {

    private Connection conn;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginUI(Connection conn) {
        this.conn = conn;
        setTitle("EMS Login");
        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        add(btnLogin);
        JButton btnExit = new JButton("Exit");
        add(btnExit);

        btnLogin.addActionListener(e -> login());
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void login() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        UserDAO userDAO = new UserDAO(conn);
        User user = userDAO.validateUser(username,password);

        if(user == null) {
            JOptionPane.showMessageDialog(this,"Invalid credentials!");
            return;
        }

        JOptionPane.showMessageDialog(this,"Login Successful!");

        switch(user.getRoleId()) {
            case 1: new EmployeeManagementUI(conn).setVisible(true); break;
            case 2: new PayrollUI(conn,user).setVisible(true); break;
            case 3: new EmployeeSelfUI(conn,user).setVisible(true); break;
            default: JOptionPane.showMessageDialog(this,"Unknown role");
        }
        this.dispose();
    }
}
