package com.globalshopper.GlobalShopper.config;

import com.globalshopper.GlobalShopper.entity.Admin;
import com.globalshopper.GlobalShopper.entity.enums.Role;
import com.globalshopper.GlobalShopper.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Value("${admin_username}")
    String userName;
    @Value("${admin_email}")
    String email;
    @Value("${admin_password}")
    String password;

    @Autowired
    private  AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
                admin.setRole(Role.ROLE_ADMIN);

                adminRepository.save(admin);
                System.out.println("Admin par defaut créé ");
            } else {
                System.out.println(" Admin déjà existant, aucune action.");
            }
        };
    }
}
