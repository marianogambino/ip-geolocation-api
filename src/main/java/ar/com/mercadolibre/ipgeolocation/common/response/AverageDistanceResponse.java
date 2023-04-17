package ar.com.mercadolibre.ipgeolocation.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AverageDistanceResponse {
    private Long averageDistance;

    public AverageDistanceResponse() {
    }

    public AverageDistanceResponse(Long averageDistance) {
        this.averageDistance = averageDistance;
    }

    public Long getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(Long averageDistance) {
        this.averageDistance = averageDistance;
    }
}
