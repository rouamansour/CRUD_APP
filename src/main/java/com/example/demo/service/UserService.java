package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        logger.info("Fetching all users from the database");
        List<User> users = userRepository.findAll();
        logger.info("Found {} users in the database", users.size());
        return users;
    }

    public User saveUser(User user) {
        logger.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());

        // Update the orders list properly
        List<Order> updatedOrders = userDetails.getOrders();

        existingUser.getOrders().clear();
        existingUser.getOrders().addAll(updatedOrders);

        return userRepository.save(existingUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
