package core;

public abstract class User {

    private static int nextId = 0;
    private final int id;
    private final String username;
    private final String email;
    private final String passwordHash;

    public User(String username, String email, String passwordHash) {
        this.id = nextId++;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Common methods for all users
    public int getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();
}
