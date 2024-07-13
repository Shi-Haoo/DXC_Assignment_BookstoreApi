package com.dxc.assignment.bookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.assignment.bookstore.models.UserRole;
import com.dxc.assignment.bookstore.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public Optional<UserRole> findUserRoleByUsername(String username){
        return userRepo.findUserRoleByUsername(username);
    }
}
