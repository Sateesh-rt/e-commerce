package com.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
@Service
public class UserService {

@Autowired
private UserRepository userRepository;


public User saveUser(User user) {
    return userRepository.save(user);
}

public User login(String name, String password, String role) {
	 return userRepository.findByNameAndPasswordAndRole(name, password, role);
}

}
