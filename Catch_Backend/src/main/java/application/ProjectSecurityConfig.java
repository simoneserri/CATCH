package application;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    	http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/saveMsg")
                .ignoringRequestMatchers(PathRequest.toH2Console()))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().permitAll()
            )
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

  
}