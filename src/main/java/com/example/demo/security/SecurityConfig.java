package com.example.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*登入*/
    public static final String LOGIN = "/api/login";

    @Autowired
    private CustomProvider customProvider;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*關閉登入*/
//        http.csrf().disable();//暫時關閉csrf，為了開發方便
//        http.authorizeRequests().anyRequest().permitAll();
//cRww3hJeRE-wkGXuSzsFFA
//        http.csrf().disable();
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl(LOGIN);

        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CustomProcessingFilter loginFilter() throws Exception {
        CustomProcessingFilter filter = new CustomProcessingFilter(LOGIN);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(customProvider); //設定驗證器
    }
}