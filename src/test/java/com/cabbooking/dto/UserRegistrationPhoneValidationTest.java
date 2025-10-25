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
 * Tests for phone number validation on UserRegistrationDto.
 */
public class UserRegistrationPhoneValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidPhone_thenNoPhoneViolation() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "0123456789",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")),
                "Expected no phoneNumber violations for a valid 10-digit phone");
    }

    @Test
    void whenInvalidPhone_thenPhoneViolation() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "12345",
                "password123",
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")),
                "Expected a phoneNumber violation for a non-10-digit phone");
    }
}
