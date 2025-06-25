package ksy.medichat.config;

import ksy.medichat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoderConfig passwordEncoder; // 주입 받음

    // 1. 스프링 시큐리티 기능 비활성화 (정적 리소스에 대한 시큐리티 예외 처리)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                                    .requestMatchers("/css/**",
                                                    "/js/**",
                                                    "/images/**");
    }

    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성 (비즈니스 로직용 필터 체인 설정)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
        return http
                    .authenticationManager(auth.build()) // 3. 인증, 인가 설정 (권한이 적을 수록 아래에서 설정) (AuthenticationManager 구성)
                    .authorizeHttpRequests(authz -> authz
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/chat/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form // 4. 폼 기반 로그인 설정
                            .loginPage("/users/login")
                            .usernameParameter("id") // ID 필드명을 username 대신 "id"로 지정
                            .passwordParameter("password") // Password 필드명을 password 대신 "password"로 지정 -> 일단 유지보수를 위해 작성
                            //.loginProcessingUrl("/users/doLogin") // 로그인 처리 경로를 별도로 설정하고 싶다면
                            .defaultSuccessUrl("/")
                            .permitAll()
                    )
                    .logout(logout -> logout // 5. 로그아웃 설정
                            .logoutUrl("/users/logout")                // 로그아웃 처리 URL
                            .logoutSuccessUrl("/")               // 로그아웃 후 리다이렉트할 경로
                            .invalidateHttpSession(true)        // 세션 무효화
                    )
                    .csrf(AbstractHttpConfigurer::disable) // 6. csrf 비활성화
                    .build();
    }

    // 7. 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 8. 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    // 9. 패스워드 인코더로 사용할 빈 등록 -> 순환 참조 오류로 인해 PasswordEncoderConfig 파일로 옮김
    /*@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/


}