package com.cabbooking.controller;

import com.cabbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = com.cabbooking.controller.UserController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
@Import(com.cabbooking.exception.GlobalExceptionHandler.class)
public class UserControllerValidationTest extends TestBase {

    @MockBean
    private UserService userService;

    @Test
    public void whenInvalidUserObject_thenReturns400AndValidationErrors() throws Exception {
        // missing required fields / invalid values
        String invalidUserJson = "{" +
                "\"firstName\": \"\", " +
                "\"lastName\": \"\", " +
                "\"email\": \"not-an-email\", " +
                "\"phoneNumber\": \"123\", " +
                "\"password\": \"short\" " +
                "}";

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidUserJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value(containsString("firstName")));
    }

    @Test
    public void whenMissingRequiredField_thenReturns400AndDescriptiveMessage() throws Exception {
        // missing email field entirely
        String missingEmailJson = "{" +
                "\"firstName\": \"Jane\", " +
                "\"lastName\": \"Doe\", " +
                "\"phoneNumber\": \"1234567890\", " +
                "\"password\": \"validpassword\" " +
                "}";

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missingEmailJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value(containsString("email")));
    }

    @Test
    public void whenMalformedJson_thenReturns400AndMalformedMessage() throws Exception {
        String malformed = "{\"firstName\": \"John\" \"lastName\": \"Doe\"}"; // missing comma

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformed))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(containsString("Malformed JSON request")));
    }
}
