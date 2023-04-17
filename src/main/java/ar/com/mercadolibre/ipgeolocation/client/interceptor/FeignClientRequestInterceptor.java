package ar.com.mercadolibre.ipgeolocation.client.interceptor;

import ar.com.mercadolibre.ipgeolocation.common.route.ExternalRoute;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignClientRequestInterceptor implements RequestInterceptor {

    @Value("${api.layer.apiKey}")
    private String apiLayerApiKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if(requestTemplate.feignTarget().name().equalsIgnoreCase(ExternalRoute.API_LAYER_CLIENT_NAME)){
            requestTemplate.header("apiKey" , apiLayerApiKey);
        }
    }
}
