package com.avis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inject from AppConfig

       @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // <--- Add this line
            .authorizeRequests()
                .antMatchers("/register", "/login","/logout-success", "/css/**", "/js/**", "/add", "/about", "/customer/details", "/customer/save", "/customer/add").permitAll()
                .antMatchers("/edit/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            .and()
            	.logout()
            		.logoutUrl("/logout")
            		.logoutSuccessUrl("/logout-success")
            		.invalidateHttpSession(true)
            		.deleteCookies("JSESSIONID")
                    .permitAll();

        return http.build();
    }

}
