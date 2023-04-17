package ar.com.mercadolibre.ipgeolocation.common.exception;

import org.springframework.http.HttpStatus;

public class IpGeolocationException extends RuntimeException {
    private String code;
    private HttpStatus status;
    private Throwable cause;

    public IpGeolocationException(String message, Throwable cause, String code, HttpStatus status) {
        super(message, cause);
        this.code = code;
        this.status = status;
        this.cause = cause;
    }

    public IpGeolocationException(String code, HttpStatus status, String message ) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public IpGeolocationException() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
