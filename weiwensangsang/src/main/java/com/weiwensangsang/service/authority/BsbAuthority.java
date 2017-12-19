package com.weiwensangsang.service.authority;

import com.weiwensangsang.domain.User;
import com.weiwensangsang.repository.UserRepository;
import com.weiwensangsang.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BsbAuthority {

    private final Logger log = LoggerFactory.getLogger(BsbAuthority.class);

    @Autowired
    private UserRepository userRepository;

    public User current() {
        User now = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin())
            .map(user -> user)
            .orElseGet(() -> User.Anonymous());
        return now;
    }

}

