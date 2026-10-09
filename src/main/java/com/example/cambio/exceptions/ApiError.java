package com.example.cambio.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiError (
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestam,
        Integer code,
        String status,
        String message,
        String path
){}