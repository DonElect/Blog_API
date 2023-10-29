package com.donatus.blog_api.exception;

import com.donatus.blog_api.model.entity.ErrorDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ErrorDetails> handleInvalidEmailException(final InvalidEmailException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check email and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ErrorDetails> handleEmailAlreadyExistException(final EmailAlreadyExistException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Register with a new email or login.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorDetails> handleWrongPasswordException(final WrongPasswordException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check password and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCommentNotFoundException(final CommentNotFoundException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check the comment ID and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorDetails> handlePostNotFoundException(final PostNotFoundException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check the post ID and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(final UserNotFoundException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .debugMessage("Check the user ID and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationFailedException(final AuthenticationFailedException exception){
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .debugMessage("Your session has expired. Login and try again.")
                .dateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String[] errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray(String[]::new);
        ErrorDetails response = ErrorDetails.builder()
                .message("Invalid input(s)")
                .status(HttpStatus.NOT_ACCEPTABLE)
                .debugMessage(Arrays.toString(errors))
                .dateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
