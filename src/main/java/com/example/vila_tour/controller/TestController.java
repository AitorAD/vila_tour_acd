package com.example.vila_tour.controller;
import com.example.vila_tour.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-mail")
    public String sendTestEmail() {
        emailService.sendEmail("llinares.omar@gmail.com", "Asunto de prueba", "Este es un correo enviado desde Spring Mail usando Gmail.");
        return "Correo enviado exitosamente.";
    }
}