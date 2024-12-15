package com.example.vila_tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class VilaTourApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "admin"; // La contraseña que deseas codificar
		String encodedPassword = passwordEncoder.encode(rawPassword);

		System.out.println("Contraseña codificada: " + encodedPassword);
		SpringApplication.run(VilaTourApplication.class, args);
	}

}
