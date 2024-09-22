package com.esphere.EventSphere.service;

import com.esphere.EventSphere.model.User;
import com.esphere.EventSphere.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();


        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorityList);
    }
}
