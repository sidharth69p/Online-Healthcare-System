package dao;
import model.Role;
import model.User;
import util.AppException;
import util.DBConnection;
import java.sql.*;

public class JdbcUserDao implements UserDao {
    @Override
    public User findByEmailAndPassword(String email, String password) throws AppException {
        String sql = "SELECT u.id,u.name,u.email,u.password,r.name role FROM users u JOIN roles r ON u.role_id=r.id WHERE u.email=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dbPass = rs.getString("password");
                    // check plaintext demo or bcrypt prefix
                    boolean ok = password.equals(dbPass) || org.mindrot.jbcrypt.BCrypt.checkpw(password, dbPass);
                    if (!ok) return null;
                    User u = new User();
                    u.setId(rs.getInt("id")); u.setName(rs.getString("name")); u.setEmail(rs.getString("email"));
                    u.setRole(Role.valueOf(rs.getString("role")));
                    return u;
                }
            }
        } catch (SQLException e) { throw new AppException("Error finding user", e); }
        return null;
    }

    @Override
    public User findById(int id) throws AppException {
        String sql = "SELECT u.id,u.name,u.email,r.name role FROM users u JOIN roles r ON u.role_id=r.id WHERE u.id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), Role.valueOf(rs.getString("role")));
                    return u;
                }
            }
        } catch (SQLException e) { throw new AppException("Error finding user by id", e); }
        return null;
    }
}
