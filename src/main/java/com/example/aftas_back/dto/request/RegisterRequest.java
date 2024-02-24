package com.example.aftas_back.dto.request;

import com.example.aftas_back.domain.enums.IdentityDocumentType;
import com.example.aftas_back.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "firstname is required")
    @NotNull
    private String name;

    @NotBlank(message = "lastname is required")
    @NotNull
    private String familyName;

    @NotBlank(message = "nationality is required")
    @NotNull
    private String nationality;

    @NotBlank(message = "identityDocumentType is required")
    @NotNull
    private IdentityDocumentType identityDocumentType;

    @NotBlank(message = "identityNumber is required")
    @NotNull
    private String identityNumber;

    @NotBlank(message = "email is required")
    @Email(message = "email format is not valid")
    private String email;

    @NotBlank(message = "password is required")
    @NotNull
    private String password;

    @NotNull
    private Role role;

}
