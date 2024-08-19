package com.example.userssrvice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtResponse
{
    private String token;
    private String type = "Bearer";

    public JwtResponse(String token) {
    }
}
