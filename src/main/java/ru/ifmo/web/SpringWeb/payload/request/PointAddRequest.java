package ru.ifmo.web.SpringWeb.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class PointAddRequest {

    @NotNull(message = "Enter X value")
    @Min(value = -4, message = "Must be from -4 to 4") @Max(value = 4, message = "Must be from -4 to 4")
    private Integer x;

    @NotNull(message = "Enter Y value")
    @Min(value = -5, message = "Must be from -5 to 3") @Max(value = 3, message = "Must be from -5 to 3")
    private Double y;

    @NotNull(message = "Enter R value")
    @Min(value = 1, message = "Must be from 1 to 4") @Max(value = 4, message = "Must be from 1 to 4")
    private Integer r;
}
