package ar.com.mercadolibre.ipgeolocation.client;

import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi.LanguagesExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
 name = ExternalRoute.IP_API_CLIENT_NAME,
 url="${ip.api.base-url}"
)
public interface IpApiClient {

    @GetMapping(value= ExternalRoute.IP_API_GET_INFO, produces = MediaType.APPLICATION_JSON_VALUE)
    LanguagesExternalResponse getLanguages(@PathVariable(HttpConstant.PARAM_IP) String ip,
                                           @RequestParam(HttpConstant.QUERY_PARAM_ACCESS_KEY) String apikey);

}
