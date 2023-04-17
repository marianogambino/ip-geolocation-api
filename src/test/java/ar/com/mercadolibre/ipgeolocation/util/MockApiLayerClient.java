package ar.com.mercadolibre.ipgeolocation.util;

import ar.com.mercadolibre.ipgeolocation.common.constants.HttpConstant;
import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

public abstract class MockApiLayerClient {

    public static void getCurrencyExchangeRates(HttpStatusCode httpStatusCode, String currencyBase,
                                                String currencyTarget, String response, MockServerClient mockServer){
        mockServer.when(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(ExternalRoute.API_LAYER_GET_RATES)
                        .withQueryStringParameter(HttpConstant.QUERY_PARAM_BASE, currencyBase)
                        .withQueryStringParameter(HttpConstant.QUERY_PARAM_SYMBOLS, currencyTarget)
                        .withHeader(HttpConstant.QUERY_PARAM_API_KEY, "D6V2ZyOfnx2Fkc1nL7z79PpC3N1ShBOl")
        )
        .respond(
            HttpResponse.response().withStatusCode(httpStatusCode.code())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(response, StandardCharsets.UTF_8)
        );
    }
}
