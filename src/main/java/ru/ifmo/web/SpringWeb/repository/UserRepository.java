package ru.ifmo.web.SpringWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.web.SpringWeb.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
