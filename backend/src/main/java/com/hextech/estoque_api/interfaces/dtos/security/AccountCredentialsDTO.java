package com.hextech.estoque_api.interfaces.dtos.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsDTO {

    @NotBlank(message = "Nome do usuário não pode ser nulo ou vazio.")
    @Email(message = "Email inválido.")
    private String username;
    @NotBlank(message = "Senha não pode ser nula ou vazia.")
    private String password;
}
