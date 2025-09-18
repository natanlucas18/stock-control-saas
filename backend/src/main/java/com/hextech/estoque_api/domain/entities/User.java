package com.hextech.estoque_api.domain.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
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

    public User() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return enabled == user.enabled && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, enabled);
    }
}
