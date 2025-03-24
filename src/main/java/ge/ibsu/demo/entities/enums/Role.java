package ge.ibsu.demo.entities.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    CUSTOMER_ADMIN(Arrays.asList(Permission.CUSTOMER_READ, Permission.CUSTOMER_ADD)),
    CUSTOMER_READ(Arrays.asList(Permission.CUSTOMER_READ));;

    private List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> perms = permissions.stream().map(
                    i -> new SimpleGrantedAuthority(i.getKeyword()
                )).collect(Collectors.toList());

        perms.add(new SimpleGrantedAuthority(name()));

        return perms;
    }
}
