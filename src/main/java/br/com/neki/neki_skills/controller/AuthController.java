package br.com.neki.neki_skills.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.neki.neki_skills.dto.CredenciaisLoginDTO;
import br.com.neki.neki_skills.entity.User;
import br.com.neki.neki_skills.security.JWTUtil;
import br.com.neki.neki_skills.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    // Injecting Dependencies
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

   
    @PostMapping("/registro")
    public Map<String, Object> registerHandler(@RequestBody User user){
        
        String encodedPass = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPass);

        user = userService.saveUser(user);

            
        User userResumido = new User();
        userResumido.setUserLogin(user.getUserLogin());
        userResumido.setId(user.getId());
        String token = jwtUtil.generateTokenWithUserData(userResumido);

        return Collections.singletonMap("jwt-token", token);
    }

    // Login de usuario
    @PostMapping("/login")
    public Map<String, Object> loginHandler
    	(@RequestBody CredenciaisLoginDTO credenciaisLoginDTO){
        try {
            
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(credenciaisLoginDTO.getUserLogin(), 
                    		credenciaisLoginDTO.getUserPassword());

            
            authManager.authenticate(authInputToken);


            User user = userService.findByLogin(credenciaisLoginDTO.getUserLogin());
            User userResumido = new User();
            userResumido.setId(user.getId());
            userResumido.setUserLogin(user.getUserLogin());
            userResumido.setLastLogin(user.getLastLogin());
            System.out.println(userResumido);
            
            String token = jwtUtil.generateTokenWithUserData(userResumido);
 
            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Credenciais Invalidas");
        }
    }
}