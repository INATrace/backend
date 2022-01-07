package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.common.User;
import com.abelium.inatrace.types.UserRole;
import com.abelium.inatrace.types.UserStatus;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;

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
