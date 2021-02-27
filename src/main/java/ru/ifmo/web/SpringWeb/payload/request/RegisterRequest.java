package ru.ifmo.web.SpringWeb.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Enter username")
    private String username;

    @NotEmpty(message = "Enter password")
    @Length(min = 6, message = "Must be greater than 6")
    private String password;
}
