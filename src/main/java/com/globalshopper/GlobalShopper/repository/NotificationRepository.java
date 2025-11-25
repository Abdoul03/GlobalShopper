package com.globalshopper.GlobalShopper.repository;

import com.globalshopper.GlobalShopper.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
