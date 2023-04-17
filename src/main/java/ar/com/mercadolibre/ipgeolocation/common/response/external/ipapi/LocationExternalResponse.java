package ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi;

import java.util.List;

public class LocationExternalResponse {

    private List<LanguageExternalResponse> languages;

    public LocationExternalResponse() {
    }

    public LocationExternalResponse(List<LanguageExternalResponse> languages) {
        this.languages = languages;
    }

    public List<LanguageExternalResponse> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageExternalResponse> languages) {
        this.languages = languages;
    }
}
