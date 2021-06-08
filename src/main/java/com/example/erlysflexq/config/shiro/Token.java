package com.example.erlysflexq.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class Token implements AuthenticationToken {

    private String token;

    public Token(String token) {
        this.token = token;
    }

    public Token() {
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
