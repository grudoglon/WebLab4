package ru.ifmo.web.SpringWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ifmo.web.SpringWeb.model.Point;
import ru.ifmo.web.SpringWeb.model.User;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByUser(User user);
    void deleteAllByUser(User user);
    void deleteById(Long id);

}
