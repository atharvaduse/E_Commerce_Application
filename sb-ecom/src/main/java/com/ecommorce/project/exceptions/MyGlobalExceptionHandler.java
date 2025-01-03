package com.ecommorce.project.exceptions;


import aj.org.objectweb.asm.commons.FieldRemapper;
import com.ecommorce.project.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//It is global exception handler
@RestControllerAdvice   // This will intercept any exception that are thrown by any controller in application
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException exception){
       Map<String,String> response = new HashMap<>();

       // getBindingResult() - this method will give list of errors get during validation of method parameters
        exception.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
    }

@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<APIResponse> myResourceNotFoundException(ResourceNotFoundException e){

String message = e.getMessage();
APIResponse apiResponse = new APIResponse(message,false);

return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
}


    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> myAPIException(APIException e){

        String message = e.getMessage();
        APIResponse apiResponse =  new APIResponse(message,false);

        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }


}