package ua.kiev.prog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

    public static final String ADMIN = "admin";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demo(final Json json, final PasswordEncoder encoder) {

        ToJson toJson = new ToJson();

        CustomUser customUserAdmin = new CustomUser(ADMIN,
                encoder.encode("password"),
                UserRole.ADMIN, "", "");
        toJson.addUser(customUserAdmin);

        CustomUser customUserModer = new CustomUser("Moder",
                encoder.encode("password"),
                UserRole.MODER, "", "");
        toJson.addUser(customUserModer);


        CustomUser customUser = new CustomUser("user",
                encoder.encode("password"),
                UserRole.USER, "", "");
        toJson.addUser(customUser);

        json.toJson(toJson.getAllUsers());
        return null;
    }

}
