package com.example.aftas_back.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.aftas_back.domain.enums.PermissionType.*;

@RequiredArgsConstructor
public enum Role {
    MANAGER(
            Set.of( VIEW_COMPETITIONS,
                    CREATE_COMPETITIONS,
                    UPDATE_COMPETITIONS,
                    DELETE_COMPETITIONS,
                    VIEW_USERS,
                    CREATE_USERS,
                    UPDATE_USERS,
                    DELETE_USERS,
                    VIEW_RANKING,
                    CREATE_HUNTING,
                    UPDATE_HUNTING,
                    DELETE_HUNTING
            )
    ),
    JURY(
            Set.of(VIEW_COMPETITIONS,
                    CREATE_COMPETITIONS,
                    UPDATE_COMPETITIONS,
                    CREATE_HUNTING,
                    UPDATE_HUNTING,
                    DELETE_HUNTING
                    )
    ),
    MEMBER(
            Set.of(VIEW_COMPETITIONS,
                    VIEW_RANKING
                    )
    );

    @Getter
    private final Set<PermissionType> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
