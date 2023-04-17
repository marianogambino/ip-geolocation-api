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

public abstract class MockIpApiClient {

    public static void getLanguages(HttpStatusCode httpStatusCode, String IP, String response, MockServerClient mockServer){
        mockServer.when(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(ExternalRoute.IP_API_GET_INFO)
                        .withPathParameter(HttpConstant.PARAM_IP, IP)
                        .withQueryStringParameter(HttpConstant.QUERY_PARAM_ACCESS_KEY, "7dd289edaf87a821cd8fe2f5774f6655")
        )
        .respond(
            HttpResponse.response().withStatusCode(httpStatusCode.code())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(response, StandardCharsets.UTF_8)
        );
    }
}
