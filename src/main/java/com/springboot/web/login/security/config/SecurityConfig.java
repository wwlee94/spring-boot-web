package com.springboot.web.login.security.config;

import com.springboot.web.login.security.CustomUserDetailsService;
import com.springboot.web.login.OAuth.social.SocialService;
import com.springboot.web.login.OAuth.social.facebook.FacebookOAuth2ClientAuthenticationProcessingFilter;
import com.springboot.web.login.OAuth.social.google.GoogleOAuth2ClientAuthenticationProcessingFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
@EnableOAuth2Client
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2ClientContext oauth2ClientContext;
    private final SocialService socialService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        //인증할 것들을 풀어준다.(리소스 css, js ... ).
        //web.ignoring().antMatchers("/static/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")//ADMIN 권한을 가진 사람만  "/admin/**" 으로 접근 가능.
                .antMatchers("/board/**").authenticated() ///board 는 권한을 가진 회원만 접근 가능
                .antMatchers("/**").permitAll()//나머지는 모드 접근 가능
                .and()

                .formLogin()//로그인페이지 커스텀
                .loginPage("/security/login")//로그인 페이지 : 없다면 기본 제공 페이지가 뜬다.
                .loginProcessingUrl("/security/login")//로그인 처리 페이지
                .defaultSuccessUrl("/")//로그인 성공시 이동 URL
                .successHandler(successHandler())//로그인 성공시 successHandler() 호출
                .failureHandler(failureHandler())
                .and()

                .logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// "/logout" 호출할 경우 로그아웃
                .logoutSuccessUrl("/") // 로그아웃이 성공했을 경우 이동할 페이지
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);


    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler("/");    //default로 이동할 url
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new CustomLoginFailureHandler();
    }

    //패스워드 인코더 (상속 받아서 커스텀도 가능. )
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//    }

    //스프링 부트 어플리케이션에서 올바른 순서로 호출할 수 있도록 필터를 연결해야하는데, OAuth2ClientContextFilter를 받아서
    //Spring Security보다 낮은 순서로 필터를 등록하고, 이를 이용해 인증 요청의 예외에 의해 리다이렉션을 처리하는데 사용할 수 있다.
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100); //  setOrder 메서드로 Spring Security 필터 보다 우선순위를 낮게 설정합니다.
        return registration;
    }

    // ClientResources 사용으로 쉽게 properties 값들을 빈으로 등록 .
    @Bean
    @ConfigurationProperties("facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }
    @Bean
    @ConfigurationProperties("google")
    public ClientResources google() {
        return new ClientResources();
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(google(), new GoogleOAuth2ClientAuthenticationProcessingFilter(socialService)));
        filters.add(ssoFilter(facebook(), new FacebookOAuth2ClientAuthenticationProcessingFilter(socialService)));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, OAuth2ClientAuthenticationProcessingFilter filter) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(restTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId());
        filter.setTokenServices(tokenServices);
        tokenServices.setRestTemplate(restTemplate);
        return filter;
    }



}