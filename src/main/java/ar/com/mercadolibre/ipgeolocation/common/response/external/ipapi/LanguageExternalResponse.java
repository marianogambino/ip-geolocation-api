package ar.com.mercadolibre.ipgeolocation.common.response.external.ipapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageExternalResponse {
    private String name;
    @JsonProperty("native")
    private String languageNative;

    public LanguageExternalResponse() {
    }

    public LanguageExternalResponse(String name, String languageNative) {
        this.name = name;
        this.languageNative = languageNative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguageNative() {
        return languageNative;
    }

    public void setLanguageNative(String languageNative) {
        this.languageNative = languageNative;
    }
}
