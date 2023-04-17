package ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi;

public class LanguagesExternalResponse {

    private LocationExternalResponse location;

    public LanguagesExternalResponse() {
    }

    public LanguagesExternalResponse(LocationExternalResponse location) {
        this.location = location;
    }

    public LocationExternalResponse getLocation() {
        return location;
    }

    public void setLocation(LocationExternalResponse location) {
        this.location = location;
    }
}
