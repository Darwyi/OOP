package org.Darwyi.login_register_system;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UserIdentifier identifier;
    private String password;
    private LocalDateTime lastLoginDate;
    private transient boolean isLoggedIn;

    public User(UserIdentifier identifier, String password) {
        this.identifier = identifier;
        this.password = password;
        this.lastLoginDate = null;
        this.isLoggedIn = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(identifier, user.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String lastLogin = (lastLoginDate != null) ? lastLoginDate.format(fmt) : "ніколи";
        return String.format("User{id=%d, name='%s', loggedIn=%b, lastLogin=%s}",
                identifier.getId(), identifier.getName(), isLoggedIn, lastLogin);
    }

    public UserIdentifier getIdentifier() { return identifier; }
    public int getId() { return identifier.getId(); }
    public String getName() { return identifier.getName(); }
    public String getPassword() { return password; }
    public LocalDateTime getLastLoginDate() { return lastLoginDate; }
    public boolean isLoggedIn() { return isLoggedIn; }
    public void setLastLoginDate(LocalDateTime lastLoginDate) { this.lastLoginDate = lastLoginDate; }
    public void setLoggedIn(boolean loggedIn) { isLoggedIn = loggedIn; }
}