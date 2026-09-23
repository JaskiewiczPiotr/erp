package com.piogrammer.erp.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;



    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }

    public void register(RegisterRequest request){

        AppUser user = new AppUser();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);
    }
    public LoginResponse login(LoginRequest request){

        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!matches){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(token);
    }



}