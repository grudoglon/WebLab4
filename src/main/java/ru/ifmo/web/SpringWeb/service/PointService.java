package ru.ifmo.web.SpringWeb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.web.SpringWeb.model.Point;
import ru.ifmo.web.SpringWeb.model.User;
import ru.ifmo.web.SpringWeb.repository.PointRepository;
import ru.ifmo.web.SpringWeb.repository.UserRepository;

import java.util.List;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public PointService(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Point> getPointsByUser(User user) {
        return pointRepository.findAllByUser(user);
    }

    @Transactional
    public List<Point> getPointsByUsername(String username) {
        return pointRepository.findAllByUser(userRepository.findByUsername(username));
    }

    @Transactional
    public void deletePointsByUser(User user) {
        pointRepository.deleteAllByUser(user);
    }

    @Transactional
    public void addPoint(Point point) {
        pointRepository.save(point);
    }
}
