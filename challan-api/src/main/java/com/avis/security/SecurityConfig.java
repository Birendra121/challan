package com.avis.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


	@Configuration
	@EnableWebSecurity
	public class SecurityConfig {

	    private final UserDetailsService userDetailsService;
	    
	   
	    public SecurityConfig(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }
	    
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    @Bean
	    public CustomLoginSuccessHandler customLoginSuccessHandler1() {
	        return new CustomLoginSuccessHandler();
	    }

	//}
	    
	    //--new code--
 
	    //----------
	   
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/register", "/login", "/index", "/challan/home", "/css/**", "/js/**", "/error").permitAll()
	                .antMatchers("/admin/**").hasRole("ADMIN")
	                .antMatchers("/admin/assign-permissions", "/admin/save-permissions").hasRole("ADMIN")
	                .antMatchers("/trc/**").hasAnyRole("USER", "ADMIN")
	                .antMatchers("/challan/vehicle", "/challan/upload").hasAnyRole("USER", "ADMIN")
	                .antMatchers("/user/**", "/user-home").hasRole("USER")
	                .antMatchers("/change-password").authenticated()
	                .anyRequest().authenticated()
	            .and()
	            .formLogin()
	                .loginPage("/login")
	                .successHandler(customLoginSuccessHandler1())
	                .permitAll()
	            .and()
	            .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login?logout")
	                .permitAll()
	            .and()
	            .csrf().disable()
	            .headers().frameOptions().disable();

	        return http.build();
	    }

	}

