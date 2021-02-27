package ru.ifmo.web.SpringWeb.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.web.SpringWeb.model.Point;
import ru.ifmo.web.SpringWeb.model.User;
import ru.ifmo.web.SpringWeb.payload.request.PointAddRequest;
import ru.ifmo.web.SpringWeb.payload.response.ErrorResponse;
import ru.ifmo.web.SpringWeb.payload.response.MessageResponse;
import ru.ifmo.web.SpringWeb.service.PointService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("points")
public class PointController {

    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity<?> get(Authentication authentication) {
        List<Point> pointList = pointService.getPointsByUser((User) authentication.getPrincipal());
        if(pointList != null){
            pointList.sort((left, right) -> (int) (right.getId() - left.getId()));
//            Collections.reverse(pointList);

            Map<String, Object> out = new HashMap<>();
            out.put("total", pointList.size());
            out.put("points", pointList);
            return ResponseEntity.ok(out);
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("Have some problems"));
    }


    @RequestMapping(value = "/remove-all", method = RequestMethod.GET)
    public ResponseEntity<?> remove(Authentication authentication) {
        pointService.deletePointsByUser((User) authentication.getPrincipal());
        return ResponseEntity.ok(new MessageResponse("All points removed"));
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@Valid @RequestBody PointAddRequest request, Authentication authentication) {
        Point point = new Point(request.getX(), request.getY(), request.getR());
        point.setUser((User) authentication.getPrincipal());
        pointService.addPoint(point);

        Map<String, Object> out = new HashMap<>();
        out.put("result", true);
        out.put("data", point);

        return ResponseEntity.ok(out);
//        return ResponseEntity.ok(new MessageResponse("New point added"));
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errorMsg = exception.getFieldErrors().
                stream().
                collect(
                        Collectors.toMap(
                                FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                (a1, a2) -> a1
                        ));

        return ErrorResponse.builder().error(errorMsg).build();
    }

}
