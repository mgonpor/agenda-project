package com.miguel.auth;

import com.miguel.persistence.entities.user.Role;
import com.miguel.persistence.entities.user.User;

public class AuthenticationMapper {

    public static User registerToUser(RegisterRequest registerRequest) {
        User user = new User();

        user.setNombre(registerRequest.getNombre());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole(Role.USER);

        return user;
    }
}
