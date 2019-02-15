package com.springboot.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/static/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        //loginPage는 로그인 뷰 페이지를 연결하고, loginProcessingUrl은 Post로 로그인을 처리할 Url
        //SucessUrl은 로그인 성공 후 이동할 페이지, failureUrl은 실패 후 이동할 페이지를 지정한다.

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/board/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                .loginPage("/security/login")/* 내가 만든 로그인 페이지 */
                .loginProcessingUrl("/security/login")
                .defaultSuccessUrl("/")
                .successHandler(successHandler())
                .failureUrl("/security/login")
                .and()
                    .logout().permitAll().logoutSuccessUrl("/"); /* 로그아웃 성공시 리다이렉트 url */

    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");    //default로 이동할 url
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }



}