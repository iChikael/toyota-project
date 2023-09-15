package com.cg.security;

import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IUserService userService;


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**").disable();
//        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());

        http.authorizeRequests()
                .antMatchers(
                        "/api/v1/auth/**"
                ).permitAll()
                .antMatchers(
                        "/api/v1/customers/**",
                        "/api/v1/staffs/**",
                        "/api/v1/staff-serviceAreas/**",
                        "/api/v1/maintenances/**",
                        "/api/v1/maintenance-items/**",
                        "/api/v1/maintenance-item-accessories/**",
                        "/api/v1/maintenance-maintenance-items/**",
                        "/api/v1/repairs/**",
                        "/api/v1/repair-items/**",
                        "/api/v1/repair-repair-items/**",
                        "/api/v1/service-areas/**",
                        "/api/v1/storage/**",
                        "/api/v1/car-queues/**",
                        "/api/v1/car-plates/**",
                        "/api/v1/car-plate-car-queues/**",
                        "/api/v1/order-services/**",
                        "/api/v1/order-maintenances/**",
                        "/api/v1/order-maintenance-items/**",
                        "/api/v1/order-maintenance-item-accessories/**",
                        "/api/v1/order-repair-items/**",
                        "/api/v1/order-repair-item-accessories/**",
                        "/api/v1/order-service-current-service-areas/**",
                        "/api/v1/bill-services/**",
                        "/api/v1/bill-service-details/**",
                        "/api/v1/bill-service-detail-accessories/**",
                        "/api/v1/dashboards/**"
                ).hasAnyAuthority("ADMIN","RECEPTION","TECHNICAL","CASHIER")
                .antMatchers(
                        "/api/v1/cars/**",
                        "/api/v1/auth/**",
                        "/api/v1/auth/**",
                        "/api/v1/customers/**",
                        "/api/v1/staffs/**",
                        "/api/v1/staff-serviceAreas/**",
                        "/api/v1/maintenances/**",
                        "/api/v1/maintenance-items/**",
                        "/api/v1/maintenance_item-accessories/**",
                        "/api/v1/maintenance-maintenance-items/**",
                        "/api/v1/repairs/**",
                        "/api/v1/repair-items/**",
                        "/api/v1/repair-repair-items/**",
                        "/api/v1/service-areas/**",
                        "/api/v1/car-plates/**",
                        "/api/v1/car-plate-car-queues/**",
                        "/api/v1/order-services/**",
                        "/api/v1/service-areas/**",
                        "/api/v1/storage/**",
                        "/api/v1/vehicle/**",
                        "/api/v1/car-queues/**",
                        "/api/v1/cars/**"
                ).permitAll()
                .antMatchers(
                        "/login",
                        "/cp/login",
                        "/forgot",
                        "/logout",
                        "/forget-password",
                        "/update-password/*",
                        "/error/*",
                        "/temp/*"
                ).permitAll()
                .antMatchers(
                        "/static/**",
                        "/resources/**",
                        "/assets/**"
                ).permitAll()
                .antMatchers(
                        "/v3/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/configuration/security",
                        "/swagger-ui/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/cp/login")
                .deleteCookies("JWT")
                .invalidateHttpSession(true)
        ;

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }

}
