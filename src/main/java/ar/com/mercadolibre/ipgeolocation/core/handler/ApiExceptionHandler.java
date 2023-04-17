package ar.com.mercadolibre.ipgeolocation.core.handler;

import ar.com.mercadolibre.ipgeolocation.common.exception.IpGeolocationException;
import ar.com.mercadolibre.ipgeolocation.common.exception.IpGeolocationExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

     @ExceptionHandler(IpGeolocationException.class)
     public ResponseEntity<IpGeolocationExceptionResponse> apiClientException(
             IpGeolocationException exception, WebRequest request){

        IpGeolocationExceptionResponse ipGeolocationExceptionResponse =
                new IpGeolocationExceptionResponse(exception.getCode(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(ipGeolocationExceptionResponse);
     }

     @ExceptionHandler(Exception.class)
     public ResponseEntity<IpGeolocationExceptionResponse> exception(Exception exception, WebRequest request){
        IpGeolocationExceptionResponse ipGeolocationExceptionResponse
                = new IpGeolocationExceptionResponse("api.internal.server_error", exception.getMessage() );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ipGeolocationExceptionResponse);
     }


}
