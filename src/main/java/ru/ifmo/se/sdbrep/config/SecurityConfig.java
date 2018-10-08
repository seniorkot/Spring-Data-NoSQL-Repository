/*
 * MIT License
 *
 * Copyright (c) 2018 seniorkot
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.ifmo.se.sdbrep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ifmo.se.sdbrep.service.ProfileService;

/**
 * This class is used for Spring Security configuration.<br>
 * Sets limits on API calls and manages user authorization.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * This inner class contains different roles for
     * Spring Security.
     */
    public static class Roles {
        public static final String ANON = "ANON";
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";

        private static final String ROLE_ = "ROLE_";
        public static final String ROLE_ANON = ROLE_ + ANON;
        public static final String ROLE_USER = ROLE_ + USER;
        public static final String ROLE_ADMIN = ROLE_ + ADMIN;
    }

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new ProfileService();
    }

    /**
     * Configures {@link UserDetailsService} in application.
     *
     * @param auth Authentication Manager Builder
     * @throws Exception Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth.userDetailsService(userDetailsService);
    }

    /**
     * Configures and authorizes HTTP requests.
     *
     * @param http Configured security builder
     * @throws Exception Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/login").hasRole(Roles.ANON)
                .antMatchers("/api/signUp").hasRole(Roles.ANON)
                .antMatchers("/api/**").hasAnyRole(Roles.ADMIN, Roles.USER)
                .antMatchers("/admin/**").hasRole(Roles.ADMIN)
                .antMatchers("/**").denyAll()
                .and().csrf().disable()
                .anonymous().authorities(Roles.ROLE_ANON);
    }
}
