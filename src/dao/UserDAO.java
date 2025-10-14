package dao;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;
    public UserDAO(Connection conn) { this.conn = conn; }

    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setRoleId(rs.getInt("role_id"));
                return user;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT u.user_id,u.username,u.role_id,r.role_name FROM users u JOIN roles r ON u.role_id=r.role_id";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setRoleId(rs.getInt("role_id"));
                list.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void addHRUser(String username, String password) {
        String sql = "INSERT INTO users(username,password,role_id) VALUES(?,?,2)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void updateUser(int userId, String username, String password) {
        String sql = "UPDATE users SET username=?, password=? WHERE user_id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setInt(3, userId);
            ps.executeUpdate();
        } catch(SQLException e){ e.printStackTrace(); }
    }

}
