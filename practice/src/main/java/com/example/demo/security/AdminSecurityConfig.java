

package com.example.demo.security;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AdminSecurityConfig {
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/admin/contacts/;id");
		
	}
	
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    		http
		        .authorizeHttpRequests((authz) -> authz
		                .requestMatchers("/admin/signup").permitAll()
		                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		                .requestMatchers("/admin/contacts").hasRole("USER")
		                .anyRequest().authenticated())
                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginPage("/admin/signin") // ログインページのpath指定
                        .loginProcessingUrl("/admin/signin")
                        .defaultSuccessUrl("/admin/contacts",true)
                        .permitAll())
                .logout((logout) -> logout
                        .logoutSuccessUrl("/admin/logout")
                        .logoutUrl("/admin/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
		 return http.build();
		 
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
