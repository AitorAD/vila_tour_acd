package com.example.vila_tour.payload.response;

import lombok.Data;

@Data
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String refreshToken;
  private Long id;
  private String username;
  private String email;
  private String role;

  public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, String role) {
    this.token = accessToken;
    this.refreshToken = refreshToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.role = role;
  }
}