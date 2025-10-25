package com.cabbooking.controller;

import com.cabbooking.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;

@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
public abstract class TestBase {

    @Autowired
    protected org.springframework.test.web.servlet.MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
