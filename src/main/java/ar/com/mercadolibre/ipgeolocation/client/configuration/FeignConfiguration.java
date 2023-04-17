package ar.com.mercadolibre.ipgeolocation.client.configuration;

import ar.com.mercadolibre.ipgeolocation.client.interceptor.FeignClientRequestInterceptor;
import ar.com.mercadolibre.ipgeolocation.client.interceptor.FeignErrorDecoder;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignConfiguration {

    @Bean
    public FeignClientRequestInterceptor feignRequestInterceptor(){
        return new FeignClientRequestInterceptor();
    }

    @Bean
    public Retryer retry() {
        return new Retryer.Default(
                TimeUnit.SECONDS.toMillis(1),
                TimeUnit.SECONDS.toMillis(2),
                3
        );
    }

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
