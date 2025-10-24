package com.globalshopper.GlobalShopper.controller;

import com.globalshopper.GlobalShopper.auth.AuthentificationService;
import com.globalshopper.GlobalShopper.dto.AuthRequest;
import com.globalshopper.GlobalShopper.dto.request.CommercantRequestDTO;
import com.globalshopper.GlobalShopper.dto.request.FournisseurRequestDTO;
import com.globalshopper.GlobalShopper.dto.response.CommercantResponseDTO;
import com.globalshopper.GlobalShopper.dto.response.FournisseurResponseDTO;
import com.globalshopper.GlobalShopper.service.CommercantService;
import com.globalshopper.GlobalShopper.service.FournisseurService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    private AuthentificationService authentificationService;
    private CommercantService commercantService;
    private FournisseurService fournisseurService;

    public AuthController(AuthentificationService authentificationService, CommercantService commercantService, FournisseurService fournisseurService) {
        this.authentificationService = authentificationService;
        this.commercantService = commercantService;
        this.fournisseurService = fournisseurService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthentificationService.TokenPairResponse> login(
            @Valid @RequestBody AuthRequest authenticationRequest
    ) {
        return ResponseEntity.ok(
                authentificationService.authenticate(authenticationRequest)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> deconnexion(
            @RequestBody Map<String, String> request
    ) {
        String refreshToken = request.get("refreshToken");
        String result = authentificationService.deleteRefreshToken(refreshToken);
        return ResponseEntity.ok(result);
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

    @PostMapping("/fournisseur/register")
    public ResponseEntity<FournisseurResponseDTO> incription (@RequestBody FournisseurRequestDTO fournisseur){
        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurService.CreatFournisseur(fournisseur));
    }
}
