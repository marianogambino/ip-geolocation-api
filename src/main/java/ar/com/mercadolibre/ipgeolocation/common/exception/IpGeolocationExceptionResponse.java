package ar.com.mercadolibre.ipgeolocation.common.exception;

public class IpGeolocationExceptionResponse {
    private String code;
    private String message;
    private String cause;

    public IpGeolocationExceptionResponse(String code, String message, String cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public IpGeolocationExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
