package server.server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import server.server.models.User;

public class AccountRowMapper implements RowMapper<User> {
    
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        
        return user;
    }
}
