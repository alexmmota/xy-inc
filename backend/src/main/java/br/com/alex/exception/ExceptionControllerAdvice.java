package br.com.alex.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Falha interna no sistema. Se persistir o erro procurar o administrador";
        LOGGER.error("M=exceptionHandler", ex);
        return new ResponseEntity<String>(message, httpStatus);
    }

    @ExceptionHandler({JsonParseException.class, JsonMappingException.class, InvalidFormatException.class})
    public ResponseEntity<?> exceptionHandlerJSON(Exception ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message = "Payload Inválido";
        LOGGER.error("M=exceptionHandlerJSON", ex);
        return new ResponseEntity<String>(message, httpStatus);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> exceptionHandlerNoContent(EntityNotFoundException ex) {
        LOGGER.error("M=exceptionHandlerNoContent", ex);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> constraintViolationExceptionHandle(MethodArgumentNotValidException ex) {
        List<String> messages = new ArrayList<>();

        BindingResult bindingResult = ex.getBindingResult();
        if(bindingResult!=null && bindingResult.hasErrors()){
            List<FieldError> fieldErrorList= bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrorList ){
                messages.add(fieldError.getDefaultMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body(messages);
    }

}
