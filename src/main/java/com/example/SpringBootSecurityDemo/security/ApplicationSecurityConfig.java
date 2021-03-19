package com.example.SpringBootSecurityDemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.SpringBootSecurityDemo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * config http security
     * antmatchers with pattern
     * @param http
     * @throws Exception
     */


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/csss/*", "/js/*").permitAll()
                .antMatchers( "/api/**").hasRole(STUDENT.name())
                //.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                //.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                //.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                // .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                 .anyRequest()
                .authenticated()
                .and()
                //.httpBasic();
                .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/courses", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
                    .key("somethingthatsecure")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                 .logoutSuccessUrl("/login");
    }

    /**
     *  user created with different role
     * @return in memory user
     */

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails anaWoodUser = User.builder()
                .username("studentuser")
                .password(passwordEncoder.encode("password"))
                //.roles(STUDENT.name())
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails adminUser = User.builder()
                .username("adminuser")
                .password(passwordEncoder.encode("password123"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails adminTrainee = User.builder()
                .username("adminTrainee")
                .password(passwordEncoder.encode("password123"))
                //.roles(ADMINTRAINEE.name())
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();


        return  new InMemoryUserDetailsManager(
                anaWoodUser,
                adminUser,
                adminTrainee
        );
    }
}
