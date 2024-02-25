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
    @NotBlank
    @NotNull(message = "firstname is required")
    private String name;

    @NotBlank
    @NotNull(message = "lastname is required")
    private String familyName;

    @NotBlank
    @NotNull(message = "nationality is required")
    private String nationality;

    @NotNull(message = "identityDocumentType is required")
    private IdentityDocumentType identityDocumentType;

    @NotNull(message = "identityNumber is required")
    private String identityNumber;

    @Email(message = "email format is not valid")
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String confirmedPassword;


}
