package com.soft.app.spring.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("admin").password("password").roles("ADMIN");

        //Set Sequence of Provider
        //2.Custom Authentication Provider
        auth.userDetailsService(userDetailsService).passwordEncoder((PasswordEncoder) userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers(
                        "/login/**",
                        "/registration/**",
                        "/rest-api/**",
                        "/resources/images/**",
                        "/resources/fonts/**",
                        "/resources/logs/**",
                        "/resources/scripts/**",
                        "/resources/styles/**",
                        "/resources/**/scripts/**",
                        "/resources/**/styles/**",
                        "/resources/**/fonts/**"
                ).permitAll()
//                .antMatchers("/resources/**").authenticated()
                .antMatchers("/**").authenticated()

                .and().logout().logoutUrl("/logout")
                .and().formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username").passwordParameter("j_password").successHandler(getAuthenticationSuccessHandler())
                .and().exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint())
                .accessDeniedPage("/Access_Denied");

        http.csrf().disable();
    }


    @Bean
    public CustomAuthenticationEntryPoint getAuthenticationEntryPoint() {
        CustomAuthenticationEntryPoint authenticationEntryPoint = new CustomAuthenticationEntryPoint();
        authenticationEntryPoint.setLoginPageUrl("/login");
        authenticationEntryPoint.setReturnParameterEnabled(true);
        authenticationEntryPoint.setReturnParameterName("r");

        return authenticationEntryPoint;
    }

    @Bean
    public CustomAuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler authenticationHandler = new CustomAuthenticationSuccessHandler();
        authenticationHandler.setDefaultTargetUrl("/");
        authenticationHandler.setTargetUrlParameter("spring-security-redirect");

        return authenticationHandler;
    }
}
