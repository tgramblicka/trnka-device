package com.trnka.trnkadevice.ui;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.UserRepository;

import lombok.Data;

@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@Data
public class UserSession {
    private UserRepository userRepository;

    @Autowired
    public UserSession(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Long userId;
    private String username;

    public void logout() {
        this.userId = null;
        this.username = null;
    }

    public Optional<User> getUser() {
        return userRepository.findById(userId);
    }

}
