package com.uc.ms_security.authorization;

import com.uc.ms_security.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class DynamicAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    private final PermissionService permissionService;

    @Override
    public AuthorizationResult authorize(
            Supplier<? extends Authentication> authenticationSupplier,
            RequestAuthorizationContext context
    ) {
        Authentication authentication = authenticationSupplier.get();

        // Deniega la petición si no hay una identidad autenticada.
        if (authentication == null || !authentication.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        Long userId;

        try {
            // El subject del JWT contiene el ID del usuario como texto.
            userId = Long.valueOf(authentication.getName());
        } catch (NumberFormatException exception) {
            return new AuthorizationDecision(false);
        }

        // Se autoriza usando el método HTTP y la ruta recibidos en esta petición.
        String method = context.getRequest().getMethod();

        String url = context.getRequest().getRequestURI();

        boolean permitted = permissionService.hasPermission(
                userId,
                method,
                url
        );

        return new AuthorizationDecision(permitted);
    }
}
