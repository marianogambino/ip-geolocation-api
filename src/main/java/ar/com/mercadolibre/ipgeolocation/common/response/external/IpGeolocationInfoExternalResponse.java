package ar.com.mercadolibre.ipgeolocation.common.response.external;

public class IpGeolocationInfoExternalResponse {
    private String countryName;
    private String countryCode2;
    private String latitude;
    private String longitude;
    private CurrencyExternalResponse currency;
    private TimeZoneExternalResponse timeZone;

    public IpGeolocationInfoExternalResponse(String countryName, String countryCode2, String latitude,
                                             String longitude, CurrencyExternalResponse currency, TimeZoneExternalResponse timeZone) {
        this.countryName = countryName;
        this.countryCode2 = countryCode2;
        this.latitude = latitude;
        this.longitude = longitude;
        this.currency = currency;
        this.timeZone = timeZone;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
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

    public CurrencyExternalResponse getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyExternalResponse currency) {
        this.currency = currency;
    }

    public TimeZoneExternalResponse getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZoneExternalResponse timeZone) {
        this.timeZone = timeZone;
    }
}
