package com.codecool.dadsinventory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.codecool.dadsinventory.security.ApplicationUserRole.DAD;
import static com.codecool.dadsinventory.security.ApplicationUserRole.MOM;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordConfig passwordConfig;

    @Autowired
    public ApplicationSecurityConfig(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/item/**", "/search").hasRole(DAD.name())
                .antMatchers("/home/**").hasRole(MOM.name())
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .rememberMe() // defaults to 2 weeks
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/");
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new com.javadevjournal.core.security.handlers.CustomAccessDeniedHandler();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails dad = User.builder()
                .username("dad")
                .password(passwordConfig.passwordEncoder().encode("password"))
                .roles(DAD.name())
                .build();

        UserDetails mom = User.builder()
                .username("mom")
                .password(passwordConfig.passwordEncoder().encode("password123"))
                .roles(MOM.name())
                .build();

        return new InMemoryUserDetailsManager(
                dad,
                mom
        );
    }
}
