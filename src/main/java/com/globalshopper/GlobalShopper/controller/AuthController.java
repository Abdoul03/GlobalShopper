package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.auth.AuthentificationService;
import com.globalshopper.GlobalShopper.dto.AuthRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private AuthentificationService authentificationService;

    @PostMapping("/login")
    public ResponseEntity<AuthentificationService.TokenPairResponse> login(
            @Valid @RequestBody AuthRequest authenticationRequest
    ) {
        return ResponseEntity.ok(
                authentificationService.authenticate(authenticationRequest)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthentificationService.TokenPairResponse> refreshToken(
            @RequestBody String refreshToken
    ) {
        return ResponseEntity.ok(authentificationService.refresh(refreshToken));
    }
}
