package com.esphere.EventSphere.controller;

import com.esphere.EventSphere.config.JwtProvider;
import com.esphere.EventSphere.model.User;
import com.esphere.EventSphere.repository.UserRepository;
import com.esphere.EventSphere.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

//    AuthController(){
//        System.out.println("Hii");
//    }

    @Autowired
    private UserRepository userRepository;




    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {



        User isEmailExists = userRepository.findByEmail(user.getEmail());

//        if(isEmailExists!=null){
//            throw new Exception("Email already registered with other account");
//        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);
        System.out.println(jwt);

        AuthResponse res = new AuthResponse();

        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Registration successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}
