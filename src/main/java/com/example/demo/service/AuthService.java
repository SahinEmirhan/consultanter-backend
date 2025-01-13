package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.example.demo.service.PasswordUtil.verifyPassword;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public boolean login(String email, String password, HttpSession session) throws NoSuchAlgorithmException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hash = user.getHash();
            String salt = user.getSalt();


            if (verifyPassword(password, salt, hash)) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("userType", user.getUserType());


                return true;
            }
        }
        return false;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }
}
