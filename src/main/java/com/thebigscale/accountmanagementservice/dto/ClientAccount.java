package com.thebigscale.accountmanagementservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.*;

@Data @Builder @Jacksonized
public class ClientAccount {

    private String id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Min(value = 15, message = "Min age should be more 15 years")
    @Max(value = 70, message = "Max age should be less than 70 years")
    private int age;

    @NotNull(message = "Name cannot be null")
    private String profileSummary;

    @Email(message = "Invalid email provided")
    @NotNull
    private String email;
}
