package ar.com.mercadolibre.ipgeolocation.client;

import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.response.external.apiLayer.CurrencyExchangeRateExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
 name = ExternalRoute.API_LAYER_CLIENT_NAME,
 url="${api.layer.base-url}"
)
public interface ApiLayerClient {

    @GetMapping(value= ExternalRoute.API_LAYER_GET_RATES, produces = MediaType.APPLICATION_JSON_VALUE)
    CurrencyExchangeRateExternalResponse getCurrencyExchangeRates(@RequestParam(HttpConstant.QUERY_PARAM_BASE) String foreignCurrency,
                                                                  @RequestParam(HttpConstant.QUERY_PARAM_SYMBOLS) String currency);

}
