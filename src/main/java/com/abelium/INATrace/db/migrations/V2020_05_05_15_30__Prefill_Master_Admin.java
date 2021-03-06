package com.abelium.INATrace.db.migrations;

import javax.persistence.EntityManager;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.abelium.INATrace.components.flyway.JpaMigration;
import com.abelium.INATrace.db.entities.User;
import com.abelium.INATrace.types.UserRole;
import com.abelium.INATrace.types.UserStatus;

public class V2020_05_05_15_30__Prefill_Master_Admin implements JpaMigration {
    
    public void migrate(EntityManager em, Environment environment) throws Exception {
        if (!"master".equals(environment.getProperty("INATrace.service.group"))) {
            return;
        }
        
        String email = environment.getProperty("INATrace.admin.email");
        String name = environment.getProperty("INATrace.admin.name");
        String surname = environment.getProperty("INATrace.admin.surname");
        String password = environment.getProperty("INATrace.admin.password");
        
        User admin = new User();
        admin.setEmail(email);
        admin.setName(name);
        admin.setSurname(surname);
        admin.setPassword(new BCryptPasswordEncoder().encode(password));
        admin.setStatus(UserStatus.ACTIVE);
        admin.setRole(UserRole.ADMIN);
        em.persist(admin);
    }
}
