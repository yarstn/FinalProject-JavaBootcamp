package com.example.final_project.Config;

import com.example.final_project.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    // [Mohammed]
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    // [Mohammed] Path ----> [Abdulaziz - Yara - Mohammed]
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/user/request-account-deletion",
                        "/api/v1/user/cancel-request-account-deletion",
                        "/api/v1/user/register", // delete after test postman
                        "/api/v1/center/center-register",
                        "/api/v1/parent/register", // حذف
                        "/api/v1/competition/get-competition-by-name**",
                        "/api/v1/competition/get-all-competitions",
                        "/api/v1/program/displayChosedCenterPrograms/**",
                        "/api/v1/program/get-programs",
                        "/api/v1/program/search",
                        "/api/v1/program/display-open-programs",
                        "/api/v1/program/get-program-by/min-age/**",
                        "/api/v1/newsletter/subscribe",
                        "/api/v1/newsletter/unsubscribe",
                        "/api/v1/advertisement/display-all-adverts",
                        "/api/v1/program/display-programs-by-date/**",
                        "/api/v1/notification/get-all-my-notifications",
                        "/api/v1/notification/get-notification-by-id/{notification_id}",
                        "/api/v1/notification/add-notification",
                        "/api/v1/notification/update-notification/{notificationId}",
                        "/api/v1/notification/delete-notification",
                        "/api/v1/notification/delete-all-my-notifications",
                        "/api/v1/notification/delete-all-my-notifications-is-read",
                        "/api/v1/notification/read-notification/{notificationId}",
                        "/api/v1/notification/read-all-notifications",
                        "/api/v1/notification/get-all-notifications-is-not-read",
                        "/api/v1/competition/get-competition-by-id/{competitionId}",
                        "/api/v1/competition/search-by-date/{startDate}/{endDate}",
                        "/api/v1/competition/search-competition-by-name/{name}",
                        "/api/v1/competition/search-competitions-by-type/{type}",
                        "/api/v1/competition/search-competitions-by-age-range/{ageFrom}/{ageTo}",
                        "/api/v1/progress/get-progress-by-program-id/{programId}",
                        "/api/v1/center/display-approved-centers"
                        ).permitAll()

                .requestMatchers(
                        "/api/v1/parent/update",
                        "/api/v1/comments/add/{centerId}",
                        "/api/v1/complaint/add/{centerId}",
                        "/api/v1/child/register",
                        "/api/v1/child/update/{id}",
                        "/api/v1/child/update/{id}",
                        "/api/v1/comments/delete/{id}",
                        "/api/v1/parent/my-account",
                        "/api/v1/parent/my-children",
                        "/api/v1/parent/like-center/**",
                        "/api/v1/parent/liked-centers",
                        "/api/v1/notification/request-participation-in-competition/**",
                        "/api/v1/child/{childId}/program/{programId}/apply",
                        "/api/v1/child/my-programs",
                        "/api/v1/child/{childId}/cancel-program/{programId}",
                        "/api/v1/child/{childId}/competition/{competitionId}/register",
                        "/api/v1/parent/add-rate/**",
                        "/api/v1/complaint/my-complaint",
                        "/api/v1/competition/get-all-competitions-for-child/{childId}",
                        "/api/v1/competition/participation-request/{competitionId}/{childId}",
                        "/api/v1/competition/cancel-child-participation/{competitionId}/{childId}",
                        "/api/v1/progress/get-all-child-progress",
                        "/api/v1/progress/get-all-child-progress-by-child-id/{childId}",
                        "/api/v1/certificate/get-my-certificate/**"
                ).hasAuthority("PARENT") // PARENT

                .requestMatchers(
                        "/api/v1/center/update-center/**",
                        "/api/v1/center/add-program" ,
                        "/api/v1/comments/get-all",
                        "/api/v1/complaint/get-all",
                        "/api/v1/center/Center-Account",
                        "/api/v1/center/change-password/**",
                        "/api/v1/program/add-program",
                        "/api/v1/program/delete-all-closed-programs",
                        "/api/v1/program/delete-program/**",
                        "/api/v1/program/set-program-stetus/**",
                        "/api/v1/program/update-program/**",
                        "/api/v1/center/display-total-center-financial-returns",
                        "/api/v1/program/display-programs-financial-returns/**",
                        "/api/v1/program/display-number-of-children-in-the-program/",
                        "/api/v1/center/display-total-number-of-joind-childrens",
                        "/api/v1/advertisement/add-advert",
                        "/api/v1/advertisement/get-my-adverts",
                        "/api/v1/complaint/center",
                        "/api/v1/center/display-total-number-center-program",
                        "/api/v1/progress/modify-child-progress/{progressId}",
                        "/api/v1/progress/delete-child-progress/{progressId}",
                        "/api/v1/center/expand-program/**",
                        "/api/v1/certificate/issue/**",
                        "/api/v1/program/get-my-programs-by-center",
                        "/api/v1/center/display-childrens-by-program/**"
                        ).hasAuthority("CENTER") // CENTER


                .requestMatchers(
                        "/api/v1/user/get-all-users",
                        "/api/v1/parent/get-all",
                        "/api/v1/parent/delete/{id}",
                        "/api/v1/center/delete-center/{centerid}",
                        "/api/v1/center/get-all-centers",
//                        "/api/v1/notification/participation-request/**",
                        "/api/v1/program/update-program/**",
                        "/api/v1/newsletter/subscribers",
                        "/api/v1/advertisement/approve-advert/centerid/**",
                        "/api/v1/advertisement/reject-center-advert/-center-id/**",
                        "/api/v1/advertisement/remove-rejected-adverts",
                        "/api/v1/competition/add-competition",
                        "/api/v1/competition/get-competition-by-id**",
                        "/api/v1/competition/update-competition/{competitionId}",
                        "/api/v1/competition/delete-competition/{competitionId}",
                        "/api/v1/competition/get-all-competitions-for-child/{childId}",
                        "/api/v1/competition/approve-participation-request/{competitionId}/{childId}/{parentId}",
                        "/api/v1/competition/reject-participation-request/{competitionId}/{childId}/{parentId}",
                        "/api/v1/competition/cancel-child-participation/{competitionId}/{childId}",
                        "/api/v1/progress/get-all-child-progress",
                        "/api/v1/progress/get-all-child-progress-by-child-id/{childId}",
                        "/api/v1/progress/modify-child-progress/{progressId}",
                        "/api/v1/center/approve-center-registration/{centerId}",
                        "/api/v1/center/reject-center-registration/{centerId}/{rejectionReason}",
                        "/api/v1/certificate/get-all-certificates"
                        ).hasAuthority("ADMIN") // ADMIN
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
