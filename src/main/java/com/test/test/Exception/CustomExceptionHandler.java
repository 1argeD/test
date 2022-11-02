package com.test.test.Exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.test.Login.Dto.SignupResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public SignupResponseDto handlerRequestException(BadRequestException badRequestException) {
        return new SignupResponseDto(badRequestException.getMessage(), false);
    }
    /*토큰이 올바르지 않음*/
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?>unsupportedJwtException(UnsupportedJwtException jwtException) {
        String error = jwtException.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("code", "403", "msg", error));
    }
    /*토큰 시간이 만료됨*/
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?>ExpiredJwt() {
        String error = "토큰이 만료되었습니다";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "401", "msg", error));
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<?> handlerIllegalAccessException(IllegalAccessException exception) {
        String error = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code","400", "msg", error));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?>RuntimeException(RuntimeException exception) {
        String error = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", "400", "msg", error));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> IOException(IOException exception) {
        String error = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code","400","msg", error));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException exception) {
        String error = exception.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("code", "403", "msg", error));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> JsonProcessingException(JsonProcessingException exception) {
        String error = exception.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code","400", "msg", error));
    }
}
