package server.server.models;

import java.math.BigInteger;

public class LoginData {
    
    BigInteger id; 
    String email;
    String dateTime; 

    public BigInteger getId() {
        return id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public LoginData() {
    }
    public LoginData(BigInteger id, String email, String dateTime) {
        this.id = id;
        this.email = email;
        this.dateTime = dateTime;
    }
    @Override
    public String toString() {
        return "LoginData [id=" + id + ", email=" + email + ", dateTime=" + dateTime + "]";
    }
}
