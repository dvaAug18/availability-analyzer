package ru.dva.vl.dto;

import java.time.LocalDateTime;

public record LogRow (LocalDateTime time, char httpStatusStartWith, double duration) {

}
