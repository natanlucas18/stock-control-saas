package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username);
        if (user.isEmpty() || !user.get().isEnabled())
            throw new UsernameNotFoundException("Usuário " + username + "não encontrado ou desabilitado.");
        else return user.get();
    }
}
