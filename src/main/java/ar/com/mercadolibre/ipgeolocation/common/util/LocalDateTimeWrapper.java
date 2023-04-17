package ar.com.mercadolibre.ipgeolocation.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class LocalDateTimeWrapper {

    public LocalDateTime getLocalDateTimeNow(){
        return LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTimeNow(String zoneId){
        return LocalDateTime.now(ZoneId.of(zoneId));
    }

}
