package com.oweit.logit

import JWTFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter


@Configuration
@EnableWebSecurity
class Security : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().httpBasic().disable().logout().disable().sessionManagement().disable().authorizeRequests().antMatchers("/token").permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()



        http.addFilterBefore(JWTFilter(), AnonymousAuthenticationFilter::class.java)
    }

}