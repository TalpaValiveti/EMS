import dao.DBConnection;
import ui.LoginUI;
import javax.swing.*;
import java.sql.Connection;

public class EMSMain {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try{
                Connection conn = DBConnection.getConnection();
                new LoginUI(conn).setVisible(true);
            }catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Database connection failed!");
            }
        });
    }
}
