package com.mahipal.airline.dao;

import com.mahipal.airline.model.User;
import com.mahipal.airline.util.DBConnection;

import java.sql.*;

public class UserDAO {

    public boolean create(User user) throws SQLException {
        String sql = "INSERT INTO users(full_name,email,password_hash,role) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, "USER");
            return ps.executeUpdate() == 1;
        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id,full_name,email,password_hash,role FROM users WHERE email=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    private User map(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("full_name"),
            rs.getString("email"),
            rs.getString("password_hash"),
            rs.getString("role")
        );
    }
}
