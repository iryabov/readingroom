package com.github.iryabov.readingroom.service

import net.n2oapp.framework.access.simple.PermissionApi
import net.n2oapp.framework.api.user.UserContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class OAuthPermissionApi : PermissionApi {
    override fun hasPermission(user: UserContext, permissionId: String): Boolean = hasRole(user, permissionId)

    override fun hasRole(user: UserContext, roleId: String): Boolean =
       (user.get("roles") as List<*>).contains(roleId)

    override fun hasAuthentication(user: UserContext): Boolean =
        SecurityContextHolder.getContext()?.authentication?.isAuthenticated ?: false

    override fun hasUsername(user: UserContext, name: String): Boolean =
        SecurityContextHolder.getContext()?.authentication?.principal == name

}