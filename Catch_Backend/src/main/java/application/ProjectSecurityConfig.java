package application;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .cors() // ✅ Abilita CORS
	        .and()
	        .csrf(csrf -> csrf.disable()) // ✅ Disabilita CSRF per test
	        .authorizeHttpRequests(requests -> requests
	            .anyRequest().permitAll()
	        )
	        .formLogin().disable()
	        .httpBasic().disable()
	        .logout().disable()
	        .headers(headers -> headers.frameOptions().disable());

	    return http.build();
	}


	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("*"); 
	    configuration.addAllowedMethod("*");
	    configuration.addAllowedHeader("*");

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
  
}