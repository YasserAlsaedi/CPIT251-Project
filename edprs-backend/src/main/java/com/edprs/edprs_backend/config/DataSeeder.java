package com.edprs.edprs_backend.config;

import com.edprs.edprs_backend.model.unit; 
import com.edprs.edprs_backend.model.user; 
import com.edprs.edprs_backend.repository.UnitRepository;
import com.edprs.edprs_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, 
                                   UnitRepository unitRepo, 
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            
            //  1. CREATE SUPERVISOR 
            if (userRepo.findByUsername("admin").isEmpty()) {
                user admin = new user();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("SUPERVISOR");
                userRepo.save(admin);
                System.out.println("✅ Admin Created");
            }

            //  2. CREATE UNITS (AMB-01 to AMB-04)
            createUnit(unitRepo, "AMB-01", 21.5433, 39.1728);
            createUnit(unitRepo, "AMB-02", 21.5500, 39.1800);
            createUnit(unitRepo, "AMB-03", 21.5600, 39.1900);
            createUnit(unitRepo, "AMB-04", 21.5700, 39.2000);

            //  3. CREATE PARAMEDICS (2 per Unit) 
            // Unit 1
            createMedic(userRepo, passwordEncoder, "medic1", "AMB-01");
            createMedic(userRepo, passwordEncoder, "medic2", "AMB-01");

            // Unit 2
            createMedic(userRepo, passwordEncoder, "medic3", "AMB-02");
            createMedic(userRepo, passwordEncoder, "medic4", "AMB-02");

            // Unit 3
            createMedic(userRepo, passwordEncoder, "medic5", "AMB-03");
            createMedic(userRepo, passwordEncoder, "medic6", "AMB-03");

            // Unit 4
            createMedic(userRepo, passwordEncoder, "medic7", "AMB-04");
            createMedic(userRepo, passwordEncoder, "medic8", "AMB-04");
        };
    }

    //  HELPER FUNCTIONS TO KEEP CODE CLEAN 

    private void createUnit(UnitRepository repo, String code, double lat, double lon) {
        if (repo.findByCode(code) == null) {
            unit u = new unit();
            u.setCode(code);
            u.setStatus("OFFLINE");
            u.setLatitude(lat);
            u.setLongitude(lon);
            repo.save(u);
            System.out.println("✅ Unit Created: " + code);
        }
    }

 

    private void createMedic(UserRepository repo, PasswordEncoder encoder, String username, String unitCode) {
        // 1. Check if user exists
        if (repo.findByUsername(username).isPresent()) {
            System.out.println("ℹ️ User already exists: " + username + " (Skipping)");
            return; // EXIT IMMEDIATELY
        }

        // 2. If not, create new
        user m = new user();
        m.setUsername(username);
        m.setPassword(encoder.encode("123")); 
        m.setRole("PARAMEDIC");
        m.setUnitCode(unitCode);
        repo.save(m);
        System.out.println("✅ Medic Created: " + username + " (Unit: " + unitCode + ")");
    }
}