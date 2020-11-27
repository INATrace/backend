package com.abelium.INATrace.db.migrations;

import javax.persistence.EntityManager;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.abelium.INATrace.components.flyway.JpaMigration;
import com.abelium.INATrace.db.entities.User;
import com.abelium.INATrace.types.UserRole;
import com.abelium.INATrace.types.UserStatus;

public class V2020_03_21_18_30__Prefill_Users implements JpaMigration {
    
    public void migrate(EntityManager em, Environment environment) throws Exception {        
        if ("master".equals(environment.getProperty("INATrace.service.group"))) {
            return;
        }
        
        User admin = new User();
        admin.setEmail("");
        admin.setName("");
        admin.setSurname("");
        admin.setPassword(new BCryptPasswordEncoder().encode(""));
        admin.setStatus(UserStatus.ACTIVE);
        admin.setRole(UserRole.ADMIN);
        em.persist(admin);
    }
}
