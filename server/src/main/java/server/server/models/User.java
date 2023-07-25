package server.server.models;

public class User {
    
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email; 
    private String phone;
    private String country;
    private String postal;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPostal() {
        return postal;
    }
    public void setPostal(String postal) {
        this.postal = postal;
    }
    public User() {
    }
    public User(String email) {
        this.email = email;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public User(String username, String password, String firstName, String lastName, String email, String phone,
            String country, String postal) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.postal = postal;
    }
    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + ", firstName=" + firstName
                + ", lastName=" + lastName + ", email=" + email + ", phone=" + phone + ", country=" + country
                + ", postal=" + postal + "]";
    }
}
