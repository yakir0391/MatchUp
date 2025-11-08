package com.example.MatchUp.Service;

import com.example.MatchUp.JWT.JwtUtil;
import com.example.MatchUp.Model.User;
import com.example.MatchUp.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.jwtUtil = jwtUtil;
    }

    public User register(String email, String phone, String password){
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(hashedPassword);
        return userRepo.save(user);
    }

    public String login(String email, String password){
        return userRepo.findByEmail(email)
                .filter(user -> bCryptPasswordEncoder.matches(password, user.getPasswordHash()))
                .map(user -> jwtUtil.generateJwtToken(user.getEmail()))
                .orElse(null);
    }

    public boolean deleteUser(Long userId) {

        if(!userRepo.existsById(userId)) {
            return false;
        }

        userRepo.deleteById(userId);
        return true;
    }
}
