package com.oweit.logit.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@EnableWebSecurity
class Security : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().httpBasic().disable().logout().disable()
            .authorizeRequests().antMatchers("/auth/token").permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()

        http.sessionManagement() // dont create a session for this configuration
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        //http.sessionManagement().disable()

        http.addFilterBefore(JWTFilter(), AnonymousAuthenticationFilter::class.java)
    }

}