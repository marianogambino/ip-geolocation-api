package ar.com.mercadolibre.ipgeolocation.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NearestDistanceResponse {
    private String country;
    private Integer nearestDistance;


    public NearestDistanceResponse() {
    }

    public NearestDistanceResponse(String country, Integer nearestDistance) {
        this.country = country;
        this.nearestDistance = nearestDistance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNearestDistance() {
        return nearestDistance;
    }

    public void setNearestDistance(Integer nearestDistance) {
        this.nearestDistance = nearestDistance;
    }
}
