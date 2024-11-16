package com.example.RBAC.Config;

import com.example.RBAC.Filter.JwtFilter;
import com.example.RBAC.Service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MyAuth {
    private MyUserDetailsService myUserDetailsService;

    private JwtFilter jwtFilter;

    public MyAuth(MyUserDetailsService myUserDetailsService, JwtFilter jwtFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers("/regis").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/getAll").permitAll()
                        .requestMatchers("/AdminRegis").permitAll()
                        .requestMatchers("/ModeRegis").permitAll()
                        //has role expect db record with ROLE_**** prefix
                        //hasAuthority(ADMIN) ,same with db ,
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only admins can access this
                        .requestMatchers("/mode/**").hasRole("MODERATOR") // Only admins can access this
                        .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(customizer->customizer.disable());
        return http.build();
    }

    //specify to use username and password authentication
    //set our own userdetails service and encoder for verification
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService( myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    //hash password
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //provide authentiocation manager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
        return configuration.getAuthenticationManager();
    }
}

//number 1 The authentication Filter from the Reading the Username & Password section passes a UsernamePasswordAuthenticationToken to the
// AuthenticationManager, which is implemented by ProviderManager.
//
//number 2 The ProviderManager is configured to use an AuthenticationProvider of type DaoAuthenticationProvider.
//
//number 3 DaoAuthenticationProvider looks up the UserDetails from the UserDetailsService.
//
//number 4 DaoAuthenticationProvider uses the PasswordEncoder to validate the password on the UserDetails returned in the previous step.
//
//number 5 When authentication is successful, the Authentication that is returned is of type UsernamePasswordAuthenticationToken and has a principal
// that is the UserDetails returned by the configured UserDetailsService. Ultimately, the returned UsernamePasswordAuthenticationToken is set on the
// SecurityContextHolder by the authentication Filter.

//https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html