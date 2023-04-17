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

public abstract class MockIpGeolocationClient {

    public static void getIpGeolocationInfo(HttpStatusCode httpStatusCode, String IP, String response, MockServerClient mockServer){
        mockServer.when(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(ExternalRoute.IP_GEOLOCATION_GET_INFO)
                        .withQueryStringParameter(HttpConstant.PARAM_IP, IP)
                        .withQueryStringParameter(HttpConstant.QUERY_PARAM_API_KEY, "44c11c25937a47f8bee071e16a3d67fd")
        )
        .respond(
            HttpResponse.response().withStatusCode(httpStatusCode.code())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(response, StandardCharsets.UTF_8)
        );
    }
}
