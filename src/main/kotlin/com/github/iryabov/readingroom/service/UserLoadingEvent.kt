package com.github.iryabov.readingroom.service

import net.n2oapp.framework.api.context.ContextEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.stereotype.Component

@Component
class UserLoadingEvent {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var context: ContextEngine

    @Value("\${admins}")
    lateinit var admins: List<String>

    @EventListener(AuthenticationSuccessEvent::class)
    fun onSuccessLogin(event: AuthenticationSuccessEvent) {
        val principal = event.authentication.principal
        val attributes = (principal as DefaultOidcUser).attributes
        val email = attributes["email"] as String
        val userId = userService.loadUser(
            email,
            attributes["name"] as String,
            attributes["picture"] as String
        )
        context.set(attributes)
        context.set("userId", userId)
        context.set("roles", if (admins.contains(email)) listOf("admin") else emptyList())
    }
}