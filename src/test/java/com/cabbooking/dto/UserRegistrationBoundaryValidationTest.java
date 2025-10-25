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
 * Boundary tests for size/length constraints on UserRegistrationDto.
 */
public class UserRegistrationBoundaryValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenFirstNameAtMinAndMaxBounds_thenNoViolation() {
        // min length 1
        UserRegistrationDto dtoMin = new UserRegistrationDto(
                "A",
                "Smith",
                "a.smith@example.com",
                "1234567890",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violationsMin = validator.validate(dtoMin);
        assertTrue(violationsMin.stream().noneMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected no firstName violation at min length");

        // max length 50
        String fifty = "A".repeat(50);
        UserRegistrationDto dtoMax = new UserRegistrationDto(
                fifty,
                "Smith",
                "a.smith@example.com",
                "1234567890",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violationsMax = validator.validate(dtoMax);
        assertTrue(violationsMax.stream().noneMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected no firstName violation at max length");
    }

    @Test
    void whenFirstNameTooLong_thenViolation() {
        // 51 chars -> should violate
        String fiftyOne = "A".repeat(51);

        UserRegistrationDto dto = new UserRegistrationDto(
                fiftyOne,
                "Smith",
                "a.smith@example.com",
                "1234567890",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected a firstName size violation for length > 50");
    }

    @Test
    void whenPasswordTooShort_thenViolation() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "Bob",
                "Smith",
                "bob.smith@example.com",
                "1234567890",
                "short",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected a password size violation for too-short password");
    }
}
