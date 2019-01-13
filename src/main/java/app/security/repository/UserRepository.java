package app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.security.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
