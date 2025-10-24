package com.cabbooking.service;

import com.cabbooking.entity.User;
import com.cabbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.cabbooking.util.ApplicationConstants.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException(EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new RuntimeException(PHONE_NUMBER_ALREADY_EXISTS);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> findUsersByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    public List<User> findUsersByStatus(User.UserStatus status) {
        return userRepository.findByStatus(status);
    }

    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public User updateUserStatus(Long id, User.UserStatus status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        user.setStatus(status);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException(USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public boolean validateCredentials(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}