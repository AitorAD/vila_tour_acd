package com.example.vila_tour.controller;

import com.example.vila_tour.domain.LoginRequest;
import com.example.vila_tour.domain.RefreshToken;
import com.example.vila_tour.domain.Role;
import com.example.vila_tour.domain.User;
import com.example.vila_tour.exception.TokenRefreshException;
import com.example.vila_tour.payload.request.RegisterRequest;
import com.example.vila_tour.payload.request.TokenRefreshRequest;
import com.example.vila_tour.payload.response.JwtResponse;
import com.example.vila_tour.payload.response.MessageResponse;
import com.example.vila_tour.payload.response.TokenRefreshResponse;
import com.example.vila_tour.repository.UserRepository;
import com.example.vila_tour.security.jwt.JwtUtils;
import com.example.vila_tour.security.services.RefreshTokenService;
import com.example.vila_tour.security.services.UserDetailsImpl;
import com.example.vila_tour.service.EmailService;
import com.example.vila_tour.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);
        int jwtExpiration = jwtUtils.getJwtExpirationMs();

        String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0);

        return ResponseEntity.ok(new JwtResponse(jwt, jwtExpiration, userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Role role = Role.USER;

        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        System.out.println(requestRefreshToken);

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PermitAll
    @PostMapping("/recoverymail/email")
    public ResponseEntity<String> sendTestEmail(@RequestParam("email") String email) {
        try {
            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: El email es requerido.");
            }

            String newPassword = emailService.generateRandomPassword();
            Optional<User> user = userService.findByEmail(email);

            if (user.isPresent()) {
                userService.updatePasswordByEmail(email, newPassword);
                emailService.sendEmail(
                        email,
                        "Recuperación de Contraseña",
                        "Hola,\n\nEste es un correo enviado desde VilaTour porque has solicitado recuperar tu contraseña. "
                                + "\n\nTu nueva contraseña es: " + newPassword + "\n\nGracias por usar VilaTour."                );

                return ResponseEntity.ok("Correo enviado exitosamente.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: El usuario no existe.");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al recuperar la contraseña: " + ex.getMessage());
        }
    }

    @PostMapping("/singout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        // refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
