package dev.makos.accounts.exception.handler;

import dev.makos.accounts.domain.dto.ErrorResponseDto;
import dev.makos.accounts.exception.BadRequestException;
import dev.makos.accounts.exception.CustomerAlreadyExistsException;
import dev.makos.accounts.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, WebRequest webRequest) {
        Map<String, String> errors = ex.getFieldErrors().stream()
                .collect(toMap(FieldError::getField, error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Unknown error")));

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .httpMethod(((ServletWebRequest) webRequest).getHttpMethod().toString())
                .errorCode(HttpStatus.BAD_REQUEST)
                .errorMessage(errors.toString())
                .errorTime(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception e, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = getErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, e, webRequest);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = getErrorResponseDto(HttpStatus.BAD_REQUEST, e, webRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = getErrorResponseDto(HttpStatus.NOT_FOUND, e, webRequest);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(BadRequestException e, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = getErrorResponseDto(HttpStatus.BAD_REQUEST, e, webRequest);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    private static ErrorResponseDto getErrorResponseDto(HttpStatus status, Exception e, WebRequest webRequest) {
        return ErrorResponseDto.builder()
                .apiPath(webRequest.getDescription(false))
                .httpMethod(((ServletWebRequest) webRequest).getHttpMethod().toString())
                .errorCode(status)
                .errorMessage(e.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
    }
}
