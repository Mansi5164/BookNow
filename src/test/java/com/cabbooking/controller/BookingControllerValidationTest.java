package com.cabbooking.controller;

import com.cabbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = com.cabbooking.controller.BookingController.class)
@Import(com.cabbooking.exception.GlobalExceptionHandler.class)
public class BookingControllerValidationTest extends TestBase {

    @MockBean
    private BookingService bookingService;

    @Test
    public void whenEmptyBookingJson_thenReturns400AndValidationErrors() throws Exception {
        String emptyJson = "{}";

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value(containsString("pickupAddress")));
    }

    @Test
    public void whenMalformedBookingJson_thenReturns400AndMalformedMessage() throws Exception {
        String malformed = "{\"pickupAddress\": \"A\" \"dropoffAddress\": \"B\"}"; // missing comma

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformed))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value(containsString("Malformed JSON request")));
    }
}
