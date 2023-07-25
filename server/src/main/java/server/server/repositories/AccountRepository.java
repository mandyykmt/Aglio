package server.server.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import server.server.models.LoginData;
import server.server.models.User;
import static server.server.repositories.Queries.*;

@Repository
public class AccountRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 

    public User getUserByEmail(String email) {
        
        try {
            User user = jdbcTemplate.queryForObject(SQL_GET_USER_BY_EMAIL,
                                                    new AccountRowMapper(),
                                                    new Object[] {email});

            System.out.println(">> RepoGetUserByEmail: " + user);

            return user; 

        } catch (Exception e) {

            System.out.println(">> RepoRegistrationError: " + e);

            return null; 
        }
    }

    public User insertRegistration(final User user) {

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                SQL_INSERT_USER);
                ps.setString(1, user.getUsername()); 
                ps.setString(2, user.getPassword());                
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getPhone());
                ps.setString(7, user.getCountry());
                ps.setString(8, user.getPostal());
                return ps;
        }); 

        System.out.println(">> RepoRegistration: " + user);

        return user; 
    }

    public LoginData login(String email, String password) {

        new LocalDateTime();
        String dateTime = LocalDateTime.now().toString();

        KeyHolder keyHolder = new GeneratedKeyHolder(); 

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(
                SQL_POST_LOGIN_ACTIVITY, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email); 
                ps.setString(2, password);
                ps.setString(3, dateTime);                
                return ps;
        }, keyHolder); 

        BigInteger primaryKey = (BigInteger) keyHolder.getKey();

        LoginData loginActivity = new LoginData(primaryKey, email, dateTime); 

        System.out.println(">> RepoPostLoginActivity: " + loginActivity);

        return loginActivity; 
    }
}
