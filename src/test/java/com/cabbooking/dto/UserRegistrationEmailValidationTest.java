package com.cabbooking.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for email validation on UserRegistrationDto.
 */
public class UserRegistrationEmailValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidEmail_thenNoEmailViolation() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "John",
                "Doe",
                "john.doe@example.com",
                "1234567890",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected no email violations for a valid email");
    }

    @Test
    void whenInvalidEmail_thenEmailViolation() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "John",
                "Doe",
                "not-an-email",
                "1234567890",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected an email violation for an invalid email");
    }
}
