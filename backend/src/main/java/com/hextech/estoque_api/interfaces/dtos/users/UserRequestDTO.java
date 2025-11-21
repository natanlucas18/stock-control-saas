package com.hextech.estoque_api.interfaces.dtos.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRequestDTO {

    private String name;
    private String email;
    private String password;
    private List<Long> rolesId;
}