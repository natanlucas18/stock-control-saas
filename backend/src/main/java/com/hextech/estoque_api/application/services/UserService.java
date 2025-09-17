package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.users.UserResponseDTO;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.exceptions.UserAlreadyExistsException;
import com.hextech.estoque_api.domain.entities.Company;
import com.hextech.estoque_api.domain.entities.Role;
import com.hextech.estoque_api.domain.entities.User;
import com.hextech.estoque_api.infrastructure.repositories.RoleRepository;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username);
        if (user.isEmpty() || !user.get().isEnabled())
            throw new UsernameNotFoundException("Usuário " + username + " não encontrado ou desabilitado.");
        else return user.get();
    }

    @Transactional
    public UserResponseDTO createNewUser(UserRequestDTO requestDTO, Long companyId) {
        repository.findByEmail(requestDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Já existe um usuário com este email.");
                });

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada."));

        List<Role> roles = roleRepository.findAllById(requestDTO.getRolesId());
        if (roles.size() != requestDTO.getRolesId().size()) {
            throw new ResourceNotFoundException("Uma ou mais funções não foram encontradas.");
        }

        var user = User.createNewUser(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword(), company, roles, passwordEncoder);

        user = repository.save(user);

        return new UserResponseDTO(user);
    }
}
