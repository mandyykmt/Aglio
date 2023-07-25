package server.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import server.server.models.LoginData;
import server.server.models.User;
import server.server.repositories.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepo;

    public User getUserByEmail(final String email) {
        return this.accountRepo.getUserByEmail(email); 
    } 

    public User insertRegistration(final User user) {
        return this.accountRepo.insertRegistration(user);
    }

    public LoginData login(final User user) {
        return this.accountRepo.login(user.getEmail(), user.getPassword()); 
    }
    
}
