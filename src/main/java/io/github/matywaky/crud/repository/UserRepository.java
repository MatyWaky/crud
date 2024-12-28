package io.github.matywaky.crud.repository;

import io.github.matywaky.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link User} entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // No additional methods are required here as JpaRepository provides built-in methods for CRUD operations.
}
