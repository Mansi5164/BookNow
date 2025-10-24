package com.cabbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {

    private Long id;
    @Size(max = 50, message = "First name must be 50 characters or fewer")
    private String firstName;
    @Size(max = 50, message = "Last name must be 50 characters or fewer")
    private String lastName;
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be 100 characters or fewer")
    private String email;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String phoneNumber;
    @Size(max = 50, message = "Role must be 50 characters or fewer")
    private String role;
    @Size(max = 50, message = "Status must be 50 characters or fewer")
    private String status;
}