package com.project.MockInterviewScheduler.security;

import com.project.MockInterviewScheduler.entity.CustomUser;
import com.project.MockInterviewScheduler.exceptions.ResourceNotFoundException;
import com.project.MockInterviewScheduler.repository.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final CustomUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user =  userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("No such user exists"));
        if(!user.isActive()){
            throw new UsernameNotFoundException("User account is deactivated");
        }
        return user;
    }
}
