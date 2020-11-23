package com.thebigscale.accountmanagementservice.controller;

import lombok.val;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
public class AccountManagementControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired
    AccountManagementController accountManagementController;

    @Test
    @DisplayName("should return valid response for valid request")
    public void shouldReturnValidResponseForValidRequest() throws Exception {
        //Given
        val clientProfileJson = "{\n" +
                "    \"name\": \"sury\",\n" +
                "    \"age\": 16,\n" +
                "    \"profileSummary\": \"Test profile summary\",\n" +
                "    \"email\":\"surysharma@gmail.com\"\n" +
                "}";

        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/client")
                        .content(clientProfileJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", IsNull.notNullValue()))
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON)
        );
    }


    @Test
    @DisplayName("should return error response when minor age")
    void shouldReturnErrorResponseWhenMandatoryFieldsAreAbsent() throws Exception {
        //Given
        val clientProfileWithMinorAge = "{\n" +
                "    \"name\": \"sury\",\n" +
                "    \"age\": 14,\n" +
                "    \"profileSummary\": \"Test profile summary\",\n" +
                "    \"email\":\"surysharma@gmail.com\"\n" +
                "}";

        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/client")
                        .content(clientProfileWithMinorAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is("Min age should be more 15 years")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @DisplayName("should return error response when elderly age")
    void shouldReturnErrorResponseWhenElderlyAged() throws Exception {
        //Given
        val clientProfileWithMinorAge = "{\n" +
                "    \"name\": \"sury\",\n" +
                "    \"age\": 71,\n" +
                "    \"profileSummary\": \"Test profile summary\",\n" +
                "    \"email\":\"surysharma@gmail.com\"\n" +
                "}";

        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/client")
                        .content(clientProfileWithMinorAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is("Max age should be less than 70 years")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }


    @Test
    @DisplayName("should return error response when missing age")
    void shouldReturnErrorResponseWhenMissingAge() throws Exception {
        //Given
        val clientProfileWithMinorAge = "{\n" +
                "    \"name\": \"sury\",\n" +
                "    \"profileSummary\": \"Test profile summary\",\n" +
                "    \"email\":\"surysharma@gmail.com\"\n" +
                "}";

        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/client")
                        .content(clientProfileWithMinorAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", Is.is("Min age should be more 15 years")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("should return error response when invalid email")
    void shouldReturnErrorResponseWhenInvalidEmail() throws Exception {
        //Given
        val clientProfileWithMinorAge = "{\n" +
                "    \"name\": \"sury\",\n" +
                "    \"age\": 20,\n" +
                "    \"profileSummary\": \"Test profile summary\",\n" +
                "    \"email\":\"invalidAtgmail.com\"\n" +
                "}";

        //Then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/client")
                        .content(clientProfileWithMinorAge)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("Invalid email provided")))
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}