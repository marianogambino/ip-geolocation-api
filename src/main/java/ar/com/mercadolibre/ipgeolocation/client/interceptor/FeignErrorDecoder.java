package ar.com.mercadolibre.ipgeolocation.client.interceptor;

import ar.com.mercadolibre.ipgeolocation.common.exception.ErrorCode;
import ar.com.mercadolibre.ipgeolocation.common.exception.ErrorMessage;
import ar.com.mercadolibre.ipgeolocation.common.exception.IpGeolocationException;
import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    return handleError(methodKey, response);
  }

  private Exception handleError(String methodKey, Response response) {
    String method = Optional.ofNullable(methodKey).map(m -> StringUtils.substringBefore(m, "#")).orElseGet(String::new);
    HttpStatus status = mapHttStatus(response.status());
    if(method.equals(ExternalRoute.IP_GEOLOCATION_CLIENT_NAME)) {
      return new IpGeolocationException(ErrorCode.IP_GEOLOCATION_CLIENT_ERROR, status, ErrorMessage.IP_GEOLOCATION_CLIENT_ERROR);
    }
    if(method.equals(ExternalRoute.IP_API_CLIENT_NAME)) {
      return new IpGeolocationException(ErrorCode.IP_API_CLIENT_CLIENT_ERROR, status, ErrorMessage.IP_API_CLIENT_ERROR);
    }
    if(method.equals(ExternalRoute.API_LAYER_CLIENT_NAME)) {
      return new IpGeolocationException(ErrorCode.API_LAYER_CLIENT_ERROR, status, ErrorMessage.API_LAYER_CLIENT_ERROR);
    }
    else {
      return new IpGeolocationException(ErrorCode.INTERNAL_SERVER_ERROR, status, ErrorMessage.INTERNAL_SERVER_ERROR);
    }
  }

  private HttpStatus mapHttStatus(int statusExternal) {
    if((HttpStatus.BAD_GATEWAY.value() == statusExternal) || (HttpStatus.SERVICE_UNAVAILABLE.value() == statusExternal)
        || (HttpStatus.GATEWAY_TIMEOUT.value() == statusExternal) || (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusExternal)) {
      return HttpStatus.FAILED_DEPENDENCY;
    }
    return HttpStatus.valueOf(statusExternal);
  }

}
