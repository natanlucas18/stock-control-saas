package com.hextech.estoque_api.domain.entities.user;

import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.role.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    private List<Role> roles;

    private User(String name, String email, String password, Company company, List<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.company = company;
        this.enabled = true;
        this.roles = roles;
    }

    public static User createNewUser(String name, String email, String password, Company company, List<Role> roles, PasswordEncoder passwordEncoder) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        if (company == null) throw new IllegalArgumentException("Empresa não pode ser nula.");
        if (roles.isEmpty()) throw new IllegalArgumentException("Usuário deve ter pelo menos uma função.");

        password = passwordEncoder.encode(password);
        return new User(name, email, password, company, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public List<String> getRoleNames() {
        return roles.stream().map(Role::getAuthority).toList();
    }
}
