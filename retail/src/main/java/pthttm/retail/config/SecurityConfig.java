package pthttm.retail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import pthttm.retail.security.CustomPasswordEncoder;
import pthttm.retail.service.CustomerDetailsService;

import pthttm.retail.service.EmployeeDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf(csrf -> csrf.disable()) // Disable CSRF protection (for testing only)
//                .authorizeHttpRequests((requests) -> requests
//                        .anyRequest().permitAll())
//                .logout(LogoutConfigurer::permitAll);
//        return httpSecurity.build();
//    }

    @Bean
    public DaoAuthenticationProvider customerAuthProvider(CustomerDetailsService customerDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerDetailsService);
        authProvider.setPasswordEncoder(new CustomPasswordEncoder());
        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider employeeAuthProvider(EmployeeDetailsService employeeDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employeeDetailsService);
        authProvider.setPasswordEncoder(new CustomPasswordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain customerSecurityFilterChain(HttpSecurity http,
                                                           DaoAuthenticationProvider customerAuthProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(customerAuthProvider)
                .securityMatcher("/customer/**")  // Đảm bảo đường dẫn được áp dụng cho quyền của ROLE_EMPLOYEE
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/customer/**").hasRole("CUSTOMER") // Cấp quyền truy cập cho ROLE_EMPLOYEE
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/customer/login")
                        .loginProcessingUrl("/customer/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll());

        return http.build();
    }

    @Bean
    public SecurityFilterChain employSecurityFilterChain(HttpSecurity http,
                                                         DaoAuthenticationProvider employeeAuthProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(employeeAuthProvider)
                .securityMatcher("/manage/**")  // Áp dụng bộ lọc cho toàn bộ URL bắt đầu bằng /manage/**
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/manage/**").hasRole("EMPLOYEE") // Cấp quyền truy cập cho ROLE_EMPLOYEE
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/manage/login")
                        .loginProcessingUrl("/manage/login")
                        .defaultSuccessUrl("/manage/product", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/manage/logout")
                        .logoutSuccessUrl("/manage/login")
                        .deleteCookies()
                        .invalidateHttpSession(true)       // Hủy phiên làm việc hiện tại
                        .permitAll());

        return http.build();
    }

//    @Bean
//    public static PasswordEncoder passwordEncoder(){
//        return new CustomPasswordEncoder();
//    }
    @Bean
    public CustomPasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder();
    }
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }
}
