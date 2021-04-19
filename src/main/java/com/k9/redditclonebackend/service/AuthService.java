package com.k9.redditclonebackend.service;

import com.k9.redditclonebackend.dto.RegisterRequest;
import com.k9.redditclonebackend.model.User;
import com.k9.redditclonebackend.model.VerificationToken;
import com.k9.redditclonebackend.repository.UserRepository;
import com.k9.redditclonebackend.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user =new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token= UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
