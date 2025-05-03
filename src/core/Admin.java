package core;

public final class Admin extends User {

    public Admin(String username, String email, String passwordHash) {
        super(username, email, passwordHash);
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }

}
