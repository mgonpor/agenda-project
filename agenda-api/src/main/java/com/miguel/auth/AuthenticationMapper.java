package com.miguel.auth;

import com.miguel.security.user.Role;
import com.miguel.security.user.User;

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
