package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.auth.AuthentificationService;
import com.globalshopper.GlobalShopper.dto.AuthRequest;
import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.service.CommercantService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private CommercantService commercantService;

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

    @PostMapping("/commercant/register")
    public ResponseEntity<CommercantResponseDTO> inscription (@RequestBody CommercantRequestDTO commercant){
        return ResponseEntity.status(HttpStatus.CREATED).body(commercantService.inscription(commercant));
    }
}
