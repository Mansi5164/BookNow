package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = MAX_NAME_LENGTH, message = "First name must be between 1 and " + MAX_NAME_LENGTH + " characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = MAX_NAME_LENGTH, message = "Last name must be between 1 and " + MAX_NAME_LENGTH + " characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = MAX_EMAIL_LENGTH, message = "Email must be " + MAX_EMAIL_LENGTH + " characters or fewer")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = PHONE_PATTERN, message = "Phone number should be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = MAX_PASSWORD_LENGTH, message = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters")
    private String password;

    private String role = "CUSTOMER";
}
