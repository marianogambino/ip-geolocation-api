package ar.com.mercadolibre.ipgeolocation.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class IpGeolocationResponse {
    private String countryName;
    private String countryCode;
    private String timezone;
    private String currentDate;
    private List<LanguageResponse> languages;
    private String time;
    private CurrencyExchangeRateResponse currencyExchangeRate;
    private Integer estimatedDistanceKms;
    private String latitude;
    private String longitude;

    public IpGeolocationResponse(){}

    public IpGeolocationResponse(
            String countryName, String countryCode,
            String timezone, String currentDate,
            String time, List<LanguageResponse> languages,
            CurrencyExchangeRateResponse currencyExchangeRateResponse,
            Integer estimatedDistanceKms,
            String latitude,
            String longitude) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.currentDate = currentDate;
        this.timezone = timezone;
        this.time = time;
        this.languages = languages;
        this.currencyExchangeRate = currencyExchangeRateResponse;
        this.estimatedDistanceKms = estimatedDistanceKms;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public List<LanguageResponse> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageResponse> languages) {
        this.languages = languages;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String currentTime) {
        this.time = currentTime;
    }

    public Integer getEstimatedDistanceKms() {
        return estimatedDistanceKms;
    }

    public void setEstimatedDistanceKms(Integer estimatedDistanceKms) {
        this.estimatedDistanceKms = estimatedDistanceKms;
    }

    public CurrencyExchangeRateResponse getCurrencyExchangeRate() {
        return currencyExchangeRate;
    }

    public void setCurrencyExchangeRate(CurrencyExchangeRateResponse currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
