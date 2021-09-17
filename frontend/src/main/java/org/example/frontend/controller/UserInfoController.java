package org.example.frontend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by arcuri82
 * https://github.com/arcuri82/testing_security_development_enterprise_systems
 */

@Named
@RequestScoped
public class UserInfoController {

    public String getUserName(){
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
