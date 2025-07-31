package com.iceAge.server.auth.infrastructure.repository;

import com.iceAge.server.auth.domain.model.User;
import com.iceAge.server.auth.domain.model.SocialProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialProviderAndProviderId(SocialProvider provider, String providerId);
}
