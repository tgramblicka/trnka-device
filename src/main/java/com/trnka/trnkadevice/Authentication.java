package com.trnka.trnkadevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.ui.UserSession;

import java.util.Optional;

@Service
public class Authentication {

    private UserRepository userRepository;
    private UserSession userSession;

    @Autowired
    public Authentication(final UserRepository userRepository,
                          final UserSession userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public Boolean authenticate(String code) {
        Optional<User> userOptional = userRepository.findByCode(code);
        if (!userOptional.isPresent()) {
            return false;
        }
        User user = userOptional.get();
        userSession.setUserId(user.getId());
        userSession.setUsername(user.getUsername());
        user.setLoginCount(user.getLoginCount()+1);
        userRepository.save(user);
        return true;
    }

}
