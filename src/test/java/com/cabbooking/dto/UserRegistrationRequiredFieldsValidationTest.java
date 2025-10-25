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
 * Tests for required (NotBlank) field validations on UserRegistrationDto.
 */
public class UserRegistrationRequiredFieldsValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenRequiredFieldsBlank_thenViolationsForEachRequiredField() {
        UserRegistrationDto dto = new UserRegistrationDto(
                "", // firstName blank
                "", // lastName blank
                "", // email blank
                "", // phone blank
                "", // password blank
                "CUSTOMER"
        );

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);

        // Expect at least one violation for each required property
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected violation for blank firstName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")),
                "Expected violation for blank lastName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected violation for blank email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")),
                "Expected violation for blank phoneNumber");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected violation for blank password");
    }
}
