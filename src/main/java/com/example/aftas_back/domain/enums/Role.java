package com.example.aftas_back.domain.enums;


import jakarta.persistence.AssociationOverride;
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
            Set.of( VIEW_COMPETITIONS, CREATE_COMPETITIONS, UPDATE_COMPETITIONS, DELETE_COMPETITIONS,
                    VIEW_USERS, CREATE_USERS, UPDATE_USERS, DELETE_USERS,
                    VIEW_RANKING, CREATE_RANKING, UPDATE_RANKING, DELETE_RANKING,
                    VIEW_HUNTING, CREATE_HUNTING, UPDATE_HUNTING, DELETE_HUNTING,
                    VIEW_FISHES, CREATE_FISHES, UPDATE_FISHES, DELETE_FISHES,
                    VIEW_LEVEL, CREATE_LEVEL, UPDATE_LEVEL, DELETE_LEVEL,
                    ENABLE_ACCOUNT
            )
    ),
    JURY(
            Set.of( VIEW_COMPETITIONS, CREATE_COMPETITIONS, UPDATE_COMPETITIONS,
                    VIEW_USERS,
                    VIEW_RANKING, CREATE_RANKING, UPDATE_RANKING,
                    VIEW_HUNTING, CREATE_HUNTING, UPDATE_HUNTING, DELETE_HUNTING,
                    VIEW_FISHES, CREATE_FISHES, UPDATE_FISHES,
                    VIEW_LEVEL, CREATE_LEVEL, UPDATE_LEVEL
            )
    ),
    MEMBER(
            Set.of( VIEW_COMPETITIONS,
                    VIEW_RANKING,
                    CREATE_RANKING
            )
    );

    @Getter
    private final Set<PermissionType> permissions;



    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permissionType -> new SimpleGrantedAuthority(permissionType.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

}
