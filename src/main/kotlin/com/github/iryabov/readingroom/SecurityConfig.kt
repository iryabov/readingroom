package com.github.iryabov.readingroom

import net.n2oapp.framework.ui.context.SessionContextEngine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig(): WebSecurityConfigurerAdapter() {

    @Bean
    fun sessionContextEngine() = SessionContextEngine()

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().and().csrf().disable()
    }

}