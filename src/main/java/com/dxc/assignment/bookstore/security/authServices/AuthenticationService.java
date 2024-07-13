package com.dxc.assignment.bookstore.security.authServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dxc.assignment.bookstore.models.UserRole;
import com.dxc.assignment.bookstore.security.authModels.ApiKeyAuthentication;
import com.dxc.assignment.bookstore.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {
    @Autowired
    UserService userSvc;

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    @Value("${bookstore.api.key}")
    private String apiVal;


    public Authentication getAuthentication(HttpServletRequest request) {

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(apiVal)) {
            throw new BadCredentialsException("Invalid API Key");
        }

        // Extract the username from the apiKey
        String username = apiKey.substring(15);
        List<GrantedAuthority> grantedAuthorities = getUserAuthorities(username);

        //return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
        return new ApiKeyAuthentication(apiKey, username , grantedAuthorities);
    }

    private List<GrantedAuthority> getUserAuthorities(String username){
        Optional<UserRole> userRoleOpt = userSvc.findUserRoleByUsername(username);
        List<String> roles = new ArrayList<>();
        roles.add(userRoleOpt.get().getRole());

        List<GrantedAuthority> grantedAuthorities = roles.stream()
            .map(r -> {return new SimpleGrantedAuthority(r);})
            .collect(Collectors.toList());

        return grantedAuthorities;
    }

}
