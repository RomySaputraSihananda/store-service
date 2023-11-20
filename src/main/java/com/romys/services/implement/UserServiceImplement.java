package com.romys.services.implement;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.romys.enums.Role;
import com.romys.models.UserModel;
import com.romys.payloads.hit.ElasticHit;
import com.romys.services.UserService;

import lombok.Getter;

@Service
public class UserServiceImplement implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return new UserDetailsImplement(this.userService.getUserByUsername(username));
        } catch (IOException e) {
            throw new UsernameNotFoundException("username not found");
        }
    }

    @Getter
    public class UserDetailsImplement implements UserDetails {
        private String id;
        private Role role;
        private String usermail;
        private String username;
        private String password;

        public UserDetailsImplement(ElasticHit<UserModel> hit) {
            this.id = hit.id();
            this.role = hit.source().getRole();
            this.username = hit.source().getUsername();
            this.password = hit.source().getPassword();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(this.role.getValue()));
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
    }

}
