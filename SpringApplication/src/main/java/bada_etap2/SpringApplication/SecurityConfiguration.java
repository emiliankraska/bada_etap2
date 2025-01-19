package bada_etap2.SpringApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;

@Configuration
public class SecurityConfiguration {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("user")  // Password encoded with NoOpPasswordEncoder
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("admin")  // Password encoded with NoOpPasswordEncoder
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // Use a NoOpPasswordEncoder for simplicity
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index","/pracownicy","/kontrakty", "/static/**").permitAll()  // Allow public access to /index
                        .anyRequest().authenticated()  // Require authentication for other pages
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Custom login page
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/index")  // Redirect to /index on successful logout
                        .permitAll()
                );

        return http.build();
    }
}
