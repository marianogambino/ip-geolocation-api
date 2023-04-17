package ar.com.mercadolibre.ipgeolocation.common.response.external;

public class TimeZoneExternalResponse {
    private String name;

    public TimeZoneExternalResponse(){}

    public TimeZoneExternalResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
