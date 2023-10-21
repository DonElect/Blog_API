package com.donatus.fashion_blog_api.config;

import com.donatus.fashion_blog_api.entity.Roles;
import com.donatus.fashion_blog_api.entity.UserEntity;
import com.donatus.fashion_blog_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServices implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity users = userRepo.findUserEntityByEmail(email)
                                        .orElseThrow(() -> new UsernameNotFoundException("User not Found"));

        return new User(users.getEmail(), users.getPassword(), mapRolesToAuthorities(users.getRoles()));
    }

    public Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
