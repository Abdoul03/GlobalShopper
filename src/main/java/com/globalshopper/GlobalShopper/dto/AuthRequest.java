package com.globalshopper.GlobalShopper.dto;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Username invalide.") String username,
        @NotBlank(message = "Mot de passe invalide.") String motDePasse
) {
}
