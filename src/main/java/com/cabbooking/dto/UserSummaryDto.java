package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {

    private Long id;
    @Size(max = MAX_NAME_LENGTH, message = "First name must be 50 characters or fewer")
    private String firstName;
    @Size(max = MAX_NAME_LENGTH, message = "Last name must be 50 characters or fewer")
    private String lastName;
    @Email(message = "Email should be valid")
    @Size(max = MAX_EMAIL_LENGTH, message = "Email must be 100 characters or fewer")
    private String email;
    @Pattern(regexp = PHONE_PATTERN, message = "Phone number should be 10 digits")
    private String phoneNumber;
    @Size(max = MAX_NAME_LENGTH, message = "Role must be 50 characters or fewer")
    private String role;
    @Size(max = MAX_NAME_LENGTH, message = "Status must be 50 characters or fewer")
    private String status;
}