package ar.com.mercadolibre.ipgeolocation.client;

import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.response.external.IpGeolocationInfoExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
 name = ExternalRoute.IP_GEOLOCATION_CLIENT_NAME,
 url="${ip.geolocation.base-url}"
)
public interface IpGeolocationClient {

    @GetMapping(value= ExternalRoute.IP_GEOLOCATION_GET_INFO, produces = MediaType.APPLICATION_JSON_VALUE)
    IpGeolocationInfoExternalResponse getIpGeolocationInfo(@RequestParam(HttpConstant.QUERY_PARAM_API_KEY) String apikey,
                                                           @RequestParam(HttpConstant.PARAM_IP) String ip);

}
