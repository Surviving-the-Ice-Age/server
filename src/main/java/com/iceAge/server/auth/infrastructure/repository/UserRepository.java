package com.iceAge.server.auth.infrastructure.repository;

import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.domain.model.SocialProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository userJpaRepository;

    public User save(User user) {
        return userJpaRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }
}
