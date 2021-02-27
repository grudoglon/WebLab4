package ru.ifmo.web.SpringWeb.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
