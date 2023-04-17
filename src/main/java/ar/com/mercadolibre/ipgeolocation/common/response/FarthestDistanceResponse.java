package ar.com.mercadolibre.ipgeolocation.common.response;

public class FarthestDistanceResponse {
    private String country;
    private Integer farthestDistance;

    public FarthestDistanceResponse() {
    }

    public FarthestDistanceResponse(String country, Integer farthestDistance) {
        this.country = country;
        this.farthestDistance = farthestDistance;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getFarthestDistance() {
        return farthestDistance;
    }

    public void setFarthestDistance(Integer farthestDistance) {
        this.farthestDistance = farthestDistance;
    }
}
