package com.myProject.dto;

import com.myProject.service.exception.AppException;

public class UserDto implements Validator<UserDto> {

    private long id;
    private String strId;
    private String login;
    private String password;
    private String email;
    private String role;
            /*
                long id = 0L;
        if (strId != null && !strId.equals("")) {
            id = Long.parseLong(strId);
        }
         */

    public UserDto(String strId, String login, String password, String email, String role) {
        this.strId = strId;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    @Override
    public UserDto isValid() throws AppException {
        if (strId != null && !"".equals(strId)) {
            try {
                id = Long.parseLong(strId);
            } catch (NumberFormatException e) {
                throw new AppException("Incorrect 'id'");
            }
        } else {
            id = 0L;
        }
        if ("".equals(login)) {
            throw new AppException("Incorrect 'login'");
        }
        if ("".equals(password)) {
            throw new AppException("Incorrect 'password'");
        }
        if ("".equals(email)) {
            throw new AppException("Incorrect 'role'");
        }
        if ("".equals(role)) {
            throw new AppException("Incorrect 'role'");
        }
        return this;
    }

    public static class UserDtoBuilder {
        private String strId;
        private String login;
        private String password;
        private String email;
        private String role;

        public UserDtoBuilder() {
        }

        public UserDtoBuilder strId(String strId) {
            this.strId = strId;
            return this;
        }

        public UserDtoBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDtoBuilder role(String role) {
            this.role = role;
            return this;
        }

        public UserDto build() {
            return new UserDto(strId, login, password, email, role);
        }
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
