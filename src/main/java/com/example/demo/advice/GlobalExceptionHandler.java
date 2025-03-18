package com.example.demo.advice;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecipeNotFoundException;
import com.example.demo.exception.RecipeResponse;
import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<RecipeResponse> handleRecipeNotFoundException(RecipeNotFoundException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(recipeResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RecipeResponse> handleBadRequestException(BadRequestException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(recipeResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RecipeResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(recipeResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RecipeResponse> handleException(Exception e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred.",
                System.currentTimeMillis()
        );
        logger.error("An unexpected error occurred: {}", e.getMessage(), e);

        return new ResponseEntity<>(recipeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
