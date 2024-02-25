package com.example.aftas_back.domain;

import com.example.aftas_back.domain.enums.IdentityDocumentType;
import com.example.aftas_back.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String familyName;
    private LocalDateTime accessionDate;
    private String nationality;
    @Enumerated( EnumType.STRING )
    private IdentityDocumentType identityDocumentType;
    private String identityNumber;
    private String email;
    private String password;
    private boolean status;


    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long currentUserId) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public boolean isStatus() {
        return status;
    }
}
