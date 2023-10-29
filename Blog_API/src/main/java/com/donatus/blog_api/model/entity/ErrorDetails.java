package com.donatus.blog_api.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorDetails {
    private String message;
    private HttpStatus status;
    private String debugMessage;
    private LocalDateTime dateTime;
}
