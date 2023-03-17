package br.com.neki.neki_skills.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.neki.neki_skills.entity.User;
import br.com.neki.neki_skills.repository.UserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired 
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Optional<User> userRes = userRepo.findByUserLogin(userLogin);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Não foi possível encontrar usuário  = " + userLogin);

        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                userLogin,
                user.getUserPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))); 
    }
}