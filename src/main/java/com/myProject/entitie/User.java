package com.myProject.entitie;

import com.myProject.dto.UserDto;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable, Comparable<User> {
    private long id;
    private String login;
    private String password;
    private String email;
    private Role role;

    public User() {  }
    public User(long id, String login, String password, String email, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
    public static class UserBuilder {
        private long id;
        private String login;
        private String password;
        private String email;

        private Role role;

        public UserBuilder() {
        }

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }
        public User build() {
            return new User(id, login, password, email, role);
        }

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login) && Objects.equals(password, user.password) && email.equals(user.email) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role);
    }

    @Override
    public int compareTo(User o) {
        return this.login.compareToIgnoreCase(o.getLogin());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
