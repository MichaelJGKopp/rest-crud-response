package com.example.demo.advice;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.RecipeNotFoundException;
import com.example.demo.exception.RecipeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Global exception handler for the Recipe Management REST Service.
 * Handles various exceptions and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handles RecipeNotFoundException.
     * Occurs when a requested recipe cannot be found in the database.
     *
     * @param e The exception containing the error message
     * @return ResponseEntity with 404 NOT_FOUND status and error details
     */
    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<RecipeResponse> handleRecipeNotFoundException(RecipeNotFoundException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(recipeResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles BadRequestException.
     * Occurs when the request contains invalid parameters or is malformed.
     *
     * @param e The exception containing the error message
     * @return ResponseEntity with 400 BAD_REQUEST status and error details
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<RecipeResponse> handleBadRequestException(BadRequestException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(recipeResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentTypeMismatchException.
     * Occurs when method argument is not the expected type.
     *
     * @param e The exception containing the type mismatch details
     * @return ResponseEntity with 400 BAD_REQUEST status and error details
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RecipeResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(recipeResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions.
     * Logs the exception details and returns a generic error message.
     *
     * @param e The exception that was thrown
     * @return ResponseEntity with 500 INTERNAL_SERVER_ERROR status and generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RecipeResponse> handleException(Exception e) {
        RecipeResponse recipeResponse = new RecipeResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Please try again. An unexpected error occurred.",
                System.currentTimeMillis()
        );
        logger.error("An unexpected error occurred: {}", e.getMessage(), e);

        return new ResponseEntity<>(recipeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}