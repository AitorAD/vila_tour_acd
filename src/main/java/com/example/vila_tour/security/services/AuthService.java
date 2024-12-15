package com.example.vila_tour.security.services;

import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.UserNotFoundException;
import com.example.vila_tour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public boolean canAccessUser(Authentication authentication, Long userId) {
        // Obtener el nombre de usuario autenticado
        String authenticatedUsername = authentication.getName();

        // Comprobar si el usuario tiene rol ADMIN
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        // Recuperar el usuario solicitado
        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + userId));

        // Permitir acceso si es ADMIN o si el usuario solicitado coincide con el autenticado
        return isAdmin || user.getUsername().equals(authenticatedUsername);
    }
}