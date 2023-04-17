package ar.com.mercadolibre.ipgeolocation.common.mapper;

import ar.com.mercadolibre.ipgeolocation.common.response.CurrencyExchangeRateResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.CurrencyRates;
import ar.com.mercadolibre.ipgeolocation.common.response.IpGeolocationResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.LanguageResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.IpGeolocationInfoExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.apiLayer.CurrencyExchangeRateExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi.LanguagesExternalResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class IpGeolocationMapper {

    private static final IpGeolocationMapper mapper = new IpGeolocationMapper();
    private IpGeolocationMapper() {
    }

    public static IpGeolocationMapper build(){ return mapper; }

    public IpGeolocationResponse getIpGeolocationResponse(
            IpGeolocationInfoExternalResponse ipGeolocationInfoExternalResponse,
            String currentDate,String currentTime, Integer distanceFromBA, LanguagesExternalResponse languagesExternalResponse,
            CurrencyExchangeRateExternalResponse currencyExchangeRateExternalResponse

    ){
        return new IpGeolocationResponse(
                ipGeolocationInfoExternalResponse.getCountryName(),
                ipGeolocationInfoExternalResponse.getCountryCode2(),
                ipGeolocationInfoExternalResponse.getTimeZone().getName(),
                currentDate,
                currentTime,
                languagesExternalResponse.getLocation().getLanguages().stream().map(it -> new LanguageResponse(it.getName(), it.getLanguageNative())).collect(Collectors.toList()),
                new CurrencyExchangeRateResponse("U$S",
                        currentDate,
                        new CurrencyRates(
                                ipGeolocationInfoExternalResponse.getCurrency().getCode(),
                                currencyExchangeRateExternalResponse.getRates().get(ipGeolocationInfoExternalResponse.getCurrency().getCode()))
                ),
                ipGeolocationInfoExternalResponse.getCountryName().equalsIgnoreCase("Argentina")? 0 : distanceFromBA,
                ipGeolocationInfoExternalResponse.getLatitude(),
                ipGeolocationInfoExternalResponse.getLongitude()

        );
    }


}
