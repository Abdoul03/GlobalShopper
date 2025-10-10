package com.globalshopper.GlobalShopper.config;

import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.entity.enums.Role;
import com.globalshopper.GlobalShopper.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Value("${admin_username}")
    String userName;
    @Value("${Admin_email}")
    String email;
    @Value("${admin_password}")
    String password;

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Transactional
    public CommandLineRunner initAdmin() {
        return args -> {
            // Verifier si un admin exist déjà
            if (adminRepository.findByUsername(userName).isEmpty()) {
                Admin admin = new Admin();
                admin.setNom("Super");
                admin.setPrenom("Admin");
                admin.setUsername(userName);
                admin.setEmail(email);
                admin.setMotDePasse(passwordEncoder.encode(password));
                admin.setActif(true);
                admin.setRole(Role.ADMIN);

                adminRepository.save(admin);
                System.out.println("Admin par defaut créé ");
            } else {
                System.out.println(" Admin déjà existant, aucune action.");
            }
        };
    }
}
