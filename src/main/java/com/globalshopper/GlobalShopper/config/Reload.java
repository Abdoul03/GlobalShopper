package com.globalshopper.GlobalShopper.config;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Reload {
    @Transactional
    @Scheduled(fixedRate = 840000) // Exécution toutes les 14 minutes (14 * 60 * 1000 ms)
    public void rafraichir(){
        System.out.println("Tâche exécutée toutes les 14 minutes via fixedRate.");
    }
}
