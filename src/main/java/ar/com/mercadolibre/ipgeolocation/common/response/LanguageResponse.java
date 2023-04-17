package ar.com.mercadolibre.ipgeolocation.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LanguageResponse {
    private String name;
    @JsonProperty("native")
    private String languageNative;

    public LanguageResponse(String name, String languageNative) {
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
