package com.example.SpringBootSecurityDemo.security;

import com.example.SpringBootSecurityDemo.auth.ApplicationUserService;
import com.example.SpringBootSecurityDemo.jwt.JwtConfig;
import com.example.SpringBootSecurityDemo.jwt.JwtTokenVerifier;
import com.example.SpringBootSecurityDemo.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static com.example.SpringBootSecurityDemo.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/csss/*", "/js/*").permitAll()
                .antMatchers( "/api/**").hasRole(STUDENT.name())

                /**
                 * Comment out antmatchers as no longer using
                 */
                //.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                //.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                //.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.name())
                // .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())

                 .anyRequest()
                .authenticated();


        /**
         * Commenting out form based authentication
         * due to implementation of jwt
         */
//                    .and()
//                    //.httpBasic();
//                .formLogin()
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/courses", true)
//                    .passwordParameter("password")
//                    .usernameParameter("username")
//                .and()
//                .rememberMe()
//                    .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
//                    .key("somethingthatsecure")
//                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID", "remember-me")
//                 .logoutSuccessUrl("/login");
    }

    /**
     *  Commenting UserDetailsService as getting user
     *  data is coming from  DummyApplication USer Service
     *
     */

//    /**
//     *  user created with different role
//     * @return in memory users / db
//     */
//
//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails anaWoodUser = User.builder()
//                .username("studentuser")
//                .password(passwordEncoder.encode("password"))
//                //.roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminUser = User.builder()
//                .username("adminuser")
//                .password(passwordEncoder.encode("password000"))
//                //.roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminTrainee = User.builder()
//                .username("adminTrainee")
//                .password(passwordEncoder.encode("password000"))
//                //.roles(ADMINTRAINEE.name())
//                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//
//        return  new InMemoryUserDetailsManager(
//                anaWoodUser,
//                adminUser,
//                adminTrainee
//        );
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
     public DaoAuthenticationProvider daoAuthenticationProvider() {
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setPasswordEncoder(passwordEncoder);
         provider.setUserDetailsService(applicationUserService);
         return provider;
     }
}
