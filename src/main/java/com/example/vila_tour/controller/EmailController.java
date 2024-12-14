package com.example.vila_tour.controller;
import com.example.vila_tour.service.EmailService;
import com.example.vila_tour.service.UserService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService; // Si necesitas actualizar la contraseña en la base de datos.

    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PermitAll
    @PostMapping("/recoverymail/email")
    public ResponseEntity<String> sendTestEmail(@RequestParam("email") String email) {
        try {
            // Validación del email
            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: El email es requerido.");
            }

            // Generar nueva contraseña aleatoria
            String newPassword = emailService.generateRandomPassword();

            // Actualizar la contraseña en la base de datos
            boolean isUpdated = userService.updatePasswordByEmail(email, newPassword);
            if (!isUpdated) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: El email no está registrado.");
            }
            // Enviar correo electrónico
            emailService.sendEmail(
                    email,
                    "Recuperación de Contraseña",
                    "Hola,\n\nEste es un correo enviado desde VilaTour porque has solicitado recuperar tu contraseña. "
                            + "\n\nTu nueva contraseña es: " + newPassword + "\n\nGracias por usar VilaTour."
            );
            // Responder al cliente
            return ResponseEntity.ok("Correo enviado exitosamente.");
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar el correo: " + e.getMessage());
        }
    }
}
