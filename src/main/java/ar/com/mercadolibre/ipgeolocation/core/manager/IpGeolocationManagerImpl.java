package ar.com.mercadolibre.ipgeolocation.core.manager;

import ar.com.mercadolibre.ipgeolocation.client.ApiLayerClient;
import ar.com.mercadolibre.ipgeolocation.client.IpApiClient;
import ar.com.mercadolibre.ipgeolocation.client.IpGeolocationClient;
import ar.com.mercadolibre.ipgeolocation.common.mapper.IpGeolocationMapper;
import ar.com.mercadolibre.ipgeolocation.common.response.AverageDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.FarthestDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.IpGeolocationResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.NearestDistanceResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.IpGeolocationInfoExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.apiLayer.CurrencyExchangeRateExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi.LanguagesExternalResponse;
import ar.com.mercadolibre.ipgeolocation.common.util.CacheUtil;
import ar.com.mercadolibre.ipgeolocation.common.util.JsonUtils;
import ar.com.mercadolibre.ipgeolocation.common.util.LocalDateTimeWrapper;
import ar.com.mercadolibre.ipgeolocation.domain.entity.CountryStatisticsDataEntity;
import ar.com.mercadolibre.ipgeolocation.domain.repository.CountryStatisticsDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IpGeolocationManagerImpl implements IpGeolocationManager {

    private final IpGeolocationClient ipGeolocationClient;
    private final String ipGeolocationApiKey;
    private final IpApiClient ipApiClient;
    private final String ipApiAccessKey;
    private final ApiLayerClient apiLayerClient;
    private final CountryStatisticsDataRepository countryStatisticsDataRepository;
    private final String latitudeBA;
    private final String longitudeBA;
    private final LocalDateTimeWrapper localDateTimeWrapper;

    public IpGeolocationManagerImpl(IpGeolocationClient ipGeolocationClient,
                                    @Value("${ip.geolocation.apiKey}") String ipGeolocationApiKey,
                                    IpApiClient ipApiClient,
                                    @Value("${ip.api.accessKey}") String ipApiAccessKey,
                                    ApiLayerClient apiLayerClient,
                                    CountryStatisticsDataRepository countryStatisticsDataRepository,
                                    @Value("${buenos.aires.latitude}") String latitudeBA,
                                    @Value("${buenos.aires.longitude}") String longitudeBA,
                                    LocalDateTimeWrapper localDateTimeWrapper) {
        this.ipGeolocationClient = ipGeolocationClient;
        this.ipGeolocationApiKey = ipGeolocationApiKey;
        this.ipApiClient = ipApiClient;
        this.ipApiAccessKey = ipApiAccessKey;
        this.apiLayerClient = apiLayerClient;
        this.countryStatisticsDataRepository = countryStatisticsDataRepository;
        this.latitudeBA = latitudeBA;
        this.longitudeBA = longitudeBA;
        this.localDateTimeWrapper = localDateTimeWrapper;
    }

    public IpGeolocationResponse getGeolocationInfo(String ip){
        String currentDate = localDateTimeWrapper.getLocalDateTimeNow().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        Optional<String> ipData = CacheUtil.getKey(ip);

        if(ipData.isPresent()){
            IpGeolocationResponse ipGeolocationResponse = (IpGeolocationResponse) JsonUtils.fromJson(CacheUtil.getKey(ipData.get()).get(), IpGeolocationResponse.class);
            ipGeolocationResponse.setTime(calculateCurrentTime(ipGeolocationResponse.getTimezone()));
            ipGeolocationResponse.setCurrentDate(currentDate);
            saveCountryStatisticsData(ipGeolocationResponse);
            return ipGeolocationResponse;
        }

        IpGeolocationInfoExternalResponse ipGeolocationInfoExternalResponse =
                this.ipGeolocationClient.getIpGeolocationInfo(ipGeolocationApiKey,ip);
        Optional<String> countryData = CacheUtil.getKey(ipGeolocationInfoExternalResponse.getCountryName());
        if(countryData.isPresent()) {
            IpGeolocationResponse ipGeolocationResponse = (IpGeolocationResponse) JsonUtils.fromJson(countryData.get(), IpGeolocationResponse.class);
            ipGeolocationResponse.setTime(calculateCurrentTime(ipGeolocationResponse.getTimezone()));
            ipGeolocationResponse.setCurrentDate(currentDate);
            saveCountryStatisticsData(ipGeolocationResponse);
            CacheUtil.getCacheProvider().set(CacheUtil.FIVE_MINUTES,ip,ipGeolocationResponse.getCountryName());
            return ipGeolocationResponse;
        }

        IpGeolocationResponse ipGeolocationResponse =
                generateIpGeolocationResponse(ip,currentDate, ipGeolocationInfoExternalResponse);

        CacheUtil.getCacheProvider().set(CacheUtil.TWO_HOURS,ipGeolocationResponse.getCountryName(), JsonUtils.toJson(ipGeolocationResponse));
        CacheUtil.getCacheProvider().set(CacheUtil.FIVE_MINUTES,ip,ipGeolocationResponse.getCountryName());
        saveCountryStatisticsData(ipGeolocationResponse);
        return ipGeolocationResponse;

    }

    @Override
    public NearestDistanceResponse getNearestDistance() {
        List<CountryStatisticsDataEntity> countryStatisticsDataEntities = countryStatisticsDataRepository.findAll();
        CountryStatisticsDataEntity countryStatisticsDataEntity = countryStatisticsDataEntities.stream()
                .filter(it -> !it.getName().equalsIgnoreCase( "Argentina"))
                .min(Comparator.comparing(CountryStatisticsDataEntity::getDistance)).get();
        return new NearestDistanceResponse(countryStatisticsDataEntity.getName(),countryStatisticsDataEntity.getDistance());
    }

    @Override
    public FarthestDistanceResponse getFarthestDistance() {
        List<CountryStatisticsDataEntity> countryStatisticsDataEntities = countryStatisticsDataRepository.findAll();
        CountryStatisticsDataEntity countryStatisticsDataEntity = countryStatisticsDataEntities.stream()
                .max(Comparator.comparing(CountryStatisticsDataEntity::getDistance)).get();
        return new FarthestDistanceResponse(countryStatisticsDataEntity.getName(), countryStatisticsDataEntity.getDistance());
    }

    @Override
    public AverageDistanceResponse getAverageDistance() {
        List<CountryStatisticsDataEntity> countryStatisticsDataEntities = countryStatisticsDataRepository.findAll();
        Long totalInvocations = countryStatisticsDataEntities.stream()
                .collect(Collectors.summarizingLong(CountryStatisticsDataEntity::getInvocations)).getSum();

        Long totalDistancePerInvocations = countryStatisticsDataEntities.stream()
                .map(it -> it.getDistance() * it.getInvocations()).collect(Collectors.toList()).stream().mapToLong(i -> i.longValue()).sum();


        return new AverageDistanceResponse(totalDistancePerInvocations/totalInvocations);
    }

    private IpGeolocationResponse generateIpGeolocationResponse(
            String ip, String currentDate,
            IpGeolocationInfoExternalResponse ipGeolocationInfoExternalResponse){

        LanguagesExternalResponse languagesExternalResponse = this.ipApiClient.getLanguages(ip, ipApiAccessKey);
        CurrencyExchangeRateExternalResponse currencyExchangeRateExternalResponse =
                this.apiLayerClient.getCurrencyExchangeRates(
                        "USD", ipGeolocationInfoExternalResponse.getCurrency().getCode()
                );

        String currentTime = calculateCurrentTime(ipGeolocationInfoExternalResponse.getTimeZone().getName());
        Double distanceFromBA = calculateDistance(
                latitudeBA,
                longitudeBA,
                ipGeolocationInfoExternalResponse.getLatitude(),
                ipGeolocationInfoExternalResponse.getLongitude()
        );

        return IpGeolocationMapper
                .build()
                .getIpGeolocationResponse(
                        ipGeolocationInfoExternalResponse,
                        currentDate,
                        currentTime,
                        distanceFromBA.intValue(),
                        languagesExternalResponse,
                        currencyExchangeRateExternalResponse
                );

    }


    private void saveCountryStatisticsData(IpGeolocationResponse ipGeolocationResponse) {
        Optional<CountryStatisticsDataEntity> countryData =
                this.countryStatisticsDataRepository.findByName(ipGeolocationResponse.getCountryName());

        if(countryData.isPresent()){
            CountryStatisticsDataEntity countryStatisticsDataEntity = countryData.get();
            Long invocations = countryStatisticsDataEntity.getInvocations() + 1L;
            countryStatisticsDataEntity.setInvocations(invocations);
            countryStatisticsDataEntity.setUpdateAt(localDateTimeWrapper.getLocalDateTimeNow());
            this.countryStatisticsDataRepository.save(countryStatisticsDataEntity);
        }else {
            this.countryStatisticsDataRepository.save(
                    new CountryStatisticsDataEntity(
                            ipGeolocationResponse.getCountryName(),
                            ipGeolocationResponse.getEstimatedDistanceKms())
            );
        }
    }

    private Double calculateDistance(String latitudeFrom, String longitudeFrom, String latitudeTo, String longitudeTo) {
        double latitudeBA = Double.parseDouble(latitudeFrom);
        double delta = Double.parseDouble(longitudeFrom) - Double.parseDouble(longitudeTo);
        double distance = Math.sin(deg2rad(latitudeBA)) * Math.sin(deg2rad(Double.parseDouble(latitudeTo))) + Math.cos(deg2rad(latitudeBA)) * Math.cos(deg2rad(Double.parseDouble(latitudeTo))) * Math.cos(deg2rad(delta));
        distance = Math.acos(distance);
        distance = rad2deg(distance);
        distance = distance * 60 * 1.1515;
        distance = distance * 1.609344;
        return (distance);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private String calculateCurrentTime(String timeZone) {
        return localDateTimeWrapper.getLocalDateTimeNow(timeZone).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
