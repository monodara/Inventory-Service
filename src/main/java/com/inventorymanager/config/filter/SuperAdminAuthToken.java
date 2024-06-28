package com.inventorymanager.config.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class SuperAdminAuthToken extends AbstractAuthenticationToken {
    public SuperAdminAuthToken() {
        super(AuthorityUtils.createAuthorityList("SUPERADMIN"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "SuperAdmin";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new RuntimeException("You cannot change authentication context");
    }
}
