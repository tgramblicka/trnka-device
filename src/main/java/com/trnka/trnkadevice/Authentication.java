package com.trnka.trnkadevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trnka.trnkadevice.domain.User;
import com.trnka.trnkadevice.repository.UserRepository;
import com.trnka.trnkadevice.ui.UserSession;

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
        User user = userRepository.findByCode(code);
        if (user == null) {
            return false;
        }
        userSession.setUserId(user.getId());
        userSession.setUsername(user.getUsername());
        return true;
    }

}
