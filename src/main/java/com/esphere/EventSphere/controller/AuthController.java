package com.esphere.EventSphere.controller;

import com.esphere.EventSphere.config.JwtProvider;
import com.esphere.EventSphere.model.User;
import com.esphere.EventSphere.repository.UserRepository;
import com.esphere.EventSphere.response.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

//    AuthController(){
//        System.out.println("Hii");
//    }

    @Autowired
    private UserRepository userRepository;


    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);




    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExists = userRepository.findByEmail(user.getEmail());

        if(isEmailExists!=null){
            throw new Exception("Email already registered with other account");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(encoder.encode(user.getPassword()));

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

    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception{

        User isEmailExists = userRepository.findByEmail(user.getEmail());

        if(isEmailExists==null){
            throw new Exception("Email ID provided is not register, please signup.");
        }

        User savedUser = isEmailExists;

        String ProvidedPassword = user.getPassword();
        System.out.println(ProvidedPassword);
        System.out.println(savedUser.getPassword());

        if(!encoder.matches(ProvidedPassword, savedUser.getPassword())){
            throw new BadCredentialsException("Invalid credentials!");
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();

        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successful");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
}
