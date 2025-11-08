package com.example.MatchUp.Controller;

import com.example.MatchUp.JWT.JwtUtil;
import com.example.MatchUp.Kafka.UserDeletedProducer;
import com.example.MatchUp.Model.LoginRequest;
import com.example.MatchUp.Model.RegisterRequest;
import com.example.MatchUp.Model.User;
import com.example.MatchUp.Model.UserIdAndPhone;
import com.example.MatchUp.Repository.UserRepository;
import com.example.MatchUp.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private UserRepository userRepository;
    private final UserDeletedProducer producer;
    private JwtUtil jwtUtil;

    public AuthController(AuthService authService, UserRepository userRepository, UserDeletedProducer producer, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.producer = producer;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        Map<String, String> response = new HashMap<>();

        try{
            authService.register(request.getEmail(), request.getPhone(), request.getPassword());
        }
        catch (Exception e){
            response.put("message", "failed to register user" + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {

        Map<String, String> response = new HashMap<>();
        String token = "";

        try{
            token = authService.login(request.getEmail(), request.getPassword());
        }catch (Exception e){
            response.put("message", "failed to login user" + e.getMessage());
        }

        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String authHeader) {

        if(!authHeader.startsWith("Bearer ") || authHeader == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = getUserIdFromEmail(email);

        boolean deleted = authService.deleteUser(userId);

        if(deleted){
            producer.sendUserDeletedEvent(userId);
            return ResponseEntity.ok("user deleted successfully");
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    public Long getUserIdFromEmail(String email) {
        return userRepository.findIdByEmail(email);
    }

    @GetMapping("/userId")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email){
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(user.getId()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phoneNumber")
    public ResponseEntity<String> getPhoneNumberByEmail(@RequestParam String email){
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(user.getPhone()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/IdPhone")
    public ResponseEntity<List<UserIdAndPhone>> getIdPhone(){
        List<UserIdAndPhone> userIdAndPhones =  userRepository.findAllIdAndPhone();

        System.out.println(userIdAndPhones);

        if(userIdAndPhones.size()>0){
            return ResponseEntity.ok(userIdAndPhones);
        }
        return ResponseEntity.notFound().build();
    }

}
