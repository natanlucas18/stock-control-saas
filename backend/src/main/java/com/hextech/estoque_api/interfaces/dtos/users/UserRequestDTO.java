package com.hextech.estoque_api.interfaces.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Nome do usuário não pode ser nulo ou vazio.")
    @Size(min = 3, message = "Nome do usuário deve ter no mínimo 3 caracteres.")
    private String name;
    @Email(message = "Email inválido.")
    private String email;
    @NotBlank(message = "Senha não pode ser nula ou vazia.")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres.")
    private String password;
    @NotNull(message = "Permissões não pode ser nula")
    private List<Long> rolesId;
}